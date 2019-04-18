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
    // TODO: Create patient object with id from URL. Or call patient object from
    // a cache?
    // TODO: Call getAllFilledTemplates on patient to pass to front-end.
    Map<String, Object> variables =
        ImmutableMap.of("title", "pc+ home", "message", "");
    return new ModelAndView(variables, "pastForms.ftl");
  }
}
