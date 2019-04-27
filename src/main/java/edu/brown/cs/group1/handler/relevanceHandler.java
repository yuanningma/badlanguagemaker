package edu.brown.cs.group1.handler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.template.Template;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import edu.brown.cs.group1.patient.Patient;

public class relevanceHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) {
    // TODO: Create patient object with id from URL.
//    String patientId = req.params(":patientId");
//    PatientDatabase pd = new PatientDatabase("data/database/members.sqlite3");
//    FormsDatabase fb = new FormsDatabase("data/database/members.sqlite3");
//    try {
//      Patient patient = pd.getPatient(Integer.parseInt(patientId));
//      List<Template> formList = fb.getAllForms(patient.getPatientId());
//      for (Template template : formList) {
//
//      }
//    } catch (SQLException sql) {
//      sql.printStackTrace();
//    }



    // TODO: Call getAllFilledTemplates on patient to pass to front-end.
    // List<Template> forms = patient.getAllFilledTemplates();
    Map<String, Object> variables = ImmutableMap.of("title", "pc+ home");

    return new ModelAndView(variables, "timeline.ftl");
  }
}
