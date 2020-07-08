package model.storage;

import model.Word;
import org.junit.jupiter.api.Test;

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

        IWordCache cacheSt = CacheStorage.getInstance();

        cacheSt.saveWord(word);

        IInfoQuery cacheQuery = CacheQuery.getInstance();

        assertEquals("Meaning de prueba",cacheQuery.getMeaningWord("prueba").getMeaning());
    }
}