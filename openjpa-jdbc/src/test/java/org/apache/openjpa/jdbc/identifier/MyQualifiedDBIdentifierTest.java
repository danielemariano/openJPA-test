package org.apache.openjpa.jdbc.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MyQualifiedDBIdentifierTest {

    private boolean expectedResult;
    private DBIdentifier dbId ;

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{
        	{ false , null },
            { true  , DBIdentifier.newTable("Schema.Table")},
            { false , QualifiedDBIdentifier.newPath( DBIdentifier.NULL )  }
        });
    }

    public MyQualifiedDBIdentifierTest(boolean expectedResult , DBIdentifier dbId ){
        this.expectedResult = expectedResult;
        this.dbId = dbId;    
    }

    @Test
    public void spliPathTest() {
        DBIdentifier[] list = QualifiedDBIdentifier.splitPath(dbId);
        if(expectedResult == false){
            assertEquals(0, list.length );
        }else {
            assertEquals(2, list.length );
        }
    }
}