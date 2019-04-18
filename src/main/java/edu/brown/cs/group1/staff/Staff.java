package edu.brown.cs.group1.staff;

import java.util.Map;

public abstract class Staff {
  
    int staffId;
    Map<Integer, Boolean> permissions;
    boolean isAdmin;
    boolean isDoctor;
    boolean isWorking;


    abstract int getStaffId();


    abstract void setStaffId(int staffId);


    abstract Map<Integer, Boolean> getPermissions();


    abstract void setPermissions(Map<Integer, Boolean> permissions);


    abstract boolean isAdmin();


    abstract void setAdmin(boolean isAdmin);


    abstract boolean isDoctor();


    abstract void setDoctor(boolean isDoctor);


    abstract boolean isWorking();


    abstract void setWorking(boolean isWorking);
    
}
