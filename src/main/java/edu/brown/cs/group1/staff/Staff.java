package edu.brown.cs.group1.staff;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Staff {

  protected int staffId;
  protected Map<Integer, Boolean> permissions;
  protected boolean isAdmin;
  protected boolean isDoctor;
  protected boolean isWorking;
  protected static List<Staff> staffList;

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
     *              a Map containing a staff member access information
     * @return
     *      a string containing the list of all permission the staff member
     *      is granted.
     */
    //TODO: INSTEAD OF A MAP MAYBE WE CAN USE A HASHSET OF ALL THE PLACES
    // THE STAFF MEMBER HAS ACCESS TO?
  public String parsePermissions(Map<Integer, Boolean> access) {
    String toReturn = new String();
    access.forEach((k, v) -> {

      if (v) {
        toReturn.concat(k.toString() + " ");
      }

    });
    return toReturn;
  }

    /**
     * Method that extracts the permissions from a string.
     *
     * @param access
     *             a string contains all the staff member's granted access.
     * @return
     *          a Map containing the granted access of the staff member.
     */
  public Map<Integer, Boolean> extractPermissions(String access) {
    String[] arr = access.split(" ");
    Map<Integer, Boolean> toReturn = new HashMap<>();

    for (String s: arr) {
      toReturn.put(Integer.parseInt(s), true);
    }

    return toReturn;
  }
}
