package Model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class CacheQuery extends SqLiteDb implements IInfoQuery{

    private static CacheQuery instance;


    public CacheQuery(){}

    public static CacheQuery getInstance(){
        if (instance==null){
            instance = new CacheQuery();
        }
        return instance;
    }

    /**
     *
     * @param term
     * @return Retorna un Objeto Word que en caso de que no se encuentre en la Cache el término buscado
     * retorna NULL.
     */
    @Override
    public Word getMeaning(String term) {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

            Statement statement = createAndGetStatement(connection);

            Word word = realizeCacheQuery(statement,term);

            return word;
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println("Get term error " + e.getMessage());
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
                System.err.println(e);
            }
        }
        return null;
    }

    private Word realizeCacheQuery(Statement statement,String term) throws SQLException {
        ResultSet rs = statement.executeQuery("select * from terms WHERE term = '" + term + "'" );

        Word word = checkAndLoadWord(term,rs);

        return word;
    }

    private Word checkAndLoadWord(String term,ResultSet rs) throws SQLException {
        Word toRet = null;

        rs.next();

        String meaning = rs.getString("meaning");

        if(existCacheEntry(meaning)){
            toRet.setTerm(term);
            toRet.setMeaning(meaning);
            toRet.setSource(rs.getInt("source"));
            toRet.setDate(getFormatDate(rs.getString("date")));
        }

        return toRet;
    }

    private boolean existCacheEntry(String meaning){
        return meaning!=null;
    }

    private Date getFormatDate(String date) {
        try {
            Date dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return dateFormat;
        }
        catch (ParseException e){
            return null;
        }
    }

}
