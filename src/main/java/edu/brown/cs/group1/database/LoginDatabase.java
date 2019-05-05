package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.brown.cs.group1.databaseAuthentication.PasswordUtlitities;
import edu.brown.cs.group1.staff.Staff;

/**
 * Database that contains usernames and passwords for staff members. This class
 * extends Database.
 */
public class LoginDatabase extends Database {
  private String path;
  private Connection dbConn;
  private PasswordUtlitities passwordUtlitities;
  private static final int SALT_LENGTH = 30;

  /**
   * Constructor for the database.
   * @param path
   *          a path to the database.
   */
  public LoginDatabase(String path) {
    passwordUtlitities = new PasswordUtlitities();
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
   * @param id
   *          the new staff member to be inserted
   * @param username
   *          the username of the staff member account.
   * @param input
   *          the password of the staff member account.
   * @throws SQLException
   *           thrown when a SQLException is thrown
   */
  public void saveUser(Integer id, String username, String input)
      throws SQLException {
    if (dbConn != null) {
      PreparedStatement prep;
      String query =
          "CREATE TABLE IF NOT EXISTS access_codes(" + "staffId INTEGER,"
              + "username TEXT,"
              + "encoded_password TEXT,"
              + "salt TEXT,"
              + "PRIMARY KEY (username));";

      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();

      query = "INSERT INTO access_codes VALUES (?,?,?,?);";
      prep = dbConn.prepareStatement(query);
      prep.setInt(1, id);
      prep.setString(2, username);
      String salt = passwordUtlitities.getSalt(SALT_LENGTH);
      String password = passwordUtlitities.generateSecurePassword(input, salt);
      prep.setString(3, password);
      prep.setString(4, salt);
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
        query = "UPDATE access_codes SET username = ? WHERE staffId = ?";
        break;
      case "password":
        query = "UPDATE access_codes SET password = ? WHERE staffId = ?";
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
      String query = "SELECT encoded_password FROM access_codes WHERE (username = ?);";
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

  public String getSalt(String username) throws SQLException {
      String toReturn = new String();
      if (dbConn != null) {
          String query = "SELECT salt FROM access_codes WHERE (username = ?)";
          PreparedStatement prep;
          prep = dbConn.prepareStatement(query);
          prep.setString(1, username);
          ResultSet rs = prep.executeQuery();
          while (rs.next()) {
              toReturn = rs.getString(1);
          }
          rs.close();
          prep.close();
      }
      return toReturn;
  }
}
