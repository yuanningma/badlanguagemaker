package edu.brown.cs.group1.staff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.database.StaffDatabase;
import edu.brown.cs.group1.patient.Patient;

public class Admin extends Staff {

  /*
   * I think Admins should know about all Doctors in the hospital, but I'm not
   * sure about the best way to implement that. We can either go with every
   * admin has a list of every doctor in the hospital, but that seems like it
   * would waste a lot of memory; alternatively, they all share a list, which is
   * what I'm going to try to do. We could also keep track of all doctors an
   * admin hired personally if we wanted to.
   */

  private static List<Doctor> doctorList;

  public Admin(int i) {
    staffId = i;
    permissions = new HashMap<Integer, Boolean>();
    isAdmin = false;
    isDoctor = false;
    isWorking = false;
    staffList = null;
  }

  public Admin(int i, Map<Integer, Boolean> p, boolean a, boolean d, boolean w) {
    staffId = i;
    permissions = p;
    isAdmin = a;
    isDoctor = d;
    isWorking = w;
  }

  public void updatePatient(String field, String value, Patient p) {
    try {
      patientdb.update(field, value, p);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to update patient information in database");
    }
  }

  public void updateStaff(String field, String value, Staff staff) {
    try {
      staffdb.update(field, value, staff);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      System.out.println("ERROR: Failed to update staff information in database");
    }
  }

  public void setStaffDb(StaffDatabase b) {
    staffdb = b;
  }

  public void setPatientDb(PatientDatabase b) {
    patientdb = b;
  }

  public void initializeDoctorList() {
    doctorList = new ArrayList<Doctor>();
  }

  public void addDoctor(Doctor d) {
    doctorList.add(d);
    try {
      staffdb.saveNewStaff(d);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to add doctor to database");
    }
  }

  public void removeDoctor(Doctor d) {
    doctorList.remove(d);
  }

  public List<Doctor> getDoctors() {
    return doctorList;
  }

  @Override
  public int getStaffId() {
    return staffId;
  }

  @Override
  public void setStaffId(int s) {
    staffId = s;
  }

  @Override
  public Map<Integer, Boolean> getPermissions() {
    return permissions;
  }

  @Override
  public void setPermissions(Map<Integer, Boolean> p) {
    permissions = p;
    try {
      staffdb.update("permissions", p.toString(), this);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to update permissions in database");
    }
  }

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
      System.out.println("ERROR: Failed to update admin privileges in database");
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
      System.out.println("ERROR: Failed to update doctor privileges in database");
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
    staffList.add(s);
    try {
      staffdb.saveNewStaff(s);
    } catch (SQLException e) {
      System.out.println("ERROR: Failed to save new staff to database");
    }
  }

  @Override
  public List<Staff> getStaff() {
    return staffList;

  }

  @Override
  public void initializeStaffList() {
    staffList = new ArrayList<Staff>();

  }

  @Override
  void removeStaff(Staff s) {
    // TODO Remove method
    staffList.remove(s);

  }

}
