import edu.brown.cs.group1.database.StaffDatabase;
import edu.brown.cs.group1.staff.Staff;
import edu.brown.cs.group1.staff.Admin;
import edu.brown.cs.group1.staff.Doctor;
import org.junit.Test;
import org.junit.Before;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertFalse;

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
        member1 = new Doctor(1, permission,false,true,true);
        permission.put(3, true);
        member2 = new Admin(2, permission,true,false, true);
        permission.put(4, false);
        member3 = new Doctor(3, permission, true, false, true);
    }

    @Test
    public void testsaveNewStaff() {
        try {
            staffDatabase.saveNewStaff(member1);
        } catch (SQLException sql) {
            sql.printStackTrace();
            System.out.println("Test Failure");
        }
    }
    @Test
    public void testUpdate() {
        try{
            staffDatabase.update("is_Working", "false", member1);
            Staff staff = staffDatabase.getStaffMember(member1.getStaffId());
            assertFalse(staff.isWorking());
        } catch (SQLException sql) {
           sql.printStackTrace();
        }
    }
}
