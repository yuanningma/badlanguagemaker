package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * PastFormsHandler provides access to all the completed forms for a given
 * patient. Routing is handled such that a patient (and their id) must be
 * specified in order to access forms.
 * @author wchoi11
 *
 */
public class PastFormsHandler implements TemplateViewRoute {

  private FormsDatabase formsDb;
  private PatientDatabase patientDb;

  /**
   * Dummy constructor. Will remove once dbPath can be passed in.
   */
  public PastFormsHandler() {
  }

  /**
   * Constructor.
   * @param formsPath
   *          Path to database of forms.
   * @param patientPath
   *          Path to database of patients.
   */
  public PastFormsHandler(String formsPath, String patientPath) {
    this.formsDb = new FormsDatabase(formsPath);
    this.patientDb = new PatientDatabase("data/database/members.sqlite3");
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    String patientId = req.params(":patientId");
    String name = "";
    // try {
    // name = this.patientDb.getPatient(Integer.parseInt(patientId)).getName();
    // } catch (NumberFormatException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (SQLException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // List<Template> forms = formsDb.getAllForms(patientId);
    // TODO: Pass forms to front-end.
    Map<String, Object> variables =
        ImmutableMap.of("title", "pc+ home", "id", patientId);

    return new ModelAndView(variables, "pastForms.ftl");
  }
}
