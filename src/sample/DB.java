package sample;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;


public class DB {

    private static Connection connection = null;

    private static void openConnection(){
        try {
            String DEVICE = "com.mysql.cj.jdbc.Driver";
            Class.forName(DEVICE);
            String PATH = "jdbc:mysql://localhost:3306/league";
            String USER = "root";
            String PASSWORD = "1234";
            connection = DriverManager.getConnection(PATH, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static Connection getConnection(){
        Connection conn= null;
        try {
            String DEVICE = "com.mysql.cj.jdbc.Driver";
            Class.forName(DEVICE);
            String PATH = "jdbc:mysql://localhost:3306/league2";
            String USER = "root";
            String PASSWORD = "1234";
            conn = DriverManager.getConnection(PATH, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e){
            System.out.println("Invalid Connection");
            throw e;
        }
    }

    public static ResultSet getResult(String queryStmt) throws SQLException{
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
        try {
            //Connect to DB (Establish MySql Connection)
            openConnection();
            System.out.println("Select statement: " + queryStmt + "\n");

            //Create statement
            stmt = connection.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            closeConnection();
        }
        //Return CachedRowSet
        return crs;
    }

    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void updateDB(String sqlStmt) throws SQLException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            openConnection();
            //Create Statement
            stmt = connection.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            closeConnection();
        }
    }

}
