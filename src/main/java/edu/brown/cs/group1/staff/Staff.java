package edu.brown.cs.group1.staff;

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

  abstract Map<Integer, Boolean> getPermissions();

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

}
