package edu.brown.cs.group1.patient;

import java.util.List;

import edu.brown.cs.group1.template.Template;

/**
 * Patient class provides access to each patient's unique id and forms filled
 * out for patient. Also provides ability to create form instance for patient.
 * @author wchoi11
 *
 */
public class Patient {
  private final int patientId;
  // private boolean isWorking;
  // TODO: Change to isHospitalized?

  /**
   * Constructor.
   * @param patientId
   *          Unique patient id.
   */
  public Patient(int patientId) {
    this.patientId = patientId;
  }

  /**
   * Gets patient's id.
   * @return Patient's id.
   */
  public int getPatientId() {
    return patientId;
  }

//  /**
//   * Sets patient's id.
//   * @param patientId
//   *          Patient's id.
//   */
//  public void setPatientId(int patientId) {
//    this.patientId = patientId;
//  }

//  /**
//   * Returns all completed forms for this patient.
//   * @return List of completed forms for this patient. Empty list if no forms
//   *         have been completed for this patient.
//   */
//  public List<Template> getAllFilledTemplates() {
//
//  }

//  /**
//   * Returns blank form to fill out for this patient.
//   * @param formName
//   *          Name/type of form.
//   * @return Blank form of specified type. Throws error if no such type exists.
//   */
//  public Template getTemplate(String formName) {
//
//  }

//  /**
//   * Returns completed form of specified type for this patient.
//   * @param formName
//   *          Name/type of form.
//   * @return Completed form of given type. Throws error if no form of this type
//   *         is found for this patient.
//   */
//  public Template getFilledTemplate(String formName) {
//
//  }

}
