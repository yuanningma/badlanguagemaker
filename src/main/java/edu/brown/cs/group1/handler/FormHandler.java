package edu.brown.cs.group1.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.template.Template;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * Form handler provides ability to view a specific form for a patient.
 * @author wchoi11
 *
 */
public class FormHandler implements TemplateViewRoute {

  private String formsDbPath;
  private FormsDatabase formsDb;

  /**
   * Constructor.
   * @param formsDbPath
   *          Path to forms database.
   */
  public FormHandler(String formsDbPath) {
    this.formsDbPath = formsDbPath;
    this.formsDb = new FormsDatabase(formsDbPath);
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    String id = req.params(":formId");
    String patId = req.params(":patientId");

    // TODO: Get form fields from id. Make map of labels to fields.
    Map<String, String> fields = new LinkedHashMap<>();
    // fields.put("Name", "Eric");
    // fields.put("Age", "1234");
    // fields.put("Weight", "1234 pounds");

    int formId = Integer.parseInt(id);
    Template form = formsDb.getForm(formId);
    System.out.println(form.getTemplateId());

    List<String> labelsAndFields = form.getFields().getContent();
    for (int i = 0; i < labelsAndFields.size(); i += 2) {
      fields.put(labelsAndFields.get(i), labelsAndFields.get(i + 1));
    }
    System.out.println(labelsAndFields);
    Map<String, Object> variables =
        ImmutableMap.of("title", "pc+ ", "fields", fields, "id", patId, "nameToId", fields);
    return new ModelAndView(variables, "form.ftl");
  }
}
