package model.cache;

import model.Word;
import model.sql.ISQLiteTable;
import model.sql.SQLiteDB;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordCacheSQLite implements IWordCache {

    private static final String TABLE_NAME = "words";
    private static final String[] TABLE_ATTRIBUTES = new String[]{
            "id INTEGER PRIMARY KEY AUTOINCREMENT",
            "term STRING",
            "meaning STRING",
            "source INTEGER",
            "date TEXT"
    };

    private ISQLiteTable wordTable;

    public WordCacheSQLite(SQLiteDB database){
        try{
            wordTable = database.createTable(TABLE_NAME, TABLE_ATTRIBUTES);
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void save(Word word) {
        try {
            String[] insertValues = new String[]{"NULL", word.getTerm(), word.getMeaning(), "1", "CURRENT_TIMESTAMP"};
            wordTable.insert(insertValues);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Word get(String term) {
        try {
            String condition = String.format("term == %s AND date >= DATE('now', '-1 day')", term);
            CachedRowSet resultSet = wordTable.getAllWhere(condition);
            return getWordFrom(resultSet);
        } catch (SQLException e){
            return null;
        }
    }

    private Word getWordFrom(ResultSet rs) throws SQLException {
        Word word = null;
        if(rs.next()){
            String term = rs.getString("term");
            String meaning = rs.getString("meaning");

            word = new Word(term, meaning);
            word.setSource(rs.getInt("source"));
            word.setDate(rs.getDate("date"));
        }
        return word;
    }
}
