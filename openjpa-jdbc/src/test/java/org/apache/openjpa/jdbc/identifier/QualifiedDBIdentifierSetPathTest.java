package org.apache.openjpa.jdbc.identifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


import static org.junit.Assert.*;

@RunWith(value= Parameterized.class)
public class QualifiedDBIdentifierSetPathTest {


    private boolean expectedResult;
    private DBIdentifier i1, i2 ;
    private String type ;


    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{


                {false , "multi" , null , null},
                {true  , "multi" , DBIdentifier.newSchema("Schema_1"), DBIdentifier.newTable("Table_1")},
                {false , "multi" , DBIdentifier.NULL , DBIdentifier.NULL },

                //aggiunto per migliorare statement coverage
                {true  , "mono" , DBIdentifier.newSchema("Schema_1") , null},

                //aggiunto per migliorare statement e branch coverage
                {true  , "multi" , DBIdentifier.newTable("Table_1") , DBIdentifier.newColumn("Column_1")},

                //aggiunto per aumentare la branch coverage
                {true  , "multiNoConventional" , DBIdentifier.newColumn("Column_1"), DBIdentifier.newCatalog("Catalog_1")},

                //aggiunto per aumentare la branch coverage
                {false , "empty" , null , null},

                //aggiunto per aumentare la branch coverage
                {true , "list" ,DBIdentifier.newSchema("Schema_1"), DBIdentifier.newTable("Table_1")},

                //aggiunto per migliorare statement coverage
                {true  , "monoMutation" , DBIdentifier.newSchema("Schema_1") , null},




        });

    }


    public QualifiedDBIdentifierSetPathTest(boolean expectedResult ,String type , DBIdentifier i1 , DBIdentifier i2 ){


        this.expectedResult = expectedResult;
        this.i1 = i1;
        this.i2 = i2;
        this.type = type;

    }


    @Test
    public void setPathTest() {


        QualifiedDBIdentifier qDBI = QualifiedDBIdentifier.newPath(DBIdentifier.newTable("Table1"));

        String expectedName = null;


        if (type == "multi") {

            qDBI.setPath(i1, i2);

            if (expectedResult == true) {

                expectedName = i1.getName() + "." + i2.getName();

            } else {

                expectedName = "Table1";

            }

        } else if (type == "mono") {

            qDBI.setPath(i1);

            expectedName = i1.getName();

        }else if (type=="multiNoConventional"){

            qDBI.setPath(i1, i2);

            if (expectedResult == true) {

                expectedName =  i2.getName();

            } else {

                expectedName = "Table1";

            }

        }else if (type=="empty"){

            qDBI.setPath(null);

            expectedName = "Table1";

        }else if (type=="list"){

            qDBI.setPath(new DBIdentifier[]{i1,i2});

            expectedName = i1.getName() + "." + i2.getName();

        }else if (type=="monoMutation"){

            qDBI.setPath(i1);

            //assertEquals(i1.getName(),qDBI.getSchemaName());
            //assertEquals(i1.getType(),qDBI.getType());

            expectedName = i1.getName();
        }


        assertEquals(expectedName, qDBI.getName());
    }


}