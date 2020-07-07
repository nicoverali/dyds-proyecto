package model.storage;

import model.Word;
import model.query.CacheQuery;
import model.query.IInfoQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CacheStorageTest {

    /**
    @BeforeAll
    public static void deleteTableIfExist(){
        Connection connection= null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

            Statement statement = connection.createStatement();

            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("DROP TABLE IF EXISTS terms");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    **/

    @Test
    public void shouldSaveWordIntoDb(){
        Word word = new Word("prueba","Meaning de prueba");

        IInfoStorage cacheSt = CacheStorage.getInstance();

        cacheSt.saveWord(word);

        IInfoQuery cacheQuery = CacheQuery.getInstance();

        assertEquals("Meaning de prueba",cacheQuery.getMeaningWord("prueba").getMeaning());
    }
}