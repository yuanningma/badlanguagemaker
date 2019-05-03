package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.main.java.edu.brown.cs.group1.database.Database;
import src.main.java.edu.brown.cs.group1.field.TemplateFields;
import src.main.java.edu.brown.cs.group1.template.Template;

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
  private int nextTempId = 15;

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
      System.out.println("ERROR: ClassNotFoundExeption TemplatesDatabase.java");
      // exp.printStackTrace();

    } catch (SQLException sql) {
      System.out.println("ERROR: SQLExeption Database.java");
      // sql.printStackTrace();
    }
  }

  /**
   * Saves template fields into the TemplatesDatabase.
   * @param template
   *          the template to be saved.
   */
  public void saveTemplate(Template template) {
    template.setTemplateId(nextTempId);
    nextTempId++;
    if (dbConn != null) {
      try (
          PreparedStatement prep1 = dbConn.prepareStatement(
              "CREATE TABLE IF NOT EXISTS template(" + "templateId INTEGER,"
                  + "template_name TEXT,"
                  + "template_field TEXT,"
                  + "PRIMARY KEY (templateId));");
          PreparedStatement prep = dbConn
              .prepareStatement("INSERT INTO template VALUES (?,?,?);");) {
        prep1.executeUpdate();
        List<String> templateInfo = template.getFields().getLabels(false);
        prep.setInt(1, template.getTemplateId());
        prep.setString(2, template.getTemplateName());
        prep.setString(3, templateInfo.toString());
        prep.addBatch();
        prep.executeBatch();
        prep.close();
      } catch (SQLException sql) {
        System.out.println("SQLException saveTemplate");
        sql.printStackTrace();
      }
    }
  }

  /**
   * Returns true if template fields are successfully saved into the
   * TemplatesDatabase.
   * @param template
   *          Template to be saved.
   * @return Whether template is successfully saved.
   */
  public boolean saveTemplateBoolean(Template template) {
    template.setTemplateId(nextTempId);
    nextTempId++;
    if (dbConn != null) {
      try (
          PreparedStatement prep1 = dbConn.prepareStatement(
              "CREATE TABLE IF NOT EXISTS template(" + "templateId INTEGER,"
                  + "template_name TEXT,"
                  + "template_field TEXT,"
                  + "PRIMARY KEY (templateId));");
          PreparedStatement prep = dbConn
              .prepareStatement("INSERT INTO template VALUES (?,?,?);");) {
        prep1.executeUpdate();
        List<String> templateInfo = template.getFields().getLabels(false);
        prep.setInt(1, template.getTemplateId());
        prep.setString(2, template.getTemplateName());
        prep.setString(3, templateInfo.toString());
        prep.addBatch();
        prep.executeBatch();
        prep.close();
        return true;
      } catch (SQLException sql) {
        System.out.println("SQLException saveTemplate");
        sql.printStackTrace();
        return false;
      }
    }
    return false;
  }

  /**
   * Returns blank template.
   * @param templateId
   *          Id of template.
   * @return Blank template of specified id. Throws error if no such template
   *         exists.
   */
  public Template getTemplate(int templateId) {
    try (PreparedStatement prep = dbConn
        .prepareStatement("SELECT * FROM template WHERE templateId = ?;");) {
      prep.setInt(1, templateId);
      ResultSet rs = prep.executeQuery();
      String fields = "";
      String name = new String();
      while (rs.next()) {
        name = rs.getString(2);
        fields = rs.getString(3);
      }
      fields = fields.replaceAll("\\[", "").replaceAll("\\]", "");
      TemplateFields parsedFields = TemplateFields.valueOf(fields);
      Template template = new Template(templateId, parsedFields, name);
      rs.close();
      return template;
    } catch (SQLException e) {
      System.out.println("SQLException getTemplate");
      // e.printStackTrace();
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
        dbConn.prepareStatement("SELECT * FROM template;");) {
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        int formId = rs.getInt(1);
        String name = rs.getString(2);
        String fieldsString = rs.getString(3);
        fieldsString = fieldsString.replaceAll("\\[", "").replaceAll("\\]", "");
        TemplateFields fields = TemplateFields.valueOf(fieldsString);
        templates.add(new Template(formId, fields, name));
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("SQLException getAllTemplates");
      // e.printStackTrace();
    }
    return templates;
  }
}
