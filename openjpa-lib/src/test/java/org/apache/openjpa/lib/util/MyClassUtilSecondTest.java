package org.apache.openjpa.lib.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MyClassUtilSecondTest {

	private String packagePath;
	private int id;

    @Parameters
    public static Collection<Object[]> configure() {
        return Arrays.asList(new Object[][]{
            { MyClassUtilSecondTest.class.toString(), 1 },
            { MyInnerClass.class.toString(), 2 },
            { MyInnerClass[].class.toString(), 3 },
            { INSTANCE.getClass().toString(), 4 },
            { long.class.toString(), 5 },
            { long[].class.toString(), 6 }
        });
    }

    public MyClassUtilSecondTest( String packagePath, int id ){
        this.packagePath = packagePath;
        this.id = id;
    }
 
    // La classe testa il metodo getPackageName della ClassUtil
    // utilizzando i 6 casi passati dal metodo configure
    @Test
    public void getPackageNameTest() {
        String expectedName = "";
    	String pakageName = ClassUtil.getPackageName(packagePath);
    
        if(id == 1 || id == 2 || id == 4){
            expectedName = "class org.apache.openjpa.lib.util";
        
        } else if(id == 3){
        	expectedName = "class [Lorg.apache.openjpa.lib.util" ;  
        	
	    } else if(id == 5 || id == 6){
	        expectedName = "";
	    }
        assertEquals(expectedName, pakageName);
    }
    
    private static abstract class MyInnerClass {
        // not needed
    }

    private static final MyInnerClass INSTANCE = new MyInnerClass() {
    };
}