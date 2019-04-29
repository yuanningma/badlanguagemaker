package edu.brown.cs.group1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * TagsDatabase provides ability to view and modify tags database. This class
 * extends the abstract class Database which implements the setDbConn method.
 * <p>
 * Tags table has two columns: tag name and associated keyword.
 * @author wchoi11
 *
 */
public class TagsDatabase extends Database {
  private Connection dbConn;

  /**
   * Constructor for Form Database.
   * @param path
   *          a string that represents the path of the database.
   */
  public TagsDatabase(String path) {
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
   * Returns all tags and associated keywords from database.
   * @return List of tags and associated keywords. Empty list if no tags.
   */
  public List<String> getAllTags() {
    List<String> tags = new ArrayList<>();
    try (PreparedStatement prep =
        dbConn.prepareStatement("SELECT * FROM tags;");) {
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        tags.add(rs.getString(1));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tags;
  }

  /**
   * Returns whether keyword is in tags map.
   * @param keyword
   *          Keyword to check.
   * @return true if keyword exists in tags map. false otherwise.
   */
  public boolean containsKey(String keyword) {
    try (PreparedStatement prep = dbConn.prepareStatement(
        "SELECT COUNT(Keyword) FROM tags WHERE Keyword = ?;")) {
      prep.setString(1, keyword);
      ResultSet rs = prep.executeQuery();
      int count = 0;
      while (rs.next()) {
        count += rs.getInt(1);
      }
      if (count == 0) {
        return false;
      }
      return true;
    } catch (SQLException e) {

    }
    return false;
  }

  /**
   * Registers new tag with associated keyword to map of tags to keywords. This
   * method does not register a new tag if the new keyword already exists in the
   * map.
   * @param tag
   *          New tag.
   * @param keyword
   *          New keyword.
   */
  public void registerNewTag(String tag, String keyword) throws SQLException {
    if (!containsKey(keyword)) {
      PreparedStatement prep;
      String query = "CREATE TABLE IF NOT EXISTS tags("
                + "Tags TEXT,"
                + "Keyword TEXT,"
                + "PRIMARY KEY (keyword));";
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();
      query = "INSERT INTO tags VALUES (?, ?);";
      prep = dbConn.prepareStatement(query);
      prep.setString(1, tag);
      prep.setString(2, keyword);
      prep.addBatch();
      prep.executeBatch();
      prep.close();
    }
  }

    /**
     * Get the respective tag of a keyword.
     * @param keyword
     *              a keyword to be inputted.
     * @return
     *        a tsg associated with a key word.
     * @throws SQLException
     *          thrown when an SQLExpection is thrown.
     */
  public String getTag(String keyword) throws SQLException {
    if (containsKey(keyword)) {
      PreparedStatement prep;
      String query = "SELECT Tags FROM tags WHERE (keyword = ?)";
      prep = dbConn.prepareStatement(query);
      prep.setString(1, keyword);
      ResultSet rs = prep.executeQuery();
      String tag = new String();
      while (rs.next()) {
          tag = rs.getString(1);
      }
      return tag;
    }
    //TODO: MAY WANT TO PUT IN A CASE FOR SEARCH BAR
    return null;
  }
}
