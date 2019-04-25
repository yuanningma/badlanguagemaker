package edu.brown.cs.TestTermiologyDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.assertTrue;


public class MedicalDictornaryDatabaseTest {
MedicalDictionaryDatabase db;
List<String> list;
TerminologyAssociation synonom;
    @Before
    public void setUp() {
        db = new MedicalDictionaryDatabase("data/database/medicalDictionary.sqlite3");
        list = db.retrieveEntry(
                "data/medicalTerminology/en_med_glut.txt");
        PriorityQueue<String> roots = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        roots.add("cardi");
        roots.add("cardio");
        roots.add("cordi");
        synonom = new TerminologyAssociation("heart", roots);
    }
    @Test
    public void testRetrieveEntries() {

        assertTrue(list.get(0).equals("11-dehydrocorticosterone"));
        assertTrue(list.get(list.size() - 1).equals("Zyvox"));
    }
    /**
     * PROCEED WITH CAUTION: PLEASE DO NOT RUN DATABASE IS ALREADY CREATED.
     */
//    @Test
//    public void testCreateDatabase() {
//        try {
//            db.createDatabase(list);
//        } catch (SQLException sql) {
//            sql.printStackTrace();
//        }
//    }
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
