package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.brown.cs.group1.patient.Patient;

/**
 * Public class that concerns the PatientDatabase. This class extends the
 * abstract class Database which implements the setDbConn method.
 */
public class PatientDatabase extends Database {
  private String path;
  private Connection dbConn;
  private static final int SEVEN = 7;

  /**
   * Constructor to database.
   * @param path
   *          the filepath to the database
   */
  public PatientDatabase(String path) {
    this.path = path;
    setDbConn(path, dbConn);
  }

  @Override
  public void create() throws SQLException {
    if (dbConn != null) {
      String query =
          "CREATE TABLE IF NOT EXISTS patient(" + "patientId INTEGER,"
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
    } else {
      System.out.println("Connection null");
    }
  }

  /**
   * Inserts a patient into the patient database.
   * @param patient
   *          a new patient to be added to the patient database.
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
   *          the field to be updated
   * @param value
   *          the value the field should be updated
   * @param patient
   *          the patient object with new information to be updated
   * @throws SQLException
   *           thrown when an SQLException is throw.
   */
  public void update(String field, String value, Patient patient)
      throws SQLException {
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

  /**
   * Method to retrieve patient with the patient id.
   *
   * @param id
   *          the patient id.
   * @return a patient object corresponding to the input id. If no patient exist
   *         in the database the method would return null.
   *
   * @throws SQLException
   *           thrown when a SQLExpection is throw within method
   */
  public Patient getPatient(String id) throws SQLException {
    if (dbConn != null) {

      String query = "SELECT * FROM patient WHERE (patientId = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setString(1, id);

      ResultSet rs = prep.executeQuery();
      Patient patient = null;

      while (rs.next()) {
        // TODO: HAVE TO UPDATE DEPENDING ON PATIENT OBJ.
        // String name = rs.getString(2);
        // String insurance = rs.getString(3);
        // String insuranceNum = rs.getString(4);
        // String doctor = rs.getString(5);
        // Date date = rs.getDate(6);
        // String phone = rs.getString(7);
        // patient = new Patient(Integer.parseInt(id));
      }

      rs.close();
      prep.close();
      return patient;
    }
    return null;
  }
}
