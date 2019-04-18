package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class DDHandler implements TemplateViewRoute{

  @Override
  public ModelAndView handle(Request arg0, Response arg1) {
    Map<String, Object> variables = ImmutableMap.of("title",
        "Maps: Brown University","content","","message","");
    return new ModelAndView(variables, "DD.ftl");
  }

}
