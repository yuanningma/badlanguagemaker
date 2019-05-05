package edu.brown.cs.group1.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.group1.database.TemplatesDatabase;
import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.similarity.ExactSimilarity;
import edu.brown.cs.group1.template.Template;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * CreateFormHandler provides ability to process POST requests to
 * "/forms/create" for saving new templates to the database.
 * @author wchoi11
 *
 */
public class CheckTemplateHandler implements Route {
  private static final Gson GSON = new Gson();
  private String tempDbPath;
  private TemplatesDatabase tempDb;

  /**
   * Constructor.
   * @param tempDbPath
   *          Templates database.
   */
  public CheckTemplateHandler(String tempDbPath) {
    this.tempDbPath = tempDbPath;
    this.tempDb = new TemplatesDatabase(tempDbPath);
  }

  @Override
  public String handle(Request req, Response res) {
    ExactSimilarity checker = new ExactSimilarity(tempDb);
    QueryParamsMap qm = req.queryMap();
    String labelsString = qm.value("fields");
    String formName = qm.value("name");
    TemplateFields labels = TemplateFields.valueOf(labelsString);
    Template template = new Template(-1, labels, formName);
    // Similarity check before saving to database.
    // Min value for mostSimil is hard-coded for now.

    List<Template> simil = checker.mostSimil(template, 0.49);

    if (simil.isEmpty()) {
      Map<String, Object> variables = ImmutableMap.of("similar", false);
      return GSON.toJson(variables);
    } else {
      String similLabels = simil.get(0).getFields().toString();
      similLabels = similLabels.replaceAll(",", " ");
      Map<String, Object> variables =
          ImmutableMap.of("similar", true, "fields", similLabels);
      return GSON.toJson(variables);
    }
  }
}
