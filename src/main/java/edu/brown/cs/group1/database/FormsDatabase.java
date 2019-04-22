package edu.brown.cs.group1.database;

import edu.brown.cs.group1.template.Template;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Public class that concerns the PatientDatabase. This class extends the
 * abstract class Database which implements the setDbConn method.
 *  */
public class FormsDatabase extends Database {
  private Connection dbConn;

    /**
     * Constructor for Template Database.
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
  public void saveTemplate(Template template) {
    if (dbConn != null) {
      try {
        PreparedStatement prep;
        String query = "CREATE TABLE IF NOT EXISTS form("
                + "formId INTEGER,"
                + "patientId TEXT,"
                + "form_input TEXT,"
                + "PRIMARY KEY (formId));";
        prep = dbConn.prepareStatement(query);
        prep.executeUpdate();
        HashSet<String> columns = getColumnsInfo();
        List<String> templateInfo = new ArrayList<>();
        // TODO: need a way to extract info
      } catch (SQLException sql) {
        sql.printStackTrace();
      }
    }
  }

  /**
   * Get the columns existing in the forms database.
   * @return
   *        a HashSet containing all the columns names
   *        present in the database.
   */
  public HashSet<String> getColumnsInfo() {
    if (dbConn != null) {
      try {
        HashSet<String> columnName = new HashSet<>();
        PreparedStatement prep;
        String query = "SELECT * FROM forms";
        prep = dbConn.prepareStatement(query);

        ResultSet rs =  prep.executeQuery(query);
        ResultSetMetaData rsmd = prep.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
          columnName.add(rsmd.getColumnName(i));
        }

        rs.close();
        prep.close();
        return columnName;

      } catch (SQLException sql) {
        sql.printStackTrace();
      }
    }
    return null;
  }
}
