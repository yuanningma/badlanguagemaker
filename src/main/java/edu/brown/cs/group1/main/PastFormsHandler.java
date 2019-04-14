package edu.brown.cs.group1.main;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class PastFormsHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables = ImmutableMap.of("title",
        "pc+ home", "message", "");
    return new ModelAndView(variables, "pastForms.ftl");
  }
}
