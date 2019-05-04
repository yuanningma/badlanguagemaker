package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.brown.cs.group1.staff.Staff;

/**
 * Database that contains usernames and passwords for staff members. This class
 * extends Database.
 */
public class LoginDatabase extends Database {
  private String path;
  private Connection dbConn;

  /**
   * Constructor for the database.
   * @param path
   *          a path to the database.
   */
  public LoginDatabase(String path) {
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
   * Method that saves new user to the database.
   * @param staffMember
   *          the new staff member to be inserted
   * @param username
   *          the username of the staff member account.
   * @param password
   *          the password of the staff member account.
   * @throws SQLException
   *           thrown when a SQLException is thrown
   */
  public void saveUser(Staff staffMember, String username, String password)
      throws SQLException {
    if (dbConn != null) {
      PreparedStatement prep;
      String query =
          "CREATE TABLE IF NOT EXISTS access_codes(" + "staffId INTEGER,"
              + "username TEXT"
              + "password TEXT"
              + "PRIMARY KEY (username));";

      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();

      query = "INSERT INTO staff VALUES (?,?,?);";
      prep = dbConn.prepareStatement(query);
      prep.setInt(1, staffMember.getStaffId());
      prep.setString(2, username);
      prep.setString(3, password);
      prep.addBatch();
      prep.executeBatch();

      prep.close();
    }
  }

  /**
   * Updates a specific field with a specific value in the staff database.
   * @param field
   *          the field to be updated
   * @param value
   *          the value the field should be updated
   * @param staff
   *          the patient object with new information to be updated
   * @throws SQLException
   *           thrown when an SQLException is throw.
   */
  public void update(String field, String value, Staff staff)
      throws SQLException {
    if (dbConn != null) {
      String query = new String();
      switch (field) {
      case "username":
        query = "UPDATE staff SET username = ? WHERE staffId = ?";
        break;
      case "password":
        query = "UPDATE staff SET password = ? WHERE staffId = ?";
        break;
      default:
        query = null;
      }
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setString(1, value);
      prep.setInt(2, staff.getStaffId());

      prep.executeUpdate();
      prep.close();
    }
  }

  /**
   * Method to retrieve password.
   *
   * @param username
   *          the staff member username.
   * @return a String corresponding to the password of the input username.
   *
   * @throws SQLException
   *           thrown when a SQLExpection is throw within method
   */
  public String getPassword(String username) throws SQLException {
    if (dbConn != null) {
      String query = "SELECT password FROM staff WHERE (username = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setString(1, username);

      ResultSet rs = prep.executeQuery();
      String password = null;

      while (rs.next()) {
        password = rs.getString(1);
      }
      rs.close();
      prep.close();
      return password;
    }
    return null;
  }
}
