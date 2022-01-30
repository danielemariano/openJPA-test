package org.apache.openjpa.jdbc.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value= Parameterized.class)
public class QualifiedDBIdentifierSplitPathTest {


    private boolean expectedResult;
    private DBIdentifier i1 ;




    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{


                {false , null },
                {true  , DBIdentifier.newTable("Schema.Table")},
                {false , QualifiedDBIdentifier.newPath( DBIdentifier.NULL )  }


        });

    }


    public QualifiedDBIdentifierSplitPathTest(boolean expectedResult , DBIdentifier i1 ){


        this.expectedResult = expectedResult;
        this.i1 = i1;

    }


    @Test
    public void spliPathTest() {

        DBIdentifier[] list = QualifiedDBIdentifier.splitPath(i1);


        if(expectedResult == false){

            assertEquals(0, list.length );

        }else {

            assertEquals(2, list.length );

        }


    }
}