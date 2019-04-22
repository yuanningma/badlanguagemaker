package edu.brown.cs.group1.TerminologyDatabase;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import edu.brown.cs.group1.textloader.TextFileLoader;

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

    /**
     * Constructor for MedicalDictionaryDatabase.
     */
  public MedicalDictionaryDatabase() { }

  private static final int DICTIONARYBEGINS = 17;

  @Override
   public List<String> retrieveEntry(String filepath) {
    TextFileLoader loader = new TextFileLoader(filepath);
    List<String> retrieve = loader.fileLoader();
    List<String> toReturn = retrieve.subList(DICTIONARYBEGINS, retrieve.size());
    return toReturn;
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
