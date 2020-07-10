package model.sql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SQLiteDBTest {

    SQLiteDB dbTest;
    @Mock SQLiteDB myMock;

    private static final String TABLE_NAME = "words";
    private static final String[] TABLE_ATTRIBUTES = new String[]{
            "id INTEGER PRIMARY KEY AUTOINCREMENT",
            "term STRING",
            "meaning STRING",
            "source INTEGER",
            "date TEXT"
    };

    @BeforeEach
    public void initDBTestObject(){
        dbTest = new SQLiteDB("dictionary");
    }

    @DisplayName("This Test Indirectly test createTable method who execute an Update Statement with same params")
    @Test
    public void executeUpdate_shouldReturn0_whenExecuteADDLStatement(){
        try {
            String parsedAttributes = String.join(",", TABLE_ATTRIBUTES);

            int ddlStatementAnswer = dbTest.executeUpdate("CREATE TABLE IF NOT EXISTS \"+tableName+\" (\"+parsedAttributes+\")");

            assertEquals(0,ddlStatementAnswer);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void executeQuery_ShouldReturnAResultSetWithValues_BecauseThereIsSomethingInTable(){

        try{


            CachedRowSet resultSet = dbTest.executeQuery("Select * from words");

            assertEquals(true,resultSet.next());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }



    @Test
    public void createTable_ShouldCallExecuteUpdate(){

        try {

            myMock.createTable(TABLE_NAME,TABLE_ATTRIBUTES);

            verify(myMock).executeUpdate("CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY AUTOINCREMENT, term STRING, meaning STRING," +
                    "source INTEGER, date TEXT) ");
        }

        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}