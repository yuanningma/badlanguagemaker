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
    // String tempId = req.params(":templateId");
    // int templateId = Integer.parseInt(tempId);
    String patId = req.params(":patientId");
    int patientId = Integer.parseInt(patId);
    // Template temp = tempsDb.getTemplate(templateId);
    // List<String> labels = temp.getFields().getLabels(false);
    // TODO: Pass in labels to variables.
    Map<String, Object> variables = ImmutableMap
        .of("title", "pc+: Home", "message", "", "patientId", patientId);
    return new ModelAndView(variables, "newForm.ftl");
  }
}
