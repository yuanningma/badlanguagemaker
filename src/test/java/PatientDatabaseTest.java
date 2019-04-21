import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.patient.Patient;
import org.junit.Test;
import org.junit.Before;

import java.sql.SQLException;

public class PatientDatabaseTest {
  PatientDatabase patientDatabase;
  Patient patient1;

  @Before
  public void setUp() {
      patientDatabase = new PatientDatabase("data/database/patients.sqlite3");
      patient1 = new Patient(1);
  }

  @Test
   public void testCreate() {
      try {
          patientDatabase.create();
      } catch (SQLException sql) {
          sql.printStackTrace();
          System.out.println("Test Failure");
      }
   }

   @Test
    public void testInsert() {
      try {
          patientDatabase.insert(patient1);
      } catch (SQLException sql) {
          sql.printStackTrace();
      }

   }
}
