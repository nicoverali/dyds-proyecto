package model.sql;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public interface ISQLiteDB {

    void createTable(String tableName, String[] attributes) throws SQLException;

    CachedRowSet executeQuery(String query) throws SQLException;

    int executeUpdate(String update) throws SQLException;

}
