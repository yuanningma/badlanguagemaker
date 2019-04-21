package edu.brown.cs.group1.database;

import edu.brown.cs.group1.staff.Admin;
import edu.brown.cs.group1.staff.Doctor;
import edu.brown.cs.group1.staff.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Public class that concerns the StaffDatabase.
 * This class extends the abstract class Database which
 * implements the setDbConn method.
 */
public class StaffDatabase extends Database {
  private String path;
  private Connection dbConn;

  public StaffDatabase(String path) {
    this.path = path;
    setDbConn(path, this.dbConn);
  }

  @Override
  public void create() throws SQLException {
    if (dbConn != null) {
     String query = "CREATE TABLE IF NOT EXISTS staff("
                    + "staffId INTEGER,"
                    + "name TEXT,"
                    + "permissions TEXT,"
                    + "is_Doctor TEXT,"
                    + "is_Admin TEXT,"
                    + "is_Working TEXT,"
                    + "PRIMARY KEY (staffId));";

      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();
      prep.close();
    }
  }

    /**
     * Inserts staff member into the staff database.
     * @param staffMember
     *                  the new staff member to be inserted
     * @throws SQLException
     *                  thrown when a SQLException is thrown
     */
  public void insert(Staff staffMember) throws SQLException {
    if (dbConn != null) {

      String query = "INSERT INTO staff VALUES (?,?,?,?,?,?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setInt(1, staffMember.getStaffId());
      prep.setString(2, "name");
      prep.setString(3, staffMember.
                          parsePermissions(staffMember.getPermissions()));
      prep.setString(4, String.valueOf(staffMember.isDoctor()));
      prep.setString(5, String.valueOf(staffMember.isAdmin()));
      prep.setString(6, String.valueOf(staffMember.isWorking()));
      prep.addBatch();
      prep.executeBatch();

      prep.close();
    }
  }


    /**
     * Updates a specific field with a specific value in the staff database.
     * @param field
     *              the field to be updated
     * @param value
     *              the value the field should be updated
     * @param staff
     *               the patient object with new information to be updated
     * @throws SQLException
     *          thrown when an SQLException is throw.
     */
    public void update(String field, String value,
                       Staff staff) throws SQLException {
    if (dbConn != null) {

      String query = "UPDATE patients SET ? = ? WHERE staffId = ?";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setString(1, field);
      prep.setString(2, value);
      prep.setInt(3, staff.getStaffId());

      prep.executeUpdate();
      prep.close();
    }
  }

    /**
     * Method to retrieve Staff member with the Staff id.
     *
     * @param id
     *          the staff member id.
     * @return
     *        a Staff member object corresponding to the input id.
     *        If no staff member exist in the database the method would
     *        return null.
     *
     * @throws SQLException
     *              thrown when a SQLExpection is throw within method
     */
  public Staff getStaffMember(String id) throws SQLException {
    if (dbConn != null) {

      String query = "SELECT * FROM staff WHERE (staffId = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();
      Staff staffmember = null;

      while (rs.next()) {
        String name = rs.getString(2);
        String permissions = rs.getString(3);
        String isAdmin = rs.getString(4);
        String isWorking = rs.getString(6);
        if (Boolean.parseBoolean(isAdmin)) {
          staffmember = new Admin(Integer.parseInt(id),
                    staffmember.extractPermissions(permissions) ,true, false,
                    Boolean.parseBoolean(isWorking));
        } else {
          staffmember = new Doctor(Integer.parseInt(id),
                    staffmember.extractPermissions(permissions), false, true,
                    Boolean.parseBoolean(isWorking));
        }
      }
      rs.close();
      prep.close();
      return staffmember;
    }
    return null;
  }

}
