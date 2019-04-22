package edu.brown.cs.TestTermiologyDatabase;

import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MedicalProcedureDatabaseTest {
    MedicalProcedureDatabase db;

    @Before
    public void setUp() {
        db = new MedicalProcedureDatabase();
    }

    @Test
    public void testRetrieveEntries() {
        List<String> list = db.retrieveEntry(
                "data/medicalTerminology/CMS29_DESC_LONG_SG.txt");
        assertTrue(list.get(0).equals("0001 Therapeutic ultrasound of vessels of head and neck"));
        assertTrue(list.get(list.size() - 1).equals("9999 Other miscellaneous procedures"));
    }
}
