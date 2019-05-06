package edu.brown.cs.group1.handler;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.group1.database.LoginDatabase;
import edu.brown.cs.group1.databaseAuthentication.PasswordUtlitities;
import spark.*;

import java.sql.SQLException;
import java.util.Map;


public class ValidateHandler implements Route {
    private static final Gson GSON = new Gson();
    @Override
    public String handle(Request req, Response res) {
        LoginDatabase login = new LoginDatabase("data/database/login.sqlite3");
        PasswordUtlitities passwordUtlitities = new PasswordUtlitities();
        QueryParamsMap qm = req.queryMap();
        String username = qm.value("username");
        String password = qm.value("password");
        Integer staffId = 0;
        Boolean pass = false;
        try {
            if (username != "" && password != "") {
                if (login.getPassword(username) != null) {
                    staffId = login.getStaff(username);
                    pass = passwordUtlitities.verifyUserPassword(password,
                            login.getPassword(username),
                            login.getSalt(username));
                }
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        Map<String, Object> variables =
                ImmutableMap.of("status", Boolean.toString(pass), "staffId", staffId);
      return GSON.toJson(variables);
    }
}
