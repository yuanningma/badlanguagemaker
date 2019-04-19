package edu.brown.cs.group1.database;
import edu.brown.cs.group1.patient.Patient;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Public class that concerns the PatientDatabase.
 * This class extends the abstract class Database which
 * implements the setDbConn method.
 */
public class PatientDatabase extends Database {
  private String path;
  private Connection dbConn;
  private static final int SEVEN = 7;

  PatientDatabase(String path) {
    this.path = path;
    setDbConn(path, dbConn);
  }

  @Override
  public void create() throws SQLException {
    if (dbConn != null) {
      String query = "CREATE TABLE IF NOT EXISTS patient("
                      + "patientId INTEGER,"
                      + "name TEXT,"
                      + "insurance_name TEXT,"
                      + "insurance_number INTEGER,"
                      + "doctor TEXT,"
                      + "dob DATE,"
                      + "emergency_contact TEXT,"
                      + "PRIMARY KEY (patientId),"
                      + "FOREIGN KEY (doctor) REFERENCES  doctor(id)"
                      + "ON DELETE CASCADE ON UPDATE CASCADE);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();
      prep.close();
    }
  }

    /**
     * Inserts a patient into the patient database.
     * @param patient
     *           a new patient to be added to the patient database.
     * @throws SQLException
     *           thrown when an SQLException is throw.
     */
  public void insert(Patient patient) throws SQLException {
    if (dbConn != null) {
      String query = "INSERT INTO patient VALUES (?,?,?,?,?,?,?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.setInt(1, patient.getPatientId());
      prep.setString(2, "name");
      prep.setString(3, "insurance");
      prep.setString(4, "insurance_number");
      prep.setString(5, "doctor");
      prep.setDate(6, java.sql.Date.valueOf("2000-05-07"));
      prep.setString(SEVEN, "(917)-443-6682");
      prep.addBatch();
      prep.executeBatch();
      prep.close();
    }
  }

    /**
     * Updates a specific field with a specific value of a patient the database.
     * @param field
     *              the field to be updated
     * @param value
     *              the value the field should be updated
     * @param patient
     *               the patient object with new information to be updated
     * @throws SQLException
     *          thrown when an SQLException is throw.
     */
  public void update(String field, String value,
                     Patient patient) throws SQLException {
    if (dbConn != null) {
      String query = "UPDATE patients SET ? = ? WHERE patientId = ?";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.setString(1, field);
      prep.setString(2, value);
      prep.setInt(3, patient.getPatientId());
      prep.executeUpdate();
      prep.close();
    }
  }
}
