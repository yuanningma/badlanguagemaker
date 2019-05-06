package edu.brown.cs.TestDatabase;

import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.patient.Patient;
import edu.brown.cs.group1.textloader.TextFileLoader;
import java.util.List;
public class PatientDatabaseTest {
  PatientDatabase patientDatabase;
  Patient patient1;

  @Before
  public void setUp() {
    patientDatabase = new PatientDatabase("data/database/members.sqlite3");
    patient1 = new Patient(1, "Veronica", "Cole", "Rich", 1);
  }

  @Test
  public void testSavePatient() {
//      try {
//          patientDatabase.savePatients(patient1);
//          patient1 = new Patient(1, "Issie", "Giddy", "Haselgrove", 2);
//          patientDatabase.savePatients(patient1);
//          patient1 = new Patient(1, "Mercedes", "Blackeden", "Whitter", 2);
//          patientDatabase.savePatients(patient1);
//          patient1 = new Patient(1, "Gunar", "Richel", "Goldsbrough", 4);
//          patientDatabase.savePatients(patient1);
//          patient1 = new Patient(1, "Benedict", "Reimer", "Staver", 4);
//          patientDatabase.savePatients(patient1);
//          patient1 = new Patient(1, "Cecilla", "Klezmki", "Champney", 5);
//          patientDatabase.savePatients(patient1);
////
//      }catch(SQLException sql) {
//        sql.printStackTrace();
//      }
//     try {

     //Patient queryResult = patientDatabase.getPatient(1);
//     TextFileLoader tf = new TextFileLoader("data/medicalTerminology/MOCK_DATA (1).csv");
//     List<String> query = tf.fileLoader();
//     for(int i = 2; i < query.size(); i++){
//     String s = query.get(i);
//     String[] arr = s.split(",");
//     patient1.setPatientId(Integer.parseInt(arr[0]));
//     patient1.setFirstName(arr[1]);
//     patient1.setMiddleName(arr[2]);
//     patient1.setLastName(arr[3]);
//     patient1.setPatientId(Integer.parseInt(arr[4]));
//     patientDatabase.savePatients(patient1);
//     }
//
//     } catch (SQLException sql) {
//     sql.printStackTrace();
//     System.out.println("Test Failure");
//     }
  }

  @Test
  public void testUpdate() {
    // try{
    // patientDatabase.update("first_name", "Sally", patient1);
    // Patient queryResult = patientDatabase.getPatient(1);
    // patientDatabase.update("doctorID", "2", patient1);
    // assertTrue(queryResult.getFirstName().equals("Sally"));
    // queryResult = patientDatabase.getPatient(1);
    // assertTrue(queryResult.getDoctorId() == 2);
    // } catch (SQLException sql) {
    // sql.printStackTrace();
    // }
  }
}
