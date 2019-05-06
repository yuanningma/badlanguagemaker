package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

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
    QueryParamsMap qm = req.queryMap();
    String username = qm.value("username");
    String password = qm.value("password");

    Map<String, Object> variables = ImmutableMap
        .of("title", "pc+: User Login", "message", "Enter login!", "path", "");

    return new ModelAndView(variables, "login.ftl");
  }
}
