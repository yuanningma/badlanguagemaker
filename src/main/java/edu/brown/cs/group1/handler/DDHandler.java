package edu.brown.cs.group1.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.PatientDatabase;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Doctor Dashboard Handler.
 * @author juliannerudner
 *
 */
public class DDHandler implements TemplateViewRoute {
  private PatientDatabase patientDb =
      new PatientDatabase("data/database/members.sqlite3");

  @Override
  public ModelAndView handle(Request arg0, Response arg1) {
    Integer id = Integer.parseInt(arg0.params(":doctorId"));

    QueryParamsMap qm = arg0.queryMap();
    List<String[]> patients1 = new ArrayList<String[]>();
    List<String> names = new ArrayList<String>();

    String search = arg0.params("search");

    try {
      patients1.addAll(patientDb.getAllPatients(id));

      patients1.forEach(P -> names.add(P[0] + " " + P[1] + " " + P[2]));

    } catch (

    SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Map<String,
        Object> variables = ImmutableMap.of("title",
            "pc+: My Dashboard",
            "content",
            "",
            "patientsFN",
            patients1,
            "message",
            "");

    return new ModelAndView(variables, "DD.ftl");
  }

}
