package edu.brown.cs.group1.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.patient.Patient;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class DDHandler implements TemplateViewRoute {
  private PatientDatabase patientDb;

  @Override
  public ModelAndView handle(Request arg0, Response arg1) {
    Integer id = Integer.parseInt(arg0.params(":doctorId"));

    List<Patient> patients = new ArrayList<Patient>();
    try {
      patients = patientDb.getAllPatients(id);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Map<String,
        Object> variables = ImmutableMap.of("title",
            "pc+: My Dashboard",
            "content",
            "",
            "message",
            "",
            "patients",
            patients);
    return new ModelAndView(variables, "DD.ftl");
  }

}
