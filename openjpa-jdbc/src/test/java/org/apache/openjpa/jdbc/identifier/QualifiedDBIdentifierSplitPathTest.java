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


        //QualifiedDBIdentifier qDBI = QualifiedDBIdentifier.newPath(DBIdentifier.newSchema("Schema1"),DBIdentifier.newTable("Table1") , DBIdentifier.newColumn("Column"));

        //QualifiedDBIdentifier qDBI2 = QualifiedDBIdentifier.newPath( DBIdentifier.newSchema("Schema1") , DBIdentifier.newTable("THETABLE"));

        //QualifiedDBIdentifier qDBI2 = QualifiedDBIdentifier.newPath( DBIdentifier.NULL );

        DBIdentifier[] list = QualifiedDBIdentifier.splitPath(i1);

        //System.out.println(i1.getType()== DBIdentifier.DBIdentifierType.SCHEMA);

        /*
        for(DBIdentifier d : list){
            System.out.println(d.getName());
        }
*/
        if(expectedResult == false){

            assertEquals(0, list.length );

        }else {

            assertEquals(2, list.length );

        }


    }
}