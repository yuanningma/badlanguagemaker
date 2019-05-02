package edu.brown.cs.TestDatabase;

import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import edu.brown.cs.group1.database.TagsDatabase;
import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagsDatabaseTest {
    TagsDatabase tagsDatabase;
    TerminologyAssociation ta;
    String filepath;
    MedicalProcedureDatabase mpd;
    MedicalDictionaryDatabase mdd;

    @Before
    public void setUp() {
//        tagsDatabase = new TagsDatabase("data/database/tags.sqlite3");
//        ta = new TerminologyAssociation(null, null);
//        filepath = "data/medicalTerminology/medicalChecklistAssociations.txt";
//        mdd = new MedicalDictionaryDatabase("data/database/medicalDictionary.sqlite3");
//        mpd = new MedicalProcedureDatabase(
//                "data/database/medicalProcedures.sqlite3");
    }

//    @Test
//    public void TestRegisterTag() {
//     List<TerminologyAssociation> toReturn = ta.readTerminologyAssociations(filepath);
//     List<String> toQuery;
//     for(TerminologyAssociation ta: toReturn) {
//         toQuery = new ArrayList<>();
//         try {
//             toQuery.addAll(mpd.query(ta));
//             toQuery.addAll(mdd.query(ta));
//         } catch (SQLException sql) {
//             sql.printStackTrace();
//         }
//         for(String result: toQuery) {
//             try {
//                 tagsDatabase.registerNewTag(ta.getTerm(), result);
//             } catch (SQLException sql) {
//                 sql.printStackTrace();
//             }
//         }
//     }
//    }

}
