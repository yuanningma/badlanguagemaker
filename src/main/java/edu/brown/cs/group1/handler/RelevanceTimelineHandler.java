package edu.brown.cs.group1.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.template.Template;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class RelevanceTimelineHandler implements Route {
  private static final Gson GSON = new Gson();
  private PatientDatabase patientDb =
      new PatientDatabase("data/database/members.sqlite3");

  private FormsDatabase formDb =
      new FormsDatabase("data/database/forms.sqlite3");

  /**
   * Constructor for searchDDHandler.
   */
  public RelevanceTimelineHandler() {

  }

  @Override
  public String handle(Request arg0, Response arg1) throws Exception {
    // TODO Auto-generated method stub
    QueryParamsMap qm = arg0.queryMap();

    // String id = arg0.params("id1");
    // System.out.println(id);
    List<Template> patientForms = formDb.getAllForms(900);

    // System.out.println(patientForms);

    Map<String, Object> vars = ImmutableMap.of("forms", patientForms);

    return GSON.toJson(vars);
  }
}
