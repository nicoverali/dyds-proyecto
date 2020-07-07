package model;

import java.sql.*;

public abstract class SqLiteDb {

    protected static String url = "jdbc:sqlite:./dictionary.db";

    protected Statement createAndGetStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        statement.setQueryTimeout(30);  // set timeout to 30 sec.

        return statement;
    }

    protected void createNewDatabaseIfNoExist() {

        try (Connection connection = DriverManager.getConnection(url)) {

            if (isConnectionOpen(connection)) {

                DatabaseMetaData meta = connection.getMetaData();

                Statement statement = createAndGetStatement(connection);

                createDbTable(statement);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void createDbTable(Statement statement) throws SQLException {
        statement.executeUpdate("create table IF NOT EXISTS terms (id INTEGER PRIMARY KEY AUTOINCREMENT, term string, meaning string, " +
                "source integer, date TEXT)");
    }


    protected boolean isConnectionOpen(Connection connection){
        return connection!=null;
    }
}
