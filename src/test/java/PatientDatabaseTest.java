import java.sql.SQLException;

import org.junit.Before;

import org.junit.Test;

import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.patient.Patient;
import static org.junit.Assert.assertTrue;

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
      try {
          patientDatabase.savePatients(patient1);
          Patient queryResult = patientDatabase.getPatient(1);
          assertTrue(queryResult.getFirstName().equals("Veronica"));
      } catch (SQLException sql) {
          sql.printStackTrace();
          System.out.println("Test Failure");
      }
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
