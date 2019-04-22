package edu.brown.cs.group1.TerminologyDatabase;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;

import java.util.ArrayList;
import java.util.List;
/**
 * This class interacts solely with the medical dictornary databasr.
 * This database would be creating using files provide by:
 * https://github.com/glutanimate/hunspell-en-med-glut/blob/master/en_med_gut.txt.
 * The file used is located at data/database/medicalTerminology/
 * en_med_glut.txt. This class seeks to create a database with the
 * files provide information and use SQLITE3 pattern matching to retrieve
 * needed data.
 */
public class MedicalDictionaryDatabase extends TerminologyDatabase {
  private String path;

  MedicalDictionaryDatabase(String path) {
    this.path = path;
  }

  @Override
   public List<String> retrieveEntry(String filepath) {
    //TODO: Implementation needed
    return new ArrayList<String>();
  }

  @Override
   public void createDatabase() {
     //TODO: Implementation needed
  }
  @Override
  public List<String> query(TerminologyAssociation input) {
    //TODO: Implementation needed
    return new ArrayList<String>();
  }
}
