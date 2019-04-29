package edu.brown.cs.group1.handler;

import java.sql.SQLException;
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
  private PatientDatabase patientDb =
      new PatientDatabase("data/database/members.sqlite3");

  public PatientProfileHandler() {
  }

  @Override
  public ModelAndView handle(Request arg0, Response arg1) {
    String id = arg0.params(":patientId");
    String name = "";
    System.out.println(id);
    try {
      name = patientDb.getPatient(Integer.parseInt(id)).getName();

      System.out.println(name);
    } catch (NumberFormatException e) {
      System.out
          .println("ERROR: number format exception, patient profile handler.");
      // e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      System.out.println("ERROR: SQL exception, patient profile handler.");
      // e.printStackTrace();
    }

    Map<String,
        Object> variables = ImmutableMap.of("title",
            "pc+: My Dashboard",
            "content",
            "",
            "id",
            id,
            "name",
            name);
    return new ModelAndView(variables, "PatientProfile.ftl");
  }

}
