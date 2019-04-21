package edu.brown.cs.group1.patient;

import java.util.ArrayList;
import java.util.List;
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
  // private boolean isWorking;
  // TODO: Change to isHospitalized?
  // TODO: Add patient database connection? Also to other databases?

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

  public Map<Integer, Template> getCompletedForms() {
    return completedForms;
  }

  public void setCompletedForms(Map<Integer, Template> completedForms) {
    this.completedForms = completedForms;
  }

  /**
   * Returns all completed forms for this patient.
   * @return List of completed forms for this patient. Empty list if no forms
   *         have been completed for this patient.
   */
  public List<Template> getAllFilledTemplates() {
    // TODO: Select all form ids from patient-form database
    List<Integer> formIds = new ArrayList<>();
    // TODO: Get forms using formId
    List<Template> forms = new ArrayList<>();
    for (int formId : formIds) {
      // TODO: Call method for returning form from formId (formId prefaced by id
      // for
      // which table to query from?)
      // Template template = ?;
      // forms.add(template);
    }
    return forms;
  }

  /**
   * Returns blank form to fill out for this patient.
   * @param formName
   *          Name/type of form.
   * @return Blank form of specified type. Throws error if no such type exists.
   */
  public Template getTemplate(String formName) {
    // TODO: Call method for inserting new entry in table, generating template
    // object with fields from table
    // TODO: Add to completedForms hashmap
    // completedForms.put(template.getTemplateId(), template);
    // TODO: Return template object
    return null;
  }

  /**
   * Returns completed form of specified type for this patient.
   * @param formName
   *          Name/type of form.
   * @return Completed form of given type. Throws error if no form of this type
   *         is found for this patient.
   */
  public Template getFilledTemplate(String formName) {
    // TODO: Call method for selecting form based on patient id
    // TODO: Return form returned from above method
    // return form;

    return null;
  }

}
