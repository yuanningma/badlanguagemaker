package edu.brown.cs.group1.handler;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.group1.database.LoginDatabase;
import edu.brown.cs.group1.databaseAuthentication.PasswordUtlitities;
import spark.*;

import java.sql.SQLException;
import java.util.Map;

public class LoginSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
        //TODO CREATE A DATABASE OF LOGIN
        LoginDatabase login = new LoginDatabase("  ");
        PasswordUtlitities passwordUtlitities = new PasswordUtlitities();
        QueryParamsMap qm = req.queryMap();
        String username = qm.value("username");
        String password = qm.value("password");

        Boolean pass = false;
        try {
            if (login.getPassword(username) != null) {
                pass = passwordUtlitities.verifyUserPassword(password, login.getPassword(username), login.getSalt(username));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        if (pass) {
          Map<String, Object> variables = ImmutableMap.of("title", "Patient Dashboard", "message",
                  "Login Success");
          return new ModelAndView(variables, "DD.ftl");
        } else {
            Map<String, Object> variables = ImmutableMap.of("title", "pc+: User Login", "message",
                    "Login Failed: Invalid username or password.");
            return new ModelAndView(variables, "login.ftl");
        }

    }
}
