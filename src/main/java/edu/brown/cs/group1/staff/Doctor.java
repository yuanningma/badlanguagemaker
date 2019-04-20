package edu.brown.cs.group1.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.group1.patient.Patient;

public class Doctor extends Staff {

  private List<Patient> patientList;

  public Doctor(int i) {
    staffId = i;
    permissions = new HashMap<Integer, Boolean>();
    isAdmin = false;
    isDoctor = false;
    isWorking = false;
    patientList = new ArrayList<Patient>();
  }

  public Doctor(int i, Map<Integer, Boolean> p, boolean a, boolean d, boolean w) {
    staffId = i;
    permissions = p;
    isAdmin = a;
    isDoctor = d;
    isWorking = w;
    patientList = new ArrayList<Patient>();
  }

  public void addPatient(Patient p) {
    patientList.add(p);
  }

  public void removePatient(Patient p) {
    patientList.remove(p);
  }

  public List<Patient> getPatients() {
    return patientList;
  }

  @Override
  public int getStaffId() {
    // TODO Auto-generated method stub
    return staffId;
  }

  @Override
  public void setStaffId(int s) {
    // TODO Auto-generated method stub
    staffId = s;
  }

  @Override
  public Map<Integer, Boolean> getPermissions() {
    // TODO Auto-generated method stub
    return permissions;
  }

  @Override
  public void setPermissions(Map<Integer, Boolean> p) {
    // TODO Auto-generated method stub
    permissions = p;
  }

  @Override
  public boolean isAdmin() {
    // TODO Auto-generated method stub
    return isAdmin;
  }

  @Override
  public void setAdmin(boolean a) {
    // TODO Auto-generated method stub
    isAdmin = a;
  }

  @Override
  public boolean isDoctor() {
    // TODO Auto-generated method stub
    return isDoctor;
  }

  @Override
  public void setDoctor(boolean d) {
    // TODO Auto-generated method stub
    isDoctor = d;
  }

  @Override
  public boolean isWorking() {
    // TODO Auto-generated method stub
    return isWorking;
  }

  @Override
  public void setWorking(boolean w) {
    // TODO Auto-generated method stub
    isWorking = w;
  }

  @Override
  public void addStaff(Staff s) {
    // TODO Auto-generated method stub
    staffList.add(s);
  }

  @Override
  public List<Staff> getStaff() {
    // TODO Auto-generated method stub
    return staffList;
  }

  @Override
  public void initializeStaffList() {
    // TODO Auto-generated method stub
    staffList = new ArrayList<Staff>();

  }

  @Override
  void removeStaff(Staff s) {
    // TODO Auto-generated method stub
    staffList.remove(s);

  }

}
