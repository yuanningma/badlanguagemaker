package edu.brown.cs.group1.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public void initializeDoctorList() {
    doctorList = new ArrayList<Doctor>();
  }

  public void addDoctor(Doctor d) {
    doctorList.add(d);
  }

  public void removeDoctor(Doctor d) {
    doctorList.remove(d);
  }

  public List<Doctor> getDoctors() {
    return doctorList;
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
