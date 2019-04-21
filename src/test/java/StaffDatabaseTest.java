import edu.brown.cs.group1.database.StaffDatabase;
import edu.brown.cs.group1.staff.Staff;
import edu.brown.cs.group1.staff.Admin;
import edu.brown.cs.group1.staff.Doctor;
import org.junit.Test;
import org.junit.Before;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StaffDatabaseTest {
    StaffDatabase staffDatabase;
    Staff member1;
    Staff member2;
    Staff member3;

    @Before
    public void setUp() {
        staffDatabase= new StaffDatabase("data/database/staff.sqlite3");
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
    public void testCreate() {
        try {
            staffDatabase.create();
        } catch (SQLException sql) {
            sql.printStackTrace();
            System.out.println("Test Failure");
        }
    }

    @Test
    public void testInsert() {
        try {
            staffDatabase.insert(member1);
            Staff result = staffDatabase.getStaffMember("2");
        } catch (SQLException sql) {
            sql.printStackTrace();
            System.out.println("Test Failure");
        }
    }

}
