package edu.brown.cs.TestTermiologyDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class MedicalDictornaryDatabaseTest {
MedicalDictionaryDatabase db;
List<String> list;
    @Before
    public void setUp() {
        db = new MedicalDictionaryDatabase("data/database/medicalDictionary.sqlite3");
        list = db.retrieveEntry(
                "data/medicalTerminology/en_med_glut.txt");
    }
    @Test
    public void testRetrieveEntries() {

        assertTrue(list.get(0).equals("11-dehydrocorticosterone"));
        assertTrue(list.get(list.size() - 1).equals("Zyvox"));
    }
    /**
     * PROCEED WITH CAUTION: DO NOT RUN DATABASE IS ALREADY CREATED.
     */
//    @Test
//    public void testCreateDatabase() {
//        try {
//            db.createDatabase(list);
//        } catch (SQLException sql) {
//            sql.printStackTrace();
//        }
//    }
}
