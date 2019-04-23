package edu.brown.cs.group1.TerminologyDatabase;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import edu.brown.cs.group1.textloader.TextFileLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class interacts solely with the medical dictionary database.
 * This database would be creating using files provide by:
 * https://github.com/glutanimate/hunspell-en-med-glut/blob/master/en_med_gut.dic.
 * The file used is located at data/medicalTerminology/
 * en_med_glut.txt. This class seeks to create a database with the
 * files provide information and use SQLITE3 pattern matching to retrieve
 * needed data.
 */
public class MedicalDictionaryDatabase extends TerminologyDatabase {
  private Connection dbConn;
    /**
     * Constructor for MedicalDictionaryDatabase.
     * @param dbfilepath
     *              the path of the database file
     */
  public MedicalDictionaryDatabase(String dbfilepath) {
    try {
      Class.forName("org.sqlite.JDBC");
      String url = "jdbc:sqlite:" + dbfilepath;
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

  private static final int DICTIONARYBEGINS = 17;

  @Override
   public List<String> retrieveEntry(String filepath) {
    TextFileLoader loader = new TextFileLoader(filepath);
    List<String> retrieve = loader.fileLoader();
    List<String> toReturn = retrieve.subList(DICTIONARYBEGINS, retrieve.size());
    return toReturn;
  }

  @Override
   public void createDatabase(List<String> entry) throws SQLException {
    if (dbConn != null) {
      String query = "CREATE TABLE IF NOT EXISTS medical_dict("
                  + "termID INTEGER,"
                  + "term TEXT,"
                  + "PRIMARY KEY (termID));";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();
      for (String s : entry) {
        query = "INSERT INTO medical_dict VALUES (null,?);";
        prep = dbConn.prepareStatement(query);
        prep.setString(1, s);
        prep.addBatch();
        prep.executeBatch();
      }
      prep.close();
    }
  }
  @Override
  public List<String> query(TerminologyAssociation input) {
    //TODO: Implementation needed
    return new ArrayList<String>();
  }
}
