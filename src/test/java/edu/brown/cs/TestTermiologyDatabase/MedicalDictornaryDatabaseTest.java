package edu.brown.cs.TestTermiologyDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class MedicalDictornaryDatabaseTest {
MedicalDictionaryDatabase db;
    @Before
    public void setUp() {
        db = new MedicalDictionaryDatabase();
    }
    @Test
    public void testRetrieveEntries() {
        List<String> list = db.retrieveEntry(
                "data/medicalTerminology/en_med_glut.txt");
        assertTrue(list.get(0).equals("11-dehydrocorticosterone"));
        assertTrue(list.get(list.size() - 1).equals("Zyvox"));
    }
}
