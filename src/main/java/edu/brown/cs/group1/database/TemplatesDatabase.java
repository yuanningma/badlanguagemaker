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
import edu.brown.cs.group1.template.Template;

/**
 * Public class that concerns the PatientDatabase. This class extends the
 * abstract class Database which implements the setDbConn method.
 * <p>
 * Note the difference between a template and form. A template refers to the
 * list of field names, but does not have any fields filled out. A form is a
 * template but with all fields filled out.
 */
public class TemplatesDatabase extends Database {
  private Connection dbConn;

  /**
   * Constructor for Template Database.
   * @param path
   *          a string that represents the path of the database.
   */
  public TemplatesDatabase(String path) {
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
   * Saves template fields into the TemplatesDatabase.
   * @param template
   *          the template to be saved.
   */
  public void saveTemplate(Template template) {
    if (dbConn != null) {
      try {
        PreparedStatement prep;
        String query = "CREATE TABLE IF NOT EXISTS form(" + "formId INTEGER,"
            + "patientId TEXT,"
            + "form_input TEXT,"
            + "PRIMARY KEY (formId));";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        // HashSet<String> columns = getColumnsInfo();
        List<String> templateInfo = new ArrayList<>();
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

  // /**
  // * Returns all completed forms for specified patient.
  // * @param patientId
  // * Patient's id.
  // * @return List of completed forms for patient. Empty list if no forms have
  // * been completed for this patient.
  // */
  // public List<Template> getAllForms(int patientId) {
  // List<Integer> formIds = new ArrayList<>();
  // try (PreparedStatement prep = dbConn.prepareStatement(
  // "SELECT form FROM patient_form WHERE patient = ?;");) {
  // prep.setInt(1, patientId);
  // ResultSet rs = prep.executeQuery();
  // while (rs.next()) {
  // formIds.add(rs.getInt(1));
  // }
  // rs.close();
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // List<Template> forms = new ArrayList<>();
  // for (int formId : formIds) {
  // // Could optimize by adding SELECT queries to batch and executing batch
  // // all at once.
  // Template template = getForm(formId);
  // forms.add(template);
  // }
  // return forms;
  // }

  /**
   * Returns blank template.
   * @param templateId
   *          Id of template.
   * @return Blank template of specified id. Throws error if no such template
   *         exists.
   */
  public Template getTemplate(int templateId) {
    try (PreparedStatement prep = dbConn
        .prepareStatement("SELECT form_input FROM templates WHERE id = ?;");) {
      prep.setInt(1, templateId);
      ResultSet rs = prep.executeQuery();
      String fields = "";
      while (rs.next()) {
        fields = rs.getString(1);
      }
      TemplateFields parsedFields = TemplateFields.valueOf(fields);
      Template template = new Template(templateId, parsedFields);
      rs.close();
      return template;
    } catch (

    SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Updates specified field's name in database.
   * @param templateId
   *          Template id.
   * @param oldFieldName
   *          Old field name.
   * @param newFieldName
   *          New field name.
   */
  public void

      updateField(int templateId, String oldFieldName, String newFieldName) {
    // Template template = getTemplate(templateId);
    // List<String> fields = template.getFields();
    // for (int i = 0; i < fields.size(); i++) {
    // if (fields.get(i).equals(oldFieldName)) {
    // fields.set(i, newFieldName);
    // }
    // }
    // saveTemplate(form);

  }

  /**
   * Returns all templates from database.
   * @return List of templates. Empty list if no templates.
   */
  public List<Template> getAllTemplates() {
    List<Template> templates = new ArrayList<>();
    try (PreparedStatement prep =
        dbConn.prepareStatement("SELECT * FROM templates;");) {
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        int formId = rs.getInt(1);
        String fieldsString = rs.getString(2);
        TemplateFields fields = TemplateFields.valueOf(fieldsString);
        templates.add(new Template(formId, fields));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return templates;
  }
}
