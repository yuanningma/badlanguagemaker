package edu.brown.cs.group1.handler;

import java.util.LinkedHashMap;
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

    // TODO: Get form fields from id. Make map of labels to fields.
    Map<String, String> fields = new LinkedHashMap<>();
    fields.put("Name", "Eric");
    fields.put("Age", "1234");
    fields.put("Weight", "1234 pounds");

    Map<String, Object> variables = ImmutableMap.of("title",
        "pc+ ", "message", id, "fields", fields);
    return new ModelAndView(variables, "form.ftl");
  }
}
