package org.apache.openjpa.lib.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MyClassUtilTest {

	private String classPath;
	
    @Parameters
    public static Collection<Object[]> configure() {
        return Arrays.asList(new Object[][]{
        	{ MyClassUtilTest.class.toString() },
            { ClassUtil.getClassName(int.class) },
            { null },
            { "" },
        });
    }

    public MyClassUtilTest( String classPath ) {
        this.classPath = classPath;
    }

    // La classe testa il metodo getClassName della ClassUtil
    // utilizzando i 4 casi passati dal metodo configure
    @Test
    public void getClassNameTest() {
        String expectedName;
        String className = ClassUtil.getClassName(classPath);
        
        if(classPath == null){
            expectedName = null ;
        } else if(classPath == ""){
            expectedName = "" ;
        } else if(classPath == "int"){
        	expectedName = "int";
        } else {
            int lastDot = classPath.toString().lastIndexOf('.');
            expectedName = lastDot > -1 ? classPath.substring(lastDot + 1) : classPath;
        }
        assertEquals(expectedName, className);
    }
    
    private static abstract class MyInnerClass {
        // not needed
    }

    private static final MyInnerClass INSTANCE = new MyInnerClass() {
    };
}