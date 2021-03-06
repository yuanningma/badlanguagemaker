package edu.brown.cs.group1.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.group1.database.PatientDatabase;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handler for Doctor Dashboard search bar.
 * @author juliannerudner
 *
 */
public class SearchDdHandler implements Route {
  private static final Gson GSON = new Gson();
  private PatientDatabase patientDb =
      new PatientDatabase("data/database/members.sqlite3");

  /**
   * Constructor for searchDDHandler.
   */
  public SearchDdHandler() {

  }

  @Override
  public String handle(Request arg0, Response arg1) throws Exception {
    // TODO Auto-generated method stub
    QueryParamsMap qm = arg0.queryMap();

    String name = qm.value("search");

    List<String[]> patients = patientDb.getPatientNameMatch(name);

    Map<String, Object> vars = ImmutableMap.of("patients", patients);

    return GSON.toJson(vars);
  }

}
