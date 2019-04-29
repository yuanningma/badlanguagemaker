package edu.brown.cs.TerminologyAssociation;
import java.sql.SQLException;
import java.util.List;
import java.util.PriorityQueue;

import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import org.junit.Before;
import org.junit.Test;

public class TerminologyAssociationTest {
    TerminologyAssociation ta;
    String filepath;
    @Before
    public void setUp() {
        ta = new TerminologyAssociation(null, null);
        filepath = "data/medicalTerminology/medicalChecklistAssociations.txt";
    }

    @Test
    public void TestFileParser() {
        List<TerminologyAssociation> toReturn = ta.readTerminologyAssociations(filepath);
        System.out.println(toReturn.size());
    }
}
