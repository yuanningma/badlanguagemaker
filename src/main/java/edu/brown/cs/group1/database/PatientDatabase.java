package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.patient.Patient;

/**
 * Public class that concerns the PatientDatabase. This class extends the
 * abstract class Database which implements the setDbConn method.
 */
public class PatientDatabase extends Database {
  private Connection dbConn;

  /**
   * Constructor to database.
   * @param path
   *          the filepath to the database
   */
  public PatientDatabase(String path) {
    try {
      Class.forName("org.sqlite.JDBC");
      String url = "jdbc:sqlite:" + path;
      dbConn = DriverManager.getConnection(url);
      Statement stat = dbConn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys = ON;");
      stat.close();

    } catch (ClassNotFoundException exp) {
      System.out.println("ERROR: ClassNotFoundException PatientDatabase.java");
      // exp.printStackTrace();

    } catch (SQLException sql) {
      System.out.println("ERROR: SQLExeption PatientDatabase.java");
      // sql.printStackTrace();
    }
  }

  /**
   * Method that saves new patients into the to the database.
   * @param patient
   *          the new patient member to be inserted
   * @throws SQLException
   *           thrown when a SQLException is thrown
   */
  public void savePatients(Patient patient) throws SQLException {
    if (dbConn != null) {
      String query =
          "CREATE TABLE IF NOT EXISTS patient(" + "patientId INTEGER,"
              + "first_name TEXT,"
              + "middle_name TEXT,"
              + "last_name TEXT,"
              + "doctorID INTEGER,"
              + "PRIMARY KEY (patientId));";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();
      query = "INSERT INTO patient VALUES (?,?,?,?,?);";
      prep = dbConn.prepareStatement(query);
      prep.setInt(1, patient.getPatientId());
      prep.setString(2, patient.getFirstName());
      prep.setString(3, patient.getMiddleName());
      prep.setString(4, patient.getLastName());
      prep.setInt(5, patient.getDoctorId());
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
      String query = new String();
      switch (field) {
      case "first_name":
        query = "UPDATE patient SET first_name = ? WHERE patientId = ?";
        break;
      case "middle_name":
        query = "UPDATE patient SET middle_name = ? WHERE patientId = ?";
        break;
      case "last_name":
        query = "UPDATE patient SET last_name = ? WHERE patientId = ?";
        break;
      case "doctorID":
        query = "UPDATE patient SET doctorID = ? WHERE patientId = ? ";
        break;
      default:
        query = null;
      }
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      if (!field.equals("doctorID")) {
        prep.setString(1, value);
      } else {
        prep.setInt(1, Integer.parseInt(value));
      }
      prep.setInt(2, patient.getPatientId());
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
  public Patient getPatient(Integer id) throws SQLException {
    if (dbConn != null) {

      String query = "SELECT * FROM patient WHERE (patientId = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setInt(1, id);

      ResultSet rs = prep.executeQuery();
      Patient patient = null;

      while (rs.next()) {
        String firstName = rs.getString(2);
        String middleName = rs.getString(3);
        String lastName = rs.getString(4);
        Integer doctor = rs.getInt(5);
        patient = new Patient(id, firstName, middleName, lastName, doctor);
      }

      rs.close();
      prep.close();
      return patient;
    }
    return null;
  }

  /**
   * Method to retrieve all patients with particular doctor.
   * @param id
   *          the doctor id
   * @return a list of patients of the doctor with the corresponding id.
   * @throws SQLException
   *           thrown when a SQLException is thrown
   */
  public List<String[]> getAllPatients(Integer id) throws SQLException {
    if (dbConn != null) {
      String query = "SELECT * FROM patient WHERE (doctorID = ?)";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.setInt(1, id);

      ResultSet rs = prep.executeQuery();
      List<String[]> patients = new ArrayList<String[]>();

      while (rs.next()) {
        Integer patientID = rs.getInt(1);
        String firstName = rs.getString(2);
        String middleName = rs.getString(3);
        String lastName = rs.getString(4);

        String name =
            rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);

        String[] line = new String[] {
            firstName, middleName, lastName, patientID.toString()
        };

        // String patient =
        // firstName + " " + lastName + " " + patientID.toString();
        patients.add(line);
      }

      rs.close();
      prep.close();
      return patients;
    }
    return null;
  }

  /**
   * Deletes a patient from the patients database.
   * @param patientID
   *          the patientID of the patient being deleted.
   * @throws SQLException
   *           throws a SQLExpection when an errors occurs when interacting with
   *           the database.
   */
  public void deletePatient(Integer patientID) throws SQLException {
    if (dbConn != null) {

      String query = "DELETE FROM patient WHERE (patientId = ?);";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);

      prep.setInt(1, patientID);

      prep.executeQuery();

      prep.close();
    }
  }

  /**
   * Retrieves a list of patients info that contain a name. Uses in search bar
   * in GUI.
   * @param name
   *          a name associated with a patients
   * @return a list of strings args filled with patient information.
   * @throws SQLException
   *           SQLExpection when a error is thrown.
   */
  public List<String[]> getPatientNameMatch(String name) throws SQLException {

    if (dbConn != null) {

      String query =
          "SELECT * FROM patient WHERE first_name = ? OR middle_name=? OR last_name=? OR patientId=?;";
      PreparedStatement prep;

      if (name.contains(" ")) {
        String[] fullSearch = name.split(" ");
        prep = dbConn.prepareStatement(query);

        for (String s : fullSearch) {

          prep.setString(1, s);
          prep.setString(2, s);
          prep.setString(3, s);
          prep.setString(4, s);
        }

      } else {
        prep = dbConn.prepareStatement(query);

        prep.setString(1, name);
        prep.setString(2, name);
        prep.setString(3, name);
        prep.setString(4, name);
      }

      ResultSet rs = prep.executeQuery();
      List<String[]> patients = new ArrayList<String[]>();
      while (rs.next()) {
        Integer id = rs.getInt(1);
        String firstName = rs.getString(2);
        String middleName = rs.getString(3);
        String lastName = rs.getString(4);
        String[] line = new String[] {
            firstName, middleName, lastName, id.toString()
        };
        patients.add(line);
      }

      rs.close();
      prep.close();

      return patients;

    } else {
      return null;
    }
  }
}
