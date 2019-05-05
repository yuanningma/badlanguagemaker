package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
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
=======
    QueryParamsMap qm = req.queryMap();
    String username = qm.value("username");
    String password = qm.value("password");
>>>>>>> 8c80bc4710675195311fd07d9a5a3b70f94a3bd2

    Map<String, Object> variables = ImmutableMap
<<<<<<< HEAD
        .of("title", "pc+: User Login", "message", "Enter login!", "path", "");

=======
        .of("title", "pc+: User Login", "message", "", "path", path);
>>>>>>> 8c80bc4710675195311fd07d9a5a3b70f94a3bd2
    return new ModelAndView(variables, "login.ftl");
  }
}
