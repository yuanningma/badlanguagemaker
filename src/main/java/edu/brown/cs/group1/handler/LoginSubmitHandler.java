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
    // TODO CREATE A DATABASE OF LOGIN
    LoginDatabase login = new LoginDatabase("data/database/login.sqlite3");
    PasswordUtlitities passwordUtlitities = new PasswordUtlitities();
    QueryParamsMap qm = req.queryMap();
    String username = qm.value("username");
    String password = qm.value("password");
    String path = "";
    Integer docId = 0;

    docId = login.getStaff(username);
    System.out.println(docId);

    Boolean pass = false;
    try {
      if (login.getPassword(username) != null) {
        System.out.println("1");
        pass = passwordUtlitities.verifyUserPassword(password,
            login.getPassword(username),
            login.getSalt(username));
      }
    } catch (SQLException sql) {
      sql.printStackTrace();
    }

    Map<String, Object> variables = ImmutableMap
        .of("title", "pc+: User Login", "message", "", "path", path);

    if (pass) {
      System.out.println("2");

      List<String[]> patients1 = new ArrayList<String[]>();
      List<String> names = new ArrayList<String>();

      try {
        patients1.addAll(patientDb.getAllPatients(docId));

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

    }
    return new ModelAndView(variables, "login.ftl");

    // } else {
    // path = "http://0.0.0.0:4567/home";
    // System.out.println("2A");
    // Map<String,
    // Object> variables = ImmutableMap.of("title",
    // "pc+: User Login",
    // "message",
    // "Login Failed: Invalid username or password.",
    // "path",
    // path);
    // return new ModelAndView(variables, "login.ftl");
    // }

  }
}
