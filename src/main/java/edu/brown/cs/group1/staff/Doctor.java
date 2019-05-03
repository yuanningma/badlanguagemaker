package edu.brown.cs.group1.staff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.database.StaffDatabase;
import edu.brown.cs.group1.patient.Patient;

public class Doctor extends Staff {

  private List<Patient> patientList;

  public Doctor(int i) {
    staffId = i;
    // permissions = new HashMap<Integer, Boolean>();
    name = new String();
    isAdmin = false;
    isDoctor = false;
    isWorking = false;
    patientList = new ArrayList<Patient>();
  }

  public Doctor(int i,
      // Map<Integer, Boolean> p,
      String n,
      boolean a,
      boolean d,
      boolean w) {
    staffId = i;
    // permissions = p;
    name = n;
    isAdmin = a;
    isDoctor = d;
    isWorking = w;
    patientList = new ArrayList<Patient>();
  }

  public void setStaffDb(StaffDatabase b) {
    staffdb = b;
  }

  public void setPatientDb(PatientDatabase b) {
    patientdb = b;
  }

  public void addPatient(Patient p) {
    if (isDoctor) {
      patientList.add(p);
      try {
        patientdb.savePatients(p);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("ERROR: Failed to save patient to database");
      }
    }
  }

  public void updatePatient(String field, String value, Patient p) {
    try {
      patientdb.update(field, value, p);
    } catch (SQLException e) {
      System.out
          .println("ERROR: Failed to update patient information in database");
    }
  }

  /**
   * Method to remove a patient. We need a corresponding SQL method.
   * @param p
   *          Patient to remove
   */
  public void removePatient(Patient p) {
    // TODO: Remove method
    if (isDoctor) {
      patientList.remove(p);
      try {
        patientdb.deletePatient(p.getPatientId());
      } catch (SQLException sql) {
        sql.printStackTrace();
      }
    }
  }

  /**
   * Returns a list of patients associated with this doctor.
   * @return a list of the corresponding patients.
   */
  public List<Patient> getPatients() {
    List<String[]> patients = new ArrayList<String[]>();
    List<Patient> toret = new ArrayList<Patient>();
    try {
      patients = patientdb.getAllPatients(staffId);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to retrieve patients from database");
    }
    for (String[] patient : patients) {
      Patient p = new Patient(Integer
          .parseInt(patient[3]), patient[0], patient[1], patient[2], staffId);
      toret.add(p);
    }
    return toret;

  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getStaffId() {
    return staffId;
  }

  @Override
  public void setStaffId(int s) {
    staffId = s;
  }

  // @Override
  // public Map<Integer, Boolean> getPermissions() {
  // return permissions;
  // }
  //
  // @Override
  // public void setPermissions(Map<Integer, Boolean> p) {
  // permissions = p;
  // try {
  // staffdb.update("permissions", p.toString(), this);
  // } catch (SQLException e) {
  // System.out.println("ERROR: Failed to update permissions in database");
  // }
  // }

  @Override
  public boolean isAdmin() {
    return isAdmin;
  }

  @Override
  public void setAdmin(boolean a) {
    isAdmin = a;
    try {
      staffdb.update("is_Admin", Boolean.toString(a), this);
    } catch (SQLException e) {
      System.out
          .println("ERROR: Failed to update admin privileges in database");
    }
  }

  @Override
  public boolean isDoctor() {
    return isDoctor;
  }

  @Override
  public void setDoctor(boolean d) {
    isDoctor = d;
    try {
      staffdb.update("is_Doctor", Boolean.toString(d), this);
    } catch (SQLException e) {
      System.out
          .println("ERROR: Failed to update doctor privileges in database");
    }
  }

  @Override
  public boolean isWorking() {
    return isWorking;
  }

  @Override
  public void setWorking(boolean w) {
    isWorking = w;
    try {
      staffdb.update("is_Working", Boolean.toString(w), this);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to update working in database");
    }
  }

  @Override
  public void addStaff(Staff s) {
    if (isAdmin) {
      staffList.add(s);
      try {
        staffdb.saveNewStaff(s);
      } catch (SQLException e) {
        System.out.println("ERROR: Failed to save new staff to database");
      }
    }
  }

  @Override
  public List<Staff> getStaff() {
    // TODO: Get staff method for SQL maybe? Only if we need it
    return staffList;
  }

  @Override
  public void initializeStaffList() {
    staffList = new ArrayList<Staff>();
  }

  @Override
  void removeStaff(Staff s) {
    staffList.remove(s);
    try {
      staffdb.deleteStaff(s.getStaffId());
    } catch (SQLException sql) {
      sql.printStackTrace();
    }
  }

  @Override
  public void setName(String name1) {
    // TODO Auto-generated method stub
    name = name1;
    try {
      staffdb.update("name", name1, this);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to update working in database");
    }

  }
}
