package edu.brown.cs.TestDatabase;

import edu.brown.cs.group1.database.LoginDatabase;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * All admin passwords are: password.
 */
public class LoginDatabaseTest {
    LoginDatabase loginDatabase;

    @Before
    public void setUp() {
        loginDatabase = new LoginDatabase("data/database/login.sqlite3");
    }

    @Test
    public void saveStaff() {
        try {
            loginDatabase.saveUser(1, "SNapoli", "password");
            loginDatabase.saveUser(2, "TBarnet","password");
            loginDatabase.saveUser(3, "BMullins", "password");
            loginDatabase.saveUser(4, "GMcCloughin", "password");
            loginDatabase.saveUser(5, "GRaecroft", "password");
            loginDatabase.saveUser(6, "GHepher", "password");
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

}
