package model.sql;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        String parsedValues = parseValues(values);
        return database.executeUpdate("INSERT INTO "+tableName+" VALUES ("+parsedValues+")");
    }

    private String parseValues(String[] values){
        return Arrays.stream(values)
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", "));
    }

}
