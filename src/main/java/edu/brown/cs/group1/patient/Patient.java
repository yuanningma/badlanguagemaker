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
  // private boolean isHospitalized;

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

  /**
   * Gets patient's first name.
   * @return patient's first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets patient's first name.
   * @param firstName
   *          patient's first name.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets patient's middle name.
   * @return patient's middle name.
   */
  public String getMiddleName() {
    return middleName;
  }

  /**
   * Sets patient's middle name.
   * @param middleName
   *          patient's middle name.
   */
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  /**
   * Gets patient's last name.
   * @return patient's last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets patient's last name.
   * @param lastName
   *          patient's last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets a doctor's ID.
   * @return the ID.
   */
  public int getDoctorId() {
    return doctorId;
  }

  /**
   * Sets a doctor's ID.
   * @param doctorId
   *          the doctor's id.
   */
  public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
  }

  /**
   * Gets patient's full name.
   * @return patient's full name.
   */
  public String getName() {
    return firstName + " " + middleName + " " + lastName;
  }

  /**
   * Gets patient's filled out forms.
   * @return A map of completed forms.
   */
  public Map<Integer, Template> getCompletedForms() {
    return completedForms;
  }

  /**
   * Sets patient's completed forms.
   * @param completedForms
   *          The patient's forms.
   */
  public void setCompletedForms(Map<Integer, Template> completedForms) {
    this.completedForms = completedForms;
  }

  @Override
  public String toString() {
    String s = null;
    return s;
  }
}
