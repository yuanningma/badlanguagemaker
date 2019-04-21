package edu.brown.cs.group1.database;
import edu.brown.cs.group1.patient.Patient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Abstract class for a database. This class contains general implementation
 * methods such as insert or querying to a respective database and a general
 * constructor that sets the Connections to the database.
 */
public abstract class Database {
  // String that contains path to the database
  private String db;

    /**
     * Constructor for a database.
     * @param db
     *          path to the database
     */
  public Database(String db) {
    this.db = db;
  }

  /**
   * default constructor.
 */
  public Database() { }
  /**
  * This method sets a connection to the database.
  * @param path
  *          string containing the path to the database.
  * @param dbConn
   *        connection that would query to the database
  */
  void setDbConn(String path, Connection dbConn) {
   // Set up a connection and store it in a field
    try {
      Class.forName("org.sqlite.JDBC");
      String url = "jdbc:sqlite:" + path;
      dbConn = DriverManager.getConnection(url);
      Statement stat = dbConn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys = ON;");
      stat.close();

    } catch (ClassNotFoundException exp) {
      exp.printStackTrace();

    } catch (SQLException sql) {
      sql.printStackTrace();
    }
  }

 /**
  * This method creates a database.
  *
  * @throws SQLException
  *             thrown when an SQLException is thrown
  */
  //public abstract void create() throws SQLException;
}
