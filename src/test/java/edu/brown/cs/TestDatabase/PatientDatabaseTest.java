package edu.brown.cs.TestDatabase;

import java.sql.SQLException;

import edu.brown.cs.group1.textloader.TextFileLoader;
import org.junit.Before;

import org.junit.Test;

import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.patient.Patient;
import static org.junit.Assert.assertTrue;
import java.util.List;


public class PatientDatabaseTest {
  PatientDatabase patientDatabase;
  Patient patient1;

  @Before
  public void setUp() {
    patientDatabase = new PatientDatabase("data/database/members.sqlite3");
    patient1 = new Patient(1,
            "Veronica", "Cole", "Rich", 1);
  }
  @Test
   public void testSavePatient() {
//      try {
//
//          //Patient queryResult = patientDatabase.getPatient(1);
//          TextFileLoader tf = new TextFileLoader("data/medicalTerminology/MOCK_DATA (2).csv");
//          List<String> query = tf.fileLoader();
//          for(int i = 2; i < query.size(); i++){
//              String s = query.get(i);
//              String[] arr = s.split(",");
//              patient1.setPatientId(i);
//              patient1.setFirstName(arr[1]);
//              patient1.setMiddleName(arr[2]);
//              patient1.setLastName(arr[3]);
//              patient1.setPatientId(i);
//              patientDatabase.savePatients(patient1);
//          }
//
//
//      } catch (SQLException sql) {
//          sql.printStackTrace();
//          System.out.println("Test Failure");
//      }
   }

   @Test
    public void testUpdate() {
      try{
          patientDatabase.update("first_name", "Sally", patient1);
          Patient queryResult = patientDatabase.getPatient(1);
          patientDatabase.update("doctorID", "2", patient1);
          assertTrue(queryResult.getFirstName().equals("Sally"));
          queryResult = patientDatabase.getPatient(1);
          assertTrue(queryResult.getDoctorId() == 2);
      } catch (SQLException sql) {
          sql.printStackTrace();
      }
   }
}
