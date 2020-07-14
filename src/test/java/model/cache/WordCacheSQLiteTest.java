package model.cache;

import model.sql.SQLiteDB;
import model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordCacheSQLiteTest {

    @Mock SQLiteDB sqLiteDBMock;
    @Mock CachedRowSet resultSetMock;
    @InjectMocks WordCacheSQLite wordCacheSQLite_test;
    Word word;
    @Mock Date dateMock;

    private static final String TABLE_NAME = "words";
    private static final String[] TABLE_ATTRIBUTES = new String[]{
            "id INTEGER PRIMARY KEY AUTOINCREMENT",
            "term STRING",
            "meaning STRING",
            "source INTEGER",
            "date TEXT"
    };

    @BeforeEach
    public void setUpWord(){
        word = new Word("test","testMeaning");
        word.setSource(1);
        word.setDate(dateMock);
    }

    @BeforeEach
    public void createTable_shouldBeCallAlways_WhenCreateAWordCacheSQLiteObj() throws SQLException{

            verify(sqLiteDBMock).createTable(TABLE_NAME,TABLE_ATTRIBUTES);

    }


    @DisplayName("Save Word into 'words' table de sqlite with exactly the same sql sentence")
    @Test
    public void saveWord_shouldCallSQLiteDB_WithExecuteUpdate() throws SQLException{

        wordCacheSQLite_test.save(word);

        verify(sqLiteDBMock).executeUpdate
                    ("INSERT INTO words VALUES( NULL, \""+word.getTerm()+"\", \""+word.getMeaning()+"\", 1, CURRENT_TIMESTAMP )");

    }

    @DisplayName("getWord should query db with the same query and no find term, so return word null")
    @Test
    public void getWord_ShouldQueryDB_getResultsetEmpty_AndGetWordNull() throws SQLException{

        String term = "test";

        when(sqLiteDBMock.executeQuery(any())).thenReturn(resultSetMock);

        Word wordAnswer = wordCacheSQLite_test.get(term);

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE term == "+term+" AND date >= DATE('now', '-1 day')";

         verify(sqLiteDBMock).executeQuery(query);

         assertEquals(null,wordAnswer);


    }
}