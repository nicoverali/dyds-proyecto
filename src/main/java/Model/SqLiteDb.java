package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SqLiteDb {

    protected static String url = "jdbc:sqlite:./dictionary.db";

    protected Statement createAndGetStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        statement.setQueryTimeout(30);  // set timeout to 30 sec.

        return statement;
    }

    protected boolean isConnectionOpen(Connection connection){
        return connection!=null;
    }
}
