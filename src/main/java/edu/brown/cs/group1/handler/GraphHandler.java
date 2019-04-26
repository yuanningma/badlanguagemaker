package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class GraphHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables = ImmutableMap.of("title", "Data");
    return new ModelAndView(variables, "chart.ftl");
  }
}
