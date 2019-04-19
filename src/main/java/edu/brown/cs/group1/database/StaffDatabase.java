package edu.brown.cs.group1.database;

import edu.brown.cs.group1.patient.Patient;
import edu.brown.cs.group1.staff.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Public class that concerns the StaffDatabase.
 * This class extends the abstract class Database which
 * implements the setDbConn method.
 */
public class StaffDatabase extends Database {
  private String path;
  private Connection dbConn;

  StaffDatabase(String path) {
    this.path = path;
    setDbConn(path, dbConn);
  }

  @Override
  public void create() throws SQLException {
    if (dbConn != null) {
     String query = "CREATE TABLE IF NOT EXISTS staff("
                    + "staffId INTEGER,"
                    + "name TEXT,"
                    + "department TEXT,"
                    + "is_Doctor TEXT,"
                    + "is_Admin TEXT,"
                    + "is_Working TEXT,"
                    + "PRIMARY KEY (staffId)"
                    + "ON DELETE CASCADE ON UPDATE CASCADE);";
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
      prep.setString(3, "department");
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

}
