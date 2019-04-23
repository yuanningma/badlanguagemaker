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
    Map<String, String[]> fields = qm.toMap();
    // TODO: Create template object.
    // TODO: Similarity check before saving to database.
    // TODO: Create template in database with labels from frontend.
    // templatesDb.saveTemplate(template);
    Map<String, Object> variables = ImmutableMap.of("message", "success!");
    return GSON.toJson(variables);
  }
}
