package edu.brown.cs.TestDatabase;

import static org.junit.Assert.assertFalse;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.group1.database.StaffDatabase;
import edu.brown.cs.group1.staff.Doctor;
import edu.brown.cs.group1.staff.Staff;
import edu.brown.cs.group1.textloader.TextFileLoader;

public class StaffDatabaseTest {
  StaffDatabase staffDatabase;
  Staff member1;
  Staff member2;
  Staff member3;

  @Before
  public void setUp() {
    staffDatabase = new StaffDatabase("data/database/members.sqlite3");
    Map<Integer, Boolean> permission = new HashMap<>();
    permission.put(1, true);
    permission.put(2, false);
    member1 = new Doctor(1, "Harry", false, true, true);
    // permission.put(3, true);
    // member2 = new Admin(2, permission, true, false, true);
    // permission.put(4, false);
    // member3 = new Doctor(3, permission, true, false, true);
  }

//  @Test
//  public void testsaveNewStaff() {
//    try {
//      // Staff queryResult = staffDatabase.getStaffMember(1);
//      TextFileLoader tf = new TextFileLoader("data/database/staff_mock.csv");
//      List<String> query = tf.fileLoader();
//      for (int i = 2; i < query.size(); i++) {
//        String s = query.get(i);
//        String[] arr = s.split(",");
//        member1.setStaffId(Integer.parseInt(arr[0]));
//        member1.setName(arr[1]);
//        member1.setDoctor(Boolean.getBoolean(arr[2]));
//        member1.setAdmin(Boolean.getBoolean(arr[2]));
//        member1.setWorking(Boolean.getBoolean(arr[3]));
//        staffDatabase.saveNewStaff(member1);
//      }
//
//    } catch (SQLException sql) {
//      sql.printStackTrace();
//      System.out.println("Test Failure");
//    }
//  }
//
//  @Test
//  public void testUpdate() {
//    try {
//      staffDatabase.update("is_Doctor", "true", member1);
//      Staff staff = staffDatabase.getStaffMember(member1.getStaffId());
//      assertFalse(staff.isWorking());
//    } catch (SQLException sql) {
//      sql.printStackTrace();
//    }
//  }
}
