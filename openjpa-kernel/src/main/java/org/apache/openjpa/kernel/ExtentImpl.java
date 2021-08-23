/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProviderIterator;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.lib.util.collections.AbstractReferenceMap;
import org.apache.openjpa.lib.util.collections.FilterIterator;
import org.apache.openjpa.lib.util.collections.IteratorChain;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;

/**
 * Representation of all members of a persistent class.
 *
 * @author Abe White
 * @author Patrick Linskey
 */
public class ExtentImpl<T>
    implements Extent<T> {

    private static final ClassMetaData[] EMPTY_METAS = new ClassMetaData[0];

    private final Broker _broker;
    private final Class<T> _type;
    private final boolean _subs;
    private final FetchConfiguration _fc;
    private final ReentrantLock _lock;
    private boolean _ignore = false;

    // set of open iterators
    private ReferenceHashSet _openItrs = null;

    /**
     * Constructor.
     *
     * @param broker the owning broker
     * @param type the candidate class
     * @param subs whether subclasses are included in the extent
     */
    ExtentImpl(Broker broker, Class<T> type, boolean subs,
        FetchConfiguration fetch) {
        _broker = broker;
        _type = type;
        _subs = subs;
        if (fetch != null)
            _fc = fetch;
        else
            _fc = (FetchConfiguration) broker.getFetchConfiguration().clone();
        _ignore = broker.getIgnoreChanges();
        if (broker.getMultithreaded())
            _lock = new ReentrantLock();
        else
            _lock = null;
    }

    @Override
    public FetchConfiguration getFetchConfiguration() {
        return _fc;
    }

    @Override
    public boolean getIgnoreChanges() {
        return _ignore;
    }

    @Override
    public void setIgnoreChanges(boolean ignoreChanges) {
        _broker.assertOpen();
        _ignore = ignoreChanges;
    }

    @Override
    public List<T> list() {
        List<T> list = new ArrayList<>();
        Iterator<T> itr = iterator();
        try {
            while (itr.hasNext())
                list.add(itr.next());
            return list;
        } finally {
            ImplHelper.close(itr);
        }
    }

    @Override
    public Iterator<T> iterator() {
        _broker.assertNontransactionalRead();
        CloseableIterator citr = null;
        try {
            // create an iterator chain; add pnew objects if transactional
            CloseableIteratorChain chain = new CloseableIteratorChain();
            boolean trans = !_ignore && _broker.isActive();
            if (trans)
                chain.addIterator(new FilterNewIterator());

            // add database iterators for each implementing class
            MetaDataRepository repos = _broker.getConfiguration().
                getMetaDataRepositoryInstance();
            ClassMetaData meta = repos.getMetaData(_type,
                _broker.getClassLoader(), false);

            ClassMetaData[] metas;
            if (meta != null && (!_subs || !meta.isManagedInterface())
                && (meta.isMapped() || (_subs
                && meta.getMappedPCSubclassMetaDatas().length > 0)))
                metas = new ClassMetaData[]{ meta };
            else if (_subs && (meta == null || meta.isManagedInterface()))
                metas = repos.getImplementorMetaDatas(_type,
                    _broker.getClassLoader(), false);
            else
                metas = EMPTY_METAS;

            ResultObjectProvider rop;
            for (ClassMetaData classMetaData : metas) {
                rop = _broker.getStoreManager().executeExtent(classMetaData,
                        _subs, _fc);
                if (rop != null)
                    chain.addIterator(new ResultObjectProviderIterator(rop));
            }

            // filter deleted objects if transactional
            if (trans)
                citr = new FilterDeletedIterator(chain);
            else
                citr = chain;
            citr.setRemoveOnClose(this);
        } catch (OpenJPAException ke) {
            throw ke;
        } catch (RuntimeException re) {
            throw new GeneralException(re);
        }

        lock();
        try {
            if (_openItrs == null)
                _openItrs = new ReferenceHashSet(AbstractReferenceMap.ReferenceStrength.WEAK);
            _openItrs.add(citr);
        } finally {
            unlock();
        }
        return citr;
    }

    @Override
    public Broker getBroker() {
        return _broker;
    }

    @Override
    public Class<T> getElementType() {
        return _type;
    }

    @Override
    public boolean hasSubclasses() {
        return _subs;
    }

    @Override
    public void closeAll() {
        if (_openItrs == null)
            return;

        lock();
        try {
            CloseableIterator citr;
            for (Object openItr : _openItrs) {
                citr = (CloseableIterator) openItr;
                citr.setRemoveOnClose(null);
                try {
                    citr.close();
                }
                catch (Exception e) {
                }
            }
            _openItrs.clear();
        } catch (OpenJPAException ke) {
            throw ke;
        } catch (RuntimeException re) {
            throw new GeneralException(re);
        } finally {
            unlock();
        }
    }

    @Override
    public void lock() {
        if (_lock != null)
            _lock.lock();
    }

    @Override
    public void unlock() {
        if (_lock != null)
            _lock.unlock();
    }

    /**
     * Closeable iterator.
     */
    private interface CloseableIterator<T>
        extends Closeable, Iterator<T> {

        /**
         * Set the extent to remove self from on close.
         */
        void setRemoveOnClose(ExtentImpl<T> extent);
    }

    /**
     * Closeable {@link IteratorChain}.
     */
    private static class CloseableIteratorChain
        extends IteratorChain
        implements CloseableIterator {

        private ExtentImpl<?> _extent = null;
        private boolean _closed = false;

        @Override
        public boolean hasNext() {
            return (_closed) ? false : super.hasNext();
        }

        @Override
        public Object next() {
            if (_closed)
                throw new NoSuchElementException();
            return super.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setRemoveOnClose(ExtentImpl extent) {
            _extent = extent;
        }

        @Override
        public void close()
            throws Exception {
            if (_extent != null && _extent._openItrs != null) {
                _extent.lock();
                try {
                    _extent._openItrs.remove(this);
                } finally {
                    _extent.unlock();
                }
            }

            _closed = true;
            for (Iterator itr = this; itr.hasNext();)
                ((Closeable) itr.next()).close();
        }
    }

    /**
     * {@link FilterIterator} that skips deleted objects.
     */
    private static class FilterDeletedIterator
        extends FilterIterator
        implements CloseableIterator, Predicate {

        private ExtentImpl _extent = null;
        private boolean _closed = false;

        public FilterDeletedIterator(Iterator itr) {
            super(itr);
            setPredicate(this);
        }

        @Override
        public boolean hasNext() {
            return (_closed) ? false : super.hasNext();
        }

        @Override
        public Object next() {
            if (_closed)
                throw new NoSuchElementException();
            return super.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setRemoveOnClose(ExtentImpl extent) {
            _extent = extent;
        }

        @Override
        public void close()
            throws Exception {
            if (_extent != null && _extent._openItrs != null) {
                _extent.lock();
                try {
                    _extent._openItrs.remove(this);
                } finally {
                    _extent.unlock();
                }
            }

            _closed = true;
            ((Closeable) getIterator()).close();
        }

        @Override
        public boolean test(Object o) {
            return !_extent._broker.isDeleted(o);
        }
    }

    /**
     * Iterator over all new objects in this extent. This iterator is always
     * wrapped, so it doesn't need to keep track of whether it's closed.
     */
    private class FilterNewIterator
        extends FilterIterator
        implements Closeable, Predicate {

        public FilterNewIterator() {
            super(_broker.getTransactionalObjects().iterator());
            setPredicate(this);
        }

        @Override
        public void close() {
        }

        @Override
        public boolean test(Object o) {
            if (!_broker.isNew(o))
                return false;

            Class<?> type = o.getClass();
            if (!_subs && type != _type)
                return false;
            if (_subs && !_type.isAssignableFrom(type))
                return false;
            return true;
		}
	}
}
