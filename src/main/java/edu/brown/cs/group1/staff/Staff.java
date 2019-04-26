package edu.brown.cs.group1.staff;

import java.util.List;
import java.util.Map;

import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.database.StaffDatabase;

public abstract class Staff {

  protected int staffId;
  protected Map<Integer, Boolean> permissions;
  protected boolean isAdmin;
  protected boolean isDoctor;
  protected boolean isWorking;
  protected static List<Staff> staffList;
  protected StaffDatabase staffdb;
  protected PatientDatabase patientdb;

  public abstract int getStaffId();

  abstract void setStaffId(int staffId);

  public abstract Map<Integer, Boolean> getPermissions();

  abstract void setPermissions(Map<Integer, Boolean> permissions);

  public abstract boolean isAdmin();

  abstract void setAdmin(boolean isAdmin);

  public abstract boolean isDoctor();

  abstract void setDoctor(boolean isDoctor);

  public abstract boolean isWorking();

  abstract void setWorking(boolean isWorking);

  abstract void addStaff(Staff s);

  abstract void removeStaff(Staff s);

  abstract void initializeStaffList();

  public abstract List<Staff> getStaff();

  /**
   * Method that returns a string used in the Staff Database.
   *
   * @param access
   *          a Map containing a staff member access information
   * @return a string containing the list of all permission the staff member is
   *         granted.
   */
  // TODO: INSTEAD OF A MAP MAYBE WE CAN USE A HASHSET OF ALL THE PLACES
  // THE STAFF MEMBER HAS ACCESS TO?
  public String parsePermissions(Map<Integer, Boolean> access) {
    String toReturn = new String();
    for (Map.Entry<Integer, Boolean> entry : access.entrySet()) {
      if (entry.getValue()) {
        toReturn = toReturn + entry.getKey() + " ";
      }
    }
    return toReturn;
  }

}
