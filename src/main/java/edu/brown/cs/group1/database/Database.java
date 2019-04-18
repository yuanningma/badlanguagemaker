package edu.brown.cs.group1.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

  // connection that would query to the database
  private static Connection dbConn;

    /**
     * This constructor takes in a path to the db file
     *
     * @param db Path to the db file
     * @throws ClassNotFoundException
     * @throws SQLException
     */
  public Database(String db) throws ClassNotFoundException, SQLException {
        // Set up a connection and store it in a field
      Class.forName("org.sqlite.JDBC");
      String url = "jdbc:sqlite:" + db;
      dbConn = DriverManager.getConnection(url);
      Statement stat = dbConn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys = ON;");
      stat.close();
    }
}
