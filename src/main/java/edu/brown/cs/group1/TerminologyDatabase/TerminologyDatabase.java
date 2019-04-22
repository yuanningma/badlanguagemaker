package edu.brown.cs.group1.TerminologyDatabase;

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
 */
  public abstract void createDatabase();
}
