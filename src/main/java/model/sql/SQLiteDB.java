package model.sql;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class SQLiteDB implements ISQLiteDB {

    private static final String BASE_URL = "jdbc:sqlite:./%s.db";
    private final String dbUrl;

    public SQLiteDB(String databaseName){
        this.dbUrl = String.format(BASE_URL, databaseName);
        System.out.println(dbUrl);
    }

    @Override
    public void createTable(String tableName, String[] attributes) throws SQLException {
        String parsedAttributes = String.join(",", attributes);
        String createStatement = "CREATE TABLE IF NOT EXISTS "+tableName+" ("+parsedAttributes+")";
        executeUpdate(createStatement);
    }

    @Override
    public CachedRowSet executeQuery(String query) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dbUrl)){
            try (Statement statement = conn.createStatement()){
                ResultSet result = statement.executeQuery(query);
                return createCachedRowSetFrom(result);
            }
        }
    }

    @Override
    public int executeUpdate(String update) throws SQLException{
        try (Connection conn = DriverManager.getConnection(dbUrl)){
            try (Statement statement = conn.createStatement()){
                System.out.println(update);
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
