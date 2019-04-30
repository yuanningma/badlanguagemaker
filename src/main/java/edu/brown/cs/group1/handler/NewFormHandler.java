package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class NewFormHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    // String id = req.params(":movieId");
    Map<String, Object> variables = ImmutableMap.of("title", "pc+: Home");
    return new ModelAndView(variables, "newForm.ftl");
  }
}
