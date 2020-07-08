package model.sql;

import org.intellij.lang.annotations.Language;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class SQLiteDB {

    private static final String BASE_URL = "jdbc:sqlite:./%s.db";
    private final String dbUrl;

    public SQLiteDB(String databaseName){
        dbUrl = String.format(BASE_URL, databaseName);
    }

    public SQLiteTable createTable(String tableName, String[] attributes) throws SQLException{
        createTableIfNotExists(tableName, attributes);
        return new SQLiteTable(this, tableName);
    }

    private void createTableIfNotExists(String tableName, String[] tableAttributes) throws SQLException {
        String attributes = String.join(",",tableAttributes);
        executeUpdate("CREATE TABLE IF NOT EXISTS "+tableName+" ("+attributes+")");
    }

    public CachedRowSet executeQuery(String query) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dbUrl)){
            try (Statement statement = conn.createStatement()){
                ResultSet result = statement.executeQuery(query);
                return createCachedRowSetFrom(result);
            }
        }
    }

    public int executeUpdate(String update) throws SQLException{
        try (Connection conn = DriverManager.getConnection(dbUrl)){
            try (Statement statement = conn.createStatement()){
                int affectedRows = statement.executeUpdate(update);
                return affectedRows;
            }
        }
    }

    private CachedRowSet createCachedRowSetFrom(ResultSet resultSet) throws SQLException {
        CachedRowSet set = RowSetProvider.newFactory().createCachedRowSet();
        set.populate(resultSet);
        return set;
    }

}
