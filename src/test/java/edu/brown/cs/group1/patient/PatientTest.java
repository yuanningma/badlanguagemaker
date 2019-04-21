package edu.brown.cs.group1.patient;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests methods in patient class.
 * @author wchoi11
 *
 */
public class PatientTest {

  @Test
  public void testConstructor() {
    Patient patient = new Patient(123, "Eric", "", "Choi", 456);
    assertNotNull(patient);
    assertTrue(patient.getPatientId() == 123);
    assertTrue(patient.getFirstName().equals("Eric"));
    assertTrue(patient.getMiddleName().equals(""));
    assertTrue(patient.getLastName().equals("Choi"));
    assertTrue(patient.getDoctorId() == 456);
  }

}
