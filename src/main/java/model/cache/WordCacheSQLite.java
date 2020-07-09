package model.cache;

import model.Word;
import model.sql.ISQLiteDB;

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

    private ISQLiteDB database;

    public WordCacheSQLite(ISQLiteDB database){
        this.database = database;
        try{
            database.createTable(TABLE_NAME, TABLE_ATTRIBUTES);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Word word) {
        try {
            database.executeUpdate("INSERT INTO "+TABLE_NAME+" VALUES( NULL, \""+word.getTerm()+"\", \""+word.getMeaning()+"\", 1, CURRENT_TIMESTAMP )");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Word get(String term) {
        try {
            String query = "SELECT * FROM "+TABLE_NAME+" WHERE term == "+term+" AND date >= DATE('now', '-1 day')";
            CachedRowSet resultSet = database.executeQuery(query);
            return getWordFrom(resultSet);
        } catch (SQLException e){
            return null;
        }
    }

    private Word getWordFrom(ResultSet rs) throws SQLException {
        Word word = null;
        if(hasResult(rs)){
            String term = rs.getString("term");
            String meaning = rs.getString("meaning");

            word = new Word(term, meaning);
            word.setSource(rs.getInt("source"));
            word.setDate(rs.getDate("date"));
        }
        return word;
    }

    private boolean hasResult(ResultSet rs) throws SQLException{
        return rs.next();
    }
}
