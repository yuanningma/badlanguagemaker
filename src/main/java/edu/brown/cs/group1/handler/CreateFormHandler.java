package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.group1.field.TemplateFields;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateFormHandler implements Route {
  private static final Gson GSON = new Gson();

  @Override
  public String handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    // String[] fields = qm.value("fields");
    String labelsString = qm.value("fields");
    System.out.println(labelsString);
    TemplateFields labels = TemplateFields.valueOf(labelsString);
    // TODO: Create template object.
    // Template template = new Template(0, labels);
    // TODO: Similarity check before saving to database.
    // TODO: Create template in database with labels from frontend.
    // TODO: Change saveTemplate in formsDatbase s.t. id is auto-incremented.
    // templatesDb.saveTemplate(template);
    Map<String, Object> variables = ImmutableMap.of("message", "success!");
    return GSON.toJson(variables);
  }
}
