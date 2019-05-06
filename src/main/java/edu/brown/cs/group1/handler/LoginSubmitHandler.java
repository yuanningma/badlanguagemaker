package edu.brown.cs.group1.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.LoginDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.databaseAuthentication.PasswordUtlitities;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class LoginSubmitHandler implements TemplateViewRoute {
  private PatientDatabase patientDb =
      new PatientDatabase("data/database/members.sqlite3");

  @SuppressWarnings("static-access")
  @Override
  public ModelAndView handle(Request req, Response res) throws SQLException {
    Map<String, Object> variables;
    LoginDatabase login = new LoginDatabase("data/database/login.sqlite3");
    PasswordUtlitities passwordUtlitities = new PasswordUtlitities();
    QueryParamsMap qm = req.queryMap();
    String username = qm.value("username");
    String password = qm.value("password");
    String path = "";
    Integer docId = 0;

    docId = login.getStaff(username);

    // System.out.println(username);

    Boolean pass = false;
    try {
      if (username != "" && password != "") {
        if (login.getPassword(username) != null) {
          pass = passwordUtlitities.verifyUserPassword(password,
              login.getPassword(username),
              login.getSalt(username));
        }
      }
    } catch (SQLException sql) {
      sql.printStackTrace();
    }
    if (pass) {
      List<String[]> patients1 = new ArrayList<String[]>();
      List<String> names = new ArrayList<String>();

      try {
        patients1.addAll(patientDb.getAllPatients(docId));
        System.out.println(patients1.size());

        patients1.forEach(P -> names.add(P[0] + " " + P[1] + " " + P[2]));

      } catch (

      SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      path = "http://0.0.0.0:4567/Dashboard/" + docId;
    variables = ImmutableMap.of("title",
          "Patient Dashboard",
          "message",
          "Login Success",
          "patientsFN",
          patients1,
          "content",
          "",
          "path",
          path);
    return new ModelAndView(variables, "DD.ftl");
    } else {
        path = "http://0.0.0.0:4567/login";
         Map<String, Object> variable = ImmutableMap.of("title",
                "pc+: User Login",
                "message",
                "Login Failed: Invalid username or password.",
                "path",
                path);
        return new ModelAndView(variable, "login.ftl");
    }
  }
}
