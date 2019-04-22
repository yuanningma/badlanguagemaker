package edu.brown.cs.group1.template;

import java.sql.Connection;

/**
 * Template class provides ability to create new forms and customize fields.
 * Adds ability to save template as a blank form to the database. Adds ability
 * to update field values of forms.
 * @author wchoi11
 *
 */
public class Template {
  private int templateId;
  private Connection conn;

  /**
   * Constructor.
   * @param conn
   *          Connection to respective form database.
   * @param templateId
   *          Form id.
   */
  public Template(Connection conn, int templateId) {
    this.templateId = templateId;
    this.conn = conn;
  }

  // TODO: Method for extracting fields and associated values from existing
  // form.

  /**
   * Updates specified field's value in database.
   * @param fieldName
   *          Field name.
   * @param val
   *          Value to update with.
   */
  public void updateField(String fieldName, String val) {
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

  /**
   * Updates specified field's value in database.
   * @param fieldName
   *          Field name.
   * @param val
   *          Value to update with.
   */
  public void updateField(String fieldName, int val) {
    // try {
    // PreparedStatement prep;
    // prep = conn.prepareStatement("UPDATE Form SET ? = ? WHERE formId = ?;");
    // prep.setString(1, fieldName);
    // prep.setInt(2, val);
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
