package model.storage;

import model.SqLiteDb;
import model.Word;

import java.sql.*;
import java.text.SimpleDateFormat;

public class CacheStorage extends SqLiteDb implements IInfoStorage {

    private static CacheStorage instance;

    private CacheStorage(){
        createNewDatabaseIfNoExist();
    }

    public static CacheStorage getInstance(){
        if(instance==null){
            instance = new CacheStorage();
        }
        return instance;
    }


    @Override
    public void saveWord(Word word) {
        Connection connection= null;
        try
        {
            connection = DriverManager.getConnection(url);

            Statement statement = createAndGetStatement(connection);

            System.out.println("INSERT  " + word.getTerm() + "', '"+ word.getMeaning() );

            saveWordIntoDb(statement,word);
        }
        catch(SQLException e)
        {
            System.err.println("Error saving " + e.getMessage());
        }
        finally
        {
            try
            {
                if(isConnectionOpen(connection))
                    connection.close();
            }
            catch(SQLException e)
            {
                System.err.println( e);
            }
        }
    }

    private void saveWordIntoDb(Statement statement,Word word) throws SQLException {
        statement.executeUpdate("insert into terms values(null, '"+ word.getTerm() + "', '"+ word.getMeaning() + "', 1,'"+
                getFormatDate()+"')");
    }

    private String getFormatDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = sdf.format(timestamp);
        return date;
    }

}
