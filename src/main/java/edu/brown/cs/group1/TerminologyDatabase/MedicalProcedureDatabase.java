package edu.brown.cs.group1.TerminologyDatabase;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import edu.brown.cs.group1.textloader.TextFileLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class interacts solely with the medical procedure database.
 * This database would be creating using files provide by:
 * https://www.cms.gov/Medicare/Coding/ICD9ProviderDiagnosticCodes/codes.html.
 * The file used is located at data/database/medicalTerminology/
 * CMS29_DESC_LONG_SG.txt. This class seeks to create a database with the
 * files provide information and use SQLITE3 pattern matching to retrieve
 * needed data.
 */
public class MedicalProcedureDatabase extends TerminologyDatabase {
  private Connection dbConn;
    /**
     * Constructor for MedicalDictionaryDatabase.
     * @param dbFilepath
     *        a string representing path to the database
     */
  public MedicalProcedureDatabase(String dbFilepath) {
    try {
      Class.forName("org.sqlite.JDBC");
      String url = "jdbc:sqlite:" + dbFilepath;
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


  @Override
  public List<String> retrieveEntry(String filepath) {
    TextFileLoader loader = new TextFileLoader(filepath);
    List<String> toReturn = loader.fileLoader();
    return toReturn;
  }

  @Override
  public void createDatabase(List<String> entry) throws SQLException {
    if (dbConn != null) {
      String query = "CREATE TABLE IF NOT EXISTS medical_proc("
                + "termID INTEGER,"
                + "term TEXT,"
                + "PRIMARY KEY (termID));";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.executeUpdate();
      for (String s : entry) {
        String[] input =  s.split(" ", 2);
        query = "INSERT INTO medical_proc VALUES (null,?);";
        prep = dbConn.prepareStatement(query);
        prep.setString(1, input[1]);
        prep.addBatch();
        prep.executeBatch();
      }
      prep.close();
    }
  }

  @Override
  public List<String> query(TerminologyAssociation input) throws SQLException {
    if (dbConn != null) {
      String query = "SELECT term FROM medical_proc WHERE (term = ?)";
      PreparedStatement prep;
      prep = dbConn.prepareStatement(query);
      prep.setString(1, input.getTerm());
      ResultSet rs = prep.executeQuery();
      List<String> toReturn = new ArrayList<>();
      while (rs.next()) {
        toReturn.add(rs.getString(1));
      }
      rs.close();
      query = "SELECT term FROM medical_proc WHERE (term LIKE ?)";
      prep = dbConn.prepareStatement(query);
      for (String s : input.getRoots()) {
        prep.setString(1, "%" + s + "%");
        rs = prep.executeQuery();
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
        return toReturn;
      }
    }
    return new ArrayList<>();
  }
}
