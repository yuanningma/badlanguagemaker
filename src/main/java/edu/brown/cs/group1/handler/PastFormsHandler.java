package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class PastFormsHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    // TODO: Get form names and ids (for href) for patient
    Map<String, Object> variables = ImmutableMap.of("title",
        "pc+ home", "message", "");
    return new ModelAndView(variables, "pastForms.ftl");
  }
}
