package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateFormHandler implements Route {
  private static final Gson GSON = new Gson();

  @Override
  public String handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    Map<String, String[]> params = qm.toMap();
    String[] fields = params.get("fields");
    System.out.println(fields[1]);
    // TODO: Create template object.
    // Template template = new Template(0, new TemplateFields(fields));
    // TODO: Similarity check before saving to database.
    // TODO: Create template in database with labels from frontend.
    // templatesDb.saveTemplate(template);
    Map<String, Object> variables = ImmutableMap.of("message", "success!");
    return GSON.toJson(variables);
  }
}
