package model.sql;

import org.intellij.lang.annotations.Language;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

class SQLiteTable implements ISQLiteTable {

    private final SQLiteDB database;
    private final String tableName;


    public SQLiteTable(SQLiteDB db, String tableName){
        this.database = db;
        this.tableName = tableName;
    }

    @Override
    public CachedRowSet getAllWhere(String whereCondition) throws SQLException{
        return database.executeQuery("SELECT * FROM "+tableName+ " WHERE " + whereCondition);
    }

    @Override
    public CachedRowSet getAll() throws SQLException{
        return database.executeQuery("SELECT * FROM "+tableName);
    }

    @Override
    public int insert(String[] values) throws SQLException{
        String parsedValues = String.join(",", values);
        return database.executeUpdate("INSERT INTO "+tableName+" VALUES ("+parsedValues+")");
    }

}
