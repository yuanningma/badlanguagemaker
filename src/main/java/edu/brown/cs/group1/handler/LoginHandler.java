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
    Map<String, Object> variables = ImmutableMap
        .of("title", "pc+: User Login", "message", "", "path", "");
    return new ModelAndView(variables, "login.ftl");
  }
}
