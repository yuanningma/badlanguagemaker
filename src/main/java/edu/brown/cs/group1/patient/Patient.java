package edu.brown.cs.group1.patient;

import java.util.Map;

import edu.brown.cs.group1.template.Template;

/**
 * Patient class provides access to each patient's unique id and forms filled
 * out for patient. Also provides ability to create form instance for patient.
 * @author wchoi11
 *
 */
public class Patient {
  private int patientId;
  private String firstName;
  private String middleName;
  private String lastName;
  private int doctorId;
  private Map<Integer, Template> completedForms;
  private boolean isHosptialized;
  // TODO: Change to isHospitalized?

  /**
   * Constructor.
   * @param patientId
   *          Patient id.
   * @param firstName
   *          First name.
   * @param middleName
   *          Middle name.
   * @param lastName
   *          Last name.
   * @param doctorId
   *          Doctor's unique id.
   */
  public Patient(int patientId,
      String firstName,
      String middleName,
      String lastName,
      int doctorId) {
    this.patientId = patientId;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.doctorId = doctorId;
  }

  /**
   * Gets patient's id.
   * @return Patient's id.
   */
  public int getPatientId() {
    return patientId;
  }

  /**
   * Sets patient's id.
   * @param patientId
   *          Patient's id.
   */
  public void setPatientId(int patientId) {
    this.patientId = patientId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
  }

  public String getName() {
    return firstName + " " + middleName + " " + lastName;
  }

  public Map<Integer, Template> getCompletedForms() {
    return completedForms;
  }

  public void setCompletedForms(Map<Integer, Template> completedForms) {
    this.completedForms = completedForms;
  }

  @Override
  public String toString() {
    String s = null;
    return s;
  }
}
