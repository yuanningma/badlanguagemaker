package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.tag.Tags;
import edu.brown.cs.group1.template.Template;
import edu.brown.cs.group1.patient.Patient;

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
  public void saveForm(Template template, Patient patient, Tags tags) {
    if (dbConn != null) {
      try {
        PreparedStatement prep;
        String query = "CREATE TABLE IF NOT EXISTS form("
            + "formId INTEGER,"
            + "patientId INTEGER,"
            + "form_input TEXT,"
            + "tags TEXT"
            + "PRIMARY KEY (formId)) Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP;";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        // HashSet<String> columns = getColumnsInfo();
        List<String> formInfo = new ArrayList<>();
        // TODO: need a way to extract info
        query = "INSERT INTO form VALUES (?,?,?);";
        prep.setInt(1, template.getTemplateId());
        prep.setInt(2, patient.getPatientId());
        formInfo.addAll(template.getFields().getContent());
        List<String> tagList = new ArrayList<>();
        for (String formInput : formInfo) {
          if (tags.containsKeyword(formInput)) {
            tagList.add(tags.getTag(formInput));
          }
        }
        prep.setString(3, formInfo.toString());
        prep.setString(4, tagList.toString());
      } catch (SQLException sql) {
        sql.printStackTrace();
      }
    }
  }

  // [NOTE] The below methods assume (for now) that only one forms table exists
  // in the database. I'm thinking that when we
  // implement multiple form tables one part of the form id must be parsed to
  // determine which table to query from. This should be easy to implement by
  // extending the below methods.

  /**
   * Returns all completed forms from database.
   * @return List of completed forms. Empty list if no forms.
   */
  public List<Template> getAllForms() {
    List<Template> forms = new ArrayList<>();
    try (PreparedStatement prep =
        dbConn.prepareStatement("SELECT * FROM forms;");) {
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        int formId = rs.getInt(1);
        String fieldsString = rs.getString(2);
        TemplateFields fields = TemplateFields.valueOf(fieldsString);
        forms.add(new Template(formId, fields));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return forms;
  }

  /**
   * Returns all completed forms for specified patient.
   * @param patientId
   *          Patient's id.
   * @return List of completed forms for patient. Empty list if no forms have
   *         been completed for this patient.
   */
  public List<Template> getAllForms(int patientId) {
    List<Template> forms = new ArrayList<>();
    try (PreparedStatement prep = dbConn.prepareStatement(
        "SELECT formId,form_input FROM form WHERE patientId = ?;");) {
      prep.setInt(1, patientId);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        Integer formID = rs.getInt(1);
        String formInput = rs.getString(2);
        TemplateFields fields = TemplateFields.valueOf(formInput);
        forms.add(new Template(formID, fields));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
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
    Template form = new Template(-1, new TemplateFields(new ArrayList<>()));
    try (PreparedStatement prep = dbConn.
             prepareStatement("SELECT form_input FROM forms WHERE id = ?;");) {
      prep.setInt(1, formId);
      ResultSet rs = prep.executeQuery();
      String fields = new String();
      while (rs.next()) {
        fields = rs.getString(1);
      }
      TemplateFields parsedFields = TemplateFields.valueOf(fields);
      form = new Template(formId, parsedFields);
      rs.close();
      return form;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return form;
  }

  /**
   * Updates a form in the database.
   * @param formId
   *          form id of the form to be replace
   * @param newFormInput
   *          form input with new values
   */
  public void updateForm(int formId, List<String> newFormInput) {
    try {
      PreparedStatement prep;
      prep = dbConn.prepareStatement("UPDATE form SET form_input = ? WHERE formId = ?;");
      prep.setString(1, newFormInput.toString());
      prep.setInt(2, formId);
      prep.executeQuery();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
