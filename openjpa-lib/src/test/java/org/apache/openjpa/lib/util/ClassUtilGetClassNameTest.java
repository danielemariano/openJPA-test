package org.apache.openjpa.lib.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value= Parameterized.class)
public class ClassUtilGetClassNameTest {

    private String classPath ;

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{


                { ClassUtilGetClassNameTest.class.toString() },
                { null },


                {""},                    //aggiunto per migliorare statement e branch coverage
                {"[I"},                  //aggiunto per migliorare statement e branch coverage
                {"[K"},                  //aggiunto per migliorare branch coverage
                {"[ Byte"},              //aggiunto per migliorare statement e branch coverage
                {"[ Byte;"},             //aggiunto per migliorare statement e branch coverage

        });

    }


    public ClassUtilGetClassNameTest( String classPath ){


        this.classPath = classPath;


    }


    @Test
    public void getClassName() {

        /*
        System.out.println(byte[].class);
        System.out.println(byte.class);
        System.out.println(byte[].class.getClass());
        System.out.println(byte.class.getClass());


         */
        String expectedName ;

        String className = ClassUtil.getClassName(classPath);

        if(classPath == null){

            expectedName = null ;

        }else if(classPath==""){

            expectedName ="" ;

        }else if(classPath=="[I"){

            expectedName ="int[]" ;

        } else if(classPath=="[K"){

            expectedName ="[K[]" ;

        }else if(classPath=="[ Byte" || classPath=="[ Byte;"){

            expectedName ="Byte[]" ;

        } else{

            int lastDot = classPath.toString().lastIndexOf('.');

            expectedName = lastDot > -1 ? classPath.substring(lastDot + 1) : classPath;
        }


        assertEquals( expectedName , className);

    }
}