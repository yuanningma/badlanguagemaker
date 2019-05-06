package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
  private int nextFormId = 100;

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
      nextFormId = getCount() + nextFormId;
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
   * @param patientid
   *          the patient id.
   * @return the template being saved.
   */
  public Template saveForm(Template template, Integer patientid, String time) {
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
                + "database_input TEXT);";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        // HashSet<String> columns = getColumnsInfo();
        List<String> formInfo = new ArrayList<>();
        query = "INSERT INTO form VALUES (?,?,?,?,?,?);";
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
        SimpleDateFormat formatter =
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = formatter.format(new Date());
        prep.setString(6, time);
        prep.addBatch();
        prep.executeBatch();
        prep.close();
        newTempl.setTags(tagList);

      } catch (SQLException sql) {
        // System.out.println("SQL Exception FormsDatabase saveForm");
        sql.printStackTrace();
      }
    }
    templateMap.put(patientid, newTempl);
    return newTempl;
  }

  /**
   * Returns true if form inputs are successfully saved into the FormsDatabase.
   * @param template
   *          Template.
   * @param patientid
   *          Patient id.
   * @return Whether form was saved successfully.
   */
  public boolean saveFormBoolean(Template template, Integer patientid) {
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
                + "database_input TEXT);";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        // HashSet<String> columns = getColumnsInfo();
        List<String> formInfo = new ArrayList<>();
        query = "INSERT INTO form VALUES (?,?,?,?,?,?);";
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
        Date date = new Date();
       SimpleDateFormat formatter =
                  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String time = formatter.format(new Date());
        prep.setString(6, time);
        prep.addBatch();
        prep.executeBatch();
        prep.close();
        newTempl.setTags(tagList);
        newTempl.setTime(time);
        return true;
      } catch (SQLException sql) {
        // System.out.println("SQL Exception FormsDatabase saveForm");
        sql.printStackTrace();
        return false;
      }
    }
    templateMap.put(patientid, newTempl);
    return false;
  }

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
          fieldsString =
              fieldsString.replaceAll("\\[", "").replaceAll("\\]", "");
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
    try (PreparedStatement prep = dbConn
        .prepareStatement("SELECT formId FROM form WHERE patientId = ?;");) {
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
    try (PreparedStatement prep =
        dbConn.prepareStatement("SELECT * FROM form WHERE patientId = ?;");) {
      prep.setInt(1, patientId);
      // System.out.println("PATIENT ID IS: " + patientId);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {

        Integer formID = rs.getInt(1);

        // System.out.println("THIS PATIENT CONTAINS FORM: " + formID);
        if (templateMap.containsKey(formID)) {
          forms.add(templateMap.get(formID));
        } else {
          // <<<<<<< HEAD
          // // String name = rs.getString(2);
          // String name = "boogerface";
          // String numthree = rs.getString(3);
          // // System.out.println("Third is: " + numthree);
          // // String formInput = rs.getString(3).substring(1,
          // // rs.getString(3).length() - 1);
          // String formInput = numthree.substring(1, numthree.length() - 1);
          // // System.out.println("formInput is: " + formInput);
          // =======
          String name = rs.getString(5);
          String date = rs.getString(6);
          // String dates = rs.getString(6);
          // System.out.println("DATE IS: " + date);
          // System.out.println("STRING DATE IS: " + dates);
          String formInput =
              rs.getString(3).replaceAll("\\[", "").replaceAll("\\]", "");

          TemplateFields fields = TemplateFields.valueOf(formInput);
          Template newForm = new Template(formID, fields, name);
          newForm.setTime(date);
          newForm.setTimeForFront(rs.getString(6));
          forms.add(newForm);
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
    Template form =
        new Template(-1, new TemplateFields(new ArrayList<>()), new String());
    try (PreparedStatement prep =
        dbConn.prepareStatement("SELECT * FROM form WHERE formId = ?;");) {

      prep.setInt(1, formId);
      ResultSet rs = prep.executeQuery();
      String fields = new String();
      String name = new String();
      while (rs.next()) {
        name = rs.getString(5);
        fields = rs.getString(3);
        // System.out.println("IN DB, FIELDS IS: " + fields);
      }

      fields = fields.replaceAll("\\[", "").replaceAll("\\]", "");
      TemplateFields parsedFields = TemplateFields.valueOf(fields);
      form = new Template(formId, parsedFields, name);
      rs.close();
      return form;
    } catch (SQLException e) {
      System.out.println("SQL Exception FormsDatabase getForm(id)");
      // e.printStackTrace();
    }
    // System.out.println(form.getTemplateId());
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
      prep = dbConn
          .prepareStatement("UPDATE form SET form_input = ? WHERE formId = ?;");
      prep.setString(1, newFormInput.toString());
      prep.setInt(2, formId);
      prep.executeQuery();
      prep =
          dbConn.prepareStatement("UPDATE form SET tags = ? WHERE formId = ?;");
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

  /**
   * This method retrieved the count of forms currently in the database.
   * @return a integer representing the number of forms in the database.
   * @throws SQLException
   *           thrown when the a SQL Exception is thrown.
   */
  public Integer getCount() throws SQLException {
    Integer count = 0;
    if (dbConn != null) {
      PreparedStatement prep;
      String query = "SELECT COUNT(formId) FROM form;";
      prep = dbConn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        count = rs.getInt(1);
      }
      rs.close();
      prep.close();
    }
    return count;
  }
}
