package edu.brown.cs.TestTermiologyDatabase;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import edu.brown.cs.group1.synonyms.TerminologyAssociation;

public class MedicalProcedureDatabaseTest {
  MedicalProcedureDatabase db;
  List<String> list;
  TerminologyAssociation synonom;

  @Before
  public void setUp() {
    db = new MedicalProcedureDatabase(
        "data/database/medicalProcedures.sqlite3");
    list = db.retrieveEntry("data/medicalTerminology/CMS29_DESC_LONG_SG.txt");
    PriorityQueue<String> roots = new PriorityQueue<>();
    roots.add("cardi");
    roots.add("cardio");
    roots.add("cordi");
    synonom = new TerminologyAssociation("heart", roots);
  }

  @Test
  public void testRetrieveEntries() {

    assertTrue(list.get(0)
        .equals("0001 Therapeutic ultrasound of vessels of head and neck"));
    assertTrue(list.get(list.size() - 1)
        .equals("9999 Other miscellaneous procedures"));
  }

  /**
   * PROCEED WITH CAUTION: DO NOT RUN DATABASE IS ALREADY CREATED.
   */
  // @Test
  // public void testCreateDatabase() {
  // try {
  // db.createDatabase(list);
  // } catch (SQLException sql) {
  // sql.printStackTrace();
  // }
  // }

  @Test
  public void testQuery() {
    try {
      List<String> result = db.query(synonom);
      System.out.println(result);
    } catch (SQLException sql) {
      sql.printStackTrace();
    }
  }
}
