package edu.brown.cs.group1.TerminologyDatabase;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;

import java.sql.SQLException;
import java.util.List;
/**
 * An abstract class for database solely on medical terminology.
 * These database would eventually be used to revive suggestions
 * to be used in the search database.
 */
public abstract class TerminologyDatabase {
 /**
 * Method would retrieve the entries from a file to be place in the database.
 * @param filepath
  *           the filepath to the text file.
 * @return
  *       a list of strings that contain the file entries
 */
  public abstract List<String> retrieveEntry(String filepath);

 /**
  * Method that would create a database with the file entries.
  * @param entry
  *          a list of strings to be inputted into the database.
  * @throws SQLException
  *          thrown when a SQLExpection is thrown.
 */
  public abstract void createDatabase(List<String> entry) throws SQLException;

 /**
  * Method that queries the database.
  * Returns the strings associated with input.
  * @param input
  *           a Terminology Association that contains roots
  *             and a respective prefixes.
  * @return
  *        a list of strings that represent the words queried from that database.
  * @throws SQLException
  *        thrown if an SQLException is thrown.
  */
  public abstract List<String> query(TerminologyAssociation input) throws SQLException;
}
