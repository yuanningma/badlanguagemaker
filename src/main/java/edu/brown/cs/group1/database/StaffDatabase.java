package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.group1.staff.Admin;
import edu.brown.cs.group1.staff.Doctor;
import edu.brown.cs.group1.staff.Staff;

/**
 * Public class that concerns the StaffDatabase. This class extends the abstract
 * class Database which implements the setDbConn method.
 */
public class StaffDatabase extends Database {
  private String path;
  private Connection dbConn;

  public StaffDatabase(String path) {
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
   * Method that saves new staff members to the database.
   * @param staffMember
   *          the new staff member to be inserted
   * @throws SQLException
   *           thrown when a SQLException is thrown
   */
  public void saveNewStaff(Staff staffMember) throws SQLException {
    if (dbConn != null) {
      PreparedStatement prep;
      String query = "CREATE TABLE IF NOT EXISTS staff(" + "staffId INTEGER,"
          + "name TEXT,"
          + "is_Doctor TEXT,"
          + "is_Admin TEXT,"
          + "is_Working TEXT,"
          + "PRIMARY KEY (staffId));";
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();

      query = "INSERT INTO staff VALUES (?,?,?,?,?);";
      prep = dbConn.prepareStatement(query);
      prep.setInt(1, staffMember.getStaffId());
      prep.setString(2, "name");
      prep.setString(3, String.valueOf(staffMember.isDoctor()));
      prep.setString(4, String.valueOf(staffMember.isAdmin()));
      prep.setString(5, String.valueOf(staffMember.isWorking()));
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
      case "name":
        query = "UPDATE staff SET name = ? WHERE staffId = ?";
        break;
      case "is_Doctor":
        query = "UPDATE staff SET is_Doctor = ? WHERE staffId = ?";
        break;
      case "is_Admin":
        query = "UPDATE staff SET is_Admin = ? WHERE staffId = ?";
        break;
      case "is_Working":
        query = "UPDATE staff SET is_Working = ? WHERE staffId = ?";
        break;
      case "username":
        query = "UPDATE staff SET username = ? WHERE staffId = ?";
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
   * Method to retrieve Staff member with the Staff id.
   *
   * @param id
   *          the staff member id.
   * @return a Staff member object corresponding to the input id. If no staff
   *         member exist in the database the method would return null.
   *
   * @throws SQLException
   *           thrown when a SQLExpection is throw within method
   */
  public Staff getStaffMember(Integer id) throws SQLException {
    if (dbConn != null) {

      String query = "SELECT * FROM staff WHERE (staffId = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setInt(1, id);

      ResultSet rs = prep.executeQuery();
      Staff staffmember = null;

      while (rs.next()) {
        String name = rs.getString(2);
        String permissions = rs.getString(3);
        String isAdmin = rs.getString(4);
        String isWorking = rs.getString(6);
        if (Boolean.parseBoolean(isAdmin)) {
          staffmember = new Admin(id,
              extractPermissions(permissions),
              true,
              false,
              Boolean.parseBoolean(isWorking));
        } else {
          staffmember = new Doctor(id,
              extractPermissions(permissions),
              false,
              true,
              Boolean.parseBoolean(isWorking));
        }
      }
      rs.close();
      prep.close();
      return staffmember;
    }
    return null;
  }

  /**
   * Deletes a staff member from the staff database.
   * @param staffID
   *          the staffID of the patient being deleted.
   * @throws SQLException
   *           throws a SQLExpection when an errors occurs when interacting with
   *           the database.
   */
  public void deleteStaff(Integer staffID) throws SQLException {
    if (dbConn != null) {

      String query = "DELETE FROM patient WHERE (patientId = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setInt(1, staffID);

      prep.executeQuery();

      prep.close();
    }
  }

  /**
   * Method that extracts the permissions from a string.
   *
   * @param access
   *          a string contains all the staff member's granted access.
   * @return a Map containing the granted access of the staff member.
   */
  public Map<Integer, Boolean> extractPermissions(String access) {
    String[] arr = access.split(" ");
    Map<Integer, Boolean> toReturn = new HashMap<>();

    for (String s : arr) {
      toReturn.put(Integer.parseInt(s), true);
    }

    return toReturn;
  }
}
