package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.template.Template;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * SaveFormHandler provides ability to process POST requests to "/forms/save"
 * for saving new forms to the database for a patient.
 * @author wchoi11
 *
 */
public class SaveFormHandler implements Route {

  private static final Gson GSON = new Gson();
  private String formsDbPath = "data/database/forms.sqlite3";
  private FormsDatabase formsDb;

  /**
   * Constructor.
   */
  public SaveFormHandler() {
    this.formsDb = new FormsDatabase(formsDbPath);
  }

  @Override
  public String handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    String labelsAndValuesString = qm.value("fields");
    String patientIdString = qm.value("patientId");
    String formName = qm.value("formName");
    int patientId = Integer.parseInt(patientIdString);
    // Create form in database with labels and values from frontend.
    TemplateFields tf = TemplateFields.valueOf(labelsAndValuesString);
    formsDb.saveForm(new Template(-1, tf, formName), patientId);
    Map<String, Object> variables = ImmutableMap.of("message", "success!");
    return GSON.toJson(variables);
  }
}
