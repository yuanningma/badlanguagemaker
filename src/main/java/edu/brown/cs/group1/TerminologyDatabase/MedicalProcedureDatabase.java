package edu.brown.cs.group1.TerminologyDatabase;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;

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
  private String path;

  MedicalProcedureDatabase(String path) {
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
