package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * New Template Handler.
 * @author juliannerudner
 *
 */
public class NewTemplateHandler implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request req, Response res) {
    Map<String, Object> variables =
        ImmutableMap.of("title", "pc+: Home", "message", "");
    return new ModelAndView(variables, "newTemplate.ftl");
  }
}
