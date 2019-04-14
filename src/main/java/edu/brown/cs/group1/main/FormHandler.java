package edu.brown.cs.group1.main;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class FormHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
  String id = req.params(":formId");
  Map<String, Object> variables =
      new ImmutableMap.Builder<String, Object>().put("title", "pc+ home")
          .build();
  return new ModelAndView(variables, "form.ftl");
  }
}
