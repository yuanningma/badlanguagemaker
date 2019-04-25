package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.template.Template;

/**
 * Public class that concerns the PatientDatabase. This class extends the
 * abstract class Database which implements the setDbConn method.
 * <p>
 * Note the difference between a template and form. A template refers to the
 * list of field names, but does not have any fields filled out. A form is a
 * template but with all fields filled out.
 */
public class FormsDatabase extends Database {
  private Connection dbConn;

  /**
   * Constructor for Form Database.
   * @param path
   *          a string that represents the path of the database.
   */
  public FormsDatabase(String path) {
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
   * Saves form inputs into the FormsDatabase.
   * @param template
   *          the template to be saved.
   */
  public void saveForm(Template template) {
    if (dbConn != null) {
      try {
        PreparedStatement prep;
        String query = "CREATE TABLE IF NOT EXISTS form(" + "formId INTEGER,"
            + "patientId TEXT,"
            + "form_input TEXT,"
            + "PRIMARY KEY (formId)) Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP;";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        // HashSet<String> columns = getColumnsInfo();
        List<String> formInfo = new ArrayList<>();
        // TODO: need a way to extract info
      } catch (SQLException sql) {
        sql.printStackTrace();
      }
    }
  }

  // /**
  // * Get the columns existing in the forms database.
  // * @return a HashSet containing all the columns names present in the
  // database.
  // */
  // public HashSet<String> getColumnsInfo() {
  // if (dbConn != null) {
  // try {
  // HashSet<String> columnName = new HashSet<>();
  // PreparedStatement prep;
  // String query = "SELECT * FROM forms";
  // prep = dbConn.prepareStatement(query);
  //
  // ResultSet rs = prep.executeQuery(query);
  // ResultSetMetaData rsmd = prep.getMetaData();
  // int columnCount = rsmd.getColumnCount();
  //
  // for (int i = 0; i < columnCount; i++) {
  // columnName.add(rsmd.getColumnName(i));
  // }
  //
  // rs.close();
  // prep.close();
  // return columnName;
  //
  // } catch (SQLException sql) {
  // sql.printStackTrace();
  // }
  // }
  // return null;
  // }

  // [NOTE] The below methods assume (for now) that only one forms table exists
  // in the database. I'm thinking that when we
  // implement multiple form tables one part of the form id must be parsed to
  // determine which table to query from. This should be easy to implement by
  // extending the below methods.

  /**
   * Returns all completed forms for specified patient.
   * @param patientId
   *          Patient's id.
   * @return List of completed forms for patient. Empty list if no forms have
   *         been completed for this patient.
   */
  public List<Template> getAllForms(int patientId) {
    List<Integer> formIds = new ArrayList<>();
    try (PreparedStatement prep = dbConn.prepareStatement(
        "SELECT form FROM patient_form WHERE patient = ?;");) {
      prep.setInt(1, patientId);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        formIds.add(rs.getInt(1));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    List<Template> forms = new ArrayList<>();
    for (int formId : formIds) {
      // Could optimize by adding SELECT queries to batch and executing batch
      // all at once.
      Template template = getForm(formId);
      forms.add(template);
    }
    return forms;
  }

  /**
   * Returns form of specified id.
   * @param formId
   *          Id of form.
   * @return Completed form of given id. Throws error if no form of exists.
   */
  public Template getForm(int formId) {
    // try (PreparedStatement prep = dbConn.prepareStatement("SELECT fields FROM
    // forms WHERE id = ?;");) {
    // prep.setInt(1, formId);
    // ResultSet rs = prep.executeQuery();
    // String fields = "";
    // while (rs.next()) {
    // fields = rs.getString(1);
    // }
    // List<String> parsedFields = parseFields(fields, true);
    // Template form = new Template(formId, parsedFields);
    // rs.close();
    // return form;
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    return null;
  }

  /**
   * Updates specified field's value in database.
   * @param fieldName
   *          Field name.
   * @param val
   *          Value to update with.
   */
  public void updateField(int formId, String fieldName, String val) {
    // Template form = getForm(formId);
    // List<String> fields = form.getFields();
    // for (int i = 0; i < fields.size(); i += 2) {
    // if (fields.get(i).equals(fieldName)) {
    // fields.set(i + 1, val);
    // }
    // }
    // saveForm(form);

    // try {
    // PreparedStatement prep;
    // prep = conn.prepareStatement("UPDATE Form SET ? = ? WHERE formId = ?;");
    // prep.setString(1, fieldName);
    // prep.setString(2, val);
    // prep.setInt(3, templateId);
    // ResultSet rs = prep.executeQuery();
    // while (rs.next()) {
    //
    // }
    // rs.close();
    // prep.close();
    // } catch (SQLException e) {
    //
    // }

  }
}
