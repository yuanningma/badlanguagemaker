package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class PatientProfileHandler implements TemplateViewRoute {

  private FormsDatabase formsDb;
  private PatientDatabase patientDb;

  public PatientProfileHandler() {
  }

  @Override
  public ModelAndView handle(Request arg0, Response arg1) {
    Map<String, Object> variables = ImmutableMap
        .of("title", "pc+: My Dashboard", "content", "", "message", "");
    return new ModelAndView(variables, "PatientProfile.ftl");
  }

}
