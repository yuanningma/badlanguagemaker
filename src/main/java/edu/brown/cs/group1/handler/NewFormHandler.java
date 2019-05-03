package edu.brown.cs.group1.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.database.TemplatesDatabase;
import edu.brown.cs.group1.template.Template;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * New Form Handler.
 * @author juliannerudner
 *
 */
public class NewFormHandler implements TemplateViewRoute {
  private TemplatesDatabase tempsDb;

  /**
   * Constructor for a NewFormHandler.
   */
  public NewFormHandler() {
    tempsDb = new TemplatesDatabase("data/database/templates.sqlite3");
  }

  @Override
  public ModelAndView handle(Request req, Response res) {

    String tempId = req.params(":templateId");
    int templateId = Integer.parseInt(tempId);
    String patId = req.params(":patientId");
    int patientId = Integer.parseInt(patId);
    Template temp = tempsDb.getTemplate(templateId);
    List<String> labels = temp.getFields().getLabels(false);

    Map<String,
        Object> variables = ImmutableMap.of("title",
            "pc+: Home",
            "message",
            "",
            "patientId",
            patientId,
            "labels",
            labels);
    return new ModelAndView(variables, "newForm.ftl");
  }
}
