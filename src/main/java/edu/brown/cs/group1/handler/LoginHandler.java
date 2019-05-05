package edu.brown.cs.group1.handler;

import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.LoginDatabase;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Login Handler.
 * @author juliannerudner
 *
 */
public class LoginHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
<<<<<<< HEAD
    QueryParamsMap qm = req.queryMap();
    String username = qm.value("username");
    String password = qm.value("password");

    LoginDatabase login = new LoginDatabase("data/database/login.sqlite3");
    Integer docId = 0;

    try {
      docId = login.getStaff(username);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String path = "http://0.0.0.0:4567/Dashboard/" + Integer.toString(docId);
    System.out.println(path);
    Map<String, Object> variables = ImmutableMap
        .of("title", "pc+: User Login", "message", "", "path", path);
=======
    Map<String, Object> variables =
        ImmutableMap.of("title", "pc+: User Login", "message", "Enter login!");
>>>>>>> b01b2352769f21aa45e360a323cffdd9aa6e22ec
    return new ModelAndView(variables, "login.ftl");
  }
}
