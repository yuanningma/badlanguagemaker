package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class FormsDatabase extends Database {
  private Connection dbConn;
  private TagsDatabase tb = new TagsDatabase("data/database/tags.sqlite3");
  private Map<Integer, Template> templateMap;
  private int nextFormId = 101;

  /**
   * Constructor for Form Database.
   * @param path
   *          a string that represents the path of the database.
   */
  public FormsDatabase(String path) {
    templateMap = new HashMap<Integer, Template>();
    try {
      Class.forName("org.sqlite.JDBC");
      String url = "jdbc:sqlite:" + path;
      dbConn = DriverManager.getConnection(url);
      Statement stat = dbConn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys = ON;");
      stat.close();

    } catch (ClassNotFoundException exp) {
      System.out.println("ClassNotFoundException FormsDatabase.java");
      // exp.printStackTrace();

    } catch (SQLException sql) {
      System.out.println("SQLException FormsDatabase.java");
      // sql.printStackTrace();
    }

  }

  /**
   * Saves form inputs into the FormsDatabase.
   * @param template
   *          the template to be saved.
   */
  public Template saveForm(Template template, Integer patientid) {
    template.setTemplateId(nextFormId);
    nextFormId++;
    Template newTempl = template;
    if (dbConn != null) {
      try {
        PreparedStatement prep;
        String query =
            "CREATE TABLE IF NOT EXISTS form(" + "formId INTEGER PRIMARY KEY,"
                + "patientId INTEGER,"
                + "form_input TEXT,"
                + "tags TEXT,"
                + "form_name TEXT,"
                + "database_input Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        // HashSet<String> columns = getColumnsInfo();
        List<String> formInfo = new ArrayList<>();
        query = "INSERT INTO form VALUES (?,?,?,?,?,null);";
        prep = dbConn.prepareStatement(query);
        prep.setInt(1, newTempl.getTemplateId());
        prep.setInt(2, patientid);
        formInfo.addAll(template.getFields().getContent());
        List<String> tagList = new ArrayList<>();
        // TODO: Tag list for a Template!!!
        for (String formInput : formInfo) {

          String tag = tb.getTag(formInput);
          if (tag != null) {
            tagList.add(tag);
          }
        }
        prep.setString(3, formInfo.toString());
        prep.setString(4, tagList.toString());
        prep.setString(5, template.getTemplateName());
        prep.addBatch();
        prep.executeBatch();
        prep.close();
        newTempl.setTags(tagList);

      } catch (SQLException sql) {
        //System.out.println("SQL Exception FormsDatabase saveForm");
         sql.printStackTrace();
      }
    }
    templateMap.put(patientid, newTempl);
    return newTempl;
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
        dbConn.prepareStatement("SELECT * FROM form;");) {

      ResultSet rs = prep.executeQuery();
      while (rs.next()) {

        int formId = rs.getInt(1);

        if (templateMap.containsKey(formId)) {
          forms.add(templateMap.get(formId));
        } else {
          String name = rs.getString(5);
          String fieldsString = rs.getString(3);
          TemplateFields fields = TemplateFields.valueOf(fieldsString);
          forms.add(new Template(formId, fields, name));
        }
      }
      rs.close();
    } catch (SQLException e) {

      System.out.println("SQL Exception FormsDatabase getAllForms");
      // e.printStackTrace();
    }
    return forms;
  }

  public void dummyMethod(int patientId) throws SQLException {
    try (PreparedStatement prep = dbConn.prepareStatement("SELECT formId FROM form WHERE patientId = ?;");) {
      prep.setInt(1, patientId);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        System.out.println("DUMMY ID: " + rs.getInt(1));
      }
    }
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
    try (PreparedStatement prep = dbConn.prepareStatement("SELECT * FROM form WHERE patientId = ?;");) {
      prep.setInt(1, patientId);
      System.out.println("PATIENT ID IS: " + patientId);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {

        Integer formID = rs.getInt(1);

        System.out.println("THIS PATIENT CONTAINS FORM: " + formID);
        if (templateMap.containsKey(formID)) {
          forms.add(templateMap.get(formID));
        } else {
          String name = rs.getString(5);
          String formInput =
              rs.getString(3).substring(1, rs.getString(2).length());
          TemplateFields fields = TemplateFields.valueOf(formInput);
          forms.add(new Template(formID, fields, name));
          // System.out.println(formID);
          // System.out.println("CONTENTO: " + fields.getContent());
        }
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("SQL Exception FormsDatabase getAllForms(id)");
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
    if (templateMap.containsKey(formId)) {
      // System.out.println("We already have it in store!");
      return templateMap.get(formId);
    }
    Template form = new Template(-1,
        new TemplateFields(new ArrayList<>()),
        new String());
    try (PreparedStatement prep = dbConn.prepareStatement("SELECT * FROM form WHERE formId = ?;");) {

      prep.setInt(1, formId);
      ResultSet rs = prep.executeQuery();
      String fields = new String();
      String name = new String();
      while (rs.next()) {
        name = rs.getString(5);
        fields = rs.getString(3);
        System.out.println("IN DB, FIELDS IS: " + fields);
      }
      TemplateFields parsedFields = TemplateFields.valueOf(fields.replaceAll("\\[",
          "")
          .replaceAll("\\]", ""));
      form = new Template(formId, parsedFields, name);
      rs.close();
      return form;
    } catch (SQLException e) {
      System.out.println("SQL Exception FormsDatabase getForm(id)");
      // e.printStackTrace();
    }
    System.out.println(form.getTemplateId());
    return form;
  }

  /**
   * Updates a form in the database.
   * @param formId
   *          form id of the form to be replace
   * @param newFormInput
   *          form input with new values
   */
  public void updateForm(int formId, Template newFormInput) {
    try {
      PreparedStatement prep;
      prep = dbConn.prepareStatement("UPDATE form SET form_input = ? WHERE formId = ?;");
      prep.setString(1, newFormInput.toString());
      prep.setInt(2, formId);
      prep.executeQuery();
      prep = dbConn.prepareStatement("UPDATE form SET tags = ? WHERE formId = ?;");
      List<String> formContent = newFormInput.getFields().getContent();
      // LIST OF TAGS
      List<String> tagList = new ArrayList<>();
      for (String formInput : formContent) {
        String tag = tb.getTag(formInput);
        if (tag != null) {
          tagList.add(tag);
        }
      }
      prep.setString(1, tagList.toString());
      prep.setInt(2, formId);
      prep.addBatch();
      prep.executeBatch();
      prep.close();
      // Template form = new Template(formId,
      // new TemplateFields(new ArrayList<>()));
      //
      //
      //
      //
      //
      // query = "INSERT INTO form VALUES (?,?,?,?);";
      // prep = dbConn.prepareStatement(query);
      // prep.setInt(1, newTempl.getTemplateId());
      // prep.setInt(2, patientid);
      // formInfo.addAll(template.getFields().getContent());
      // List<String> tagList = new ArrayList<>();
      // // TODO: Tag list for a Template!!!
      // for (String formInput : formInfo) {
      // String tag = tb.getTag(formInput);
      // if (tag != null) {
      // tagList.add(tag);
      // }
      // }
      // prep.setString(3, formInfo.toString());
      // prep.setString(4, tagList.toString());
      // prep.addBatch();
      // prep.executeBatch();
      // prep.close();

    } catch (SQLException e) {
      System.out.println("SQL Exception FormsDatabase updateForm");
      // e.printStackTrace();
    }
  }
}
