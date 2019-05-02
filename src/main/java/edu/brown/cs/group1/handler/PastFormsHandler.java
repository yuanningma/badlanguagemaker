package edu.brown.cs.group1.handler;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.database.TemplatesDatabase;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import edu.brown.cs.group1.template.Template;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


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
  private TemplatesDatabase tempDb;
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
    this.formsDb = new FormsDatabase("data/database/forms.sqlite3");
    this.patientDb = new PatientDatabase("data/database/members.sqlite3");
    this.tempDb = new TemplatesDatabase("data/database/templates.sqlite3");
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    String patientIdString = req.params(":patientId");
    int patientId = Integer.parseInt(patientIdString);
     List<Template> forms = formsDb.getAllForms(patientId);
     List<Integer> formIds = new ArrayList<>();
     for (Template form : forms) {
     formIds.add(form.getTemplateId());
     }
     List<Template> templates = tempDb.getAllTemplates();
     Map<String, Integer> nameToId = new HashMap<>();
     for (Template temp : templates) {
     nameToId.put(temp.getTemplateName(), temp.getTemplateId());
     }
    // TODO: Pass form ids to front-end.
    // TODO: Pass map of template name to id
    Map<String, Object> variables =
        ImmutableMap.of("title", "pc+ home", "id", patientId, "nameToId", nameToId ,"formIds" ,formIds);

    return new ModelAndView(variables, "pastForms.ftl");
  }
}
