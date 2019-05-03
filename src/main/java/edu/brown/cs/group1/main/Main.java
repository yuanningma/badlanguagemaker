package edu.brown.cs.group1.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AtomicDouble;
import com.google.gson.Gson;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.database.TemplatesDatabase;
import edu.brown.cs.group1.handler.CreateTemplateHandler;
import edu.brown.cs.group1.handler.DDHandler;
import edu.brown.cs.group1.handler.FormHandler;
import edu.brown.cs.group1.handler.GraphHandler;
import edu.brown.cs.group1.handler.LoginHandler;
import edu.brown.cs.group1.handler.NewFormHandler;
import edu.brown.cs.group1.handler.NewTemplateHandler;
import edu.brown.cs.group1.handler.PastFormsHandler;
import edu.brown.cs.group1.handler.PatientProfileHandler;
import edu.brown.cs.group1.handler.SaveFormHandler;
import edu.brown.cs.group1.handler.SearchDdHandler;
import edu.brown.cs.group1.handler.XRayHandler;
import edu.brown.cs.group1.search.Relevance;
import edu.brown.cs.group1.template.Template;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Main Class.
 * @author juliannerudner
 *
 */
public final class Main {
  private static final int DEFAULT_PORT = 4567;

  /**
   * Runs program.
   * @param args
   *          Command for how program is to be run.
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;
  private String tempDbPath = "data/database/templates.sqlite3";
  // private String formsDbPath = "data/database/forms.sqlite3";

  // private FormsDatabase formsDb = new FormsDatabase(formsDbPath);
  private TemplatesDatabase tempDb = new TemplatesDatabase(tempDbPath);

  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Run method.
   */
  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(
        DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    Spark.get("/login", new LoginHandler(), freeMarker);
    Spark.get("/home", new LoginHandler(), freeMarker);
    Spark.get("/Dashboard/:doctorId", new DDHandler(), freeMarker);
    Spark.get("/patients/:patientId/forms",
        new PastFormsHandler("na", "na"),
        freeMarker);
    Spark.get("/patients/:patientId/forms/:formId",
        new FormHandler("data/database/forms.sqlite3"),
        freeMarker);
    Spark.get("/patients/:patientId/profile",
        new PatientProfileHandler(),
        freeMarker);
    Spark.get("/patients/:patientId/forms/:templateId/new",
        new NewFormHandler(),
        freeMarker);
    Spark.post("/forms/save", new SaveFormHandler());
    Spark.get("/templates/new", new NewTemplateHandler(), freeMarker);

    Spark.post("/templates/create", new CreateTemplateHandler(tempDbPath));

    Spark.get("/imaging", new XRayHandler(), freeMarker);
    Spark.get("/data", new GraphHandler(), freeMarker);

    Spark
        .get("/patients/:patientId/timeline", new PatientHandler(), freeMarker);

    Spark.post("/searchDD", new SearchDdHandler());
    Spark.post("/relevance", new RelevanceTimelineHandler());

  }

  private static String currID = "0";

  /**
   * Patient Handler, essentially the handler for patient information / the
   * patient timeline.
   * @author juliannerudner
   *
   */
  private static class PatientHandler implements TemplateViewRoute {
    private FormsDatabase formsDb;
    private PatientDatabase patientDb =
        new PatientDatabase("data/database/members.sqlite3");

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      // TODO Auto-generated method stub
      String id = arg0.params(":patientId");
      currID = id;
      String name = "";
      try {
        name = patientDb.getPatient(Integer.parseInt(id)).getName();
      } catch (NumberFormatException e) {
        System.out.println(
            "ERROR: number format exception, patient profile handler.");
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
              "id1",
              id,
              "name",
              name);

      return new ModelAndView(variables, "timeline.ftl");
    }
  }

  /**
   * 
   * @author juliannerudner, yma37
   *
   */
  public class RelevanceTimelineHandler implements Route {
    private final Gson gson = new Gson();
    private Relevance r;

    // private PatientDatabase patientDb = new
    // PatientDatabase("data/database/members.sqlite3");
    //
    // private FormsDatabase formDb = new
    // FormsDatabase("data/database/forms.sqlite3");

    /**
     * Constructor for searchDDHandler.
     */
    public RelevanceTimelineHandler() {
      r = new Relevance();
    }

    @Override
    public String handle(Request arg0, Response arg1) throws Exception {
      // TODO Auto-generated method stub
      QueryParamsMap qm = arg0.queryMap();
      System.out.println(currID);

      // System.out.println("VALUES: " + qm.values());

      String searchTerm = qm.value("search");
      System.out.println("SEARCHED: " + searchTerm);
      List<String> terms = r.generateTerms(searchTerm);
      if (qm.value("cardio").equals("true")) {
        System.out.println(r.generateTerms("cardio"));
        terms.addAll(r.generateTerms("cardio"));
        terms.addAll(r.generateTerms("cardiovascular"));
      }
      if (qm.value("respiro").equals("true")) {
        terms.addAll(r.generateTerms("respiratory"));
        terms.addAll(r.generateTerms("lung"));
      }
      if (qm.value("neuro").equals("true")) {
        terms.addAll(r.generateTerms("neuro"));
      }
      if (Boolean.getBoolean(qm.value("endo"))) {
        terms.addAll(r.generateTerms("endo"));
      }
      if (qm.value("reno").equals("true")) {
        terms.addAll(r.generateTerms("reno"));
        terms.addAll(r.generateTerms("rena"));
        terms.addAll(r.generateTerms("renal"));
      }
      if (qm.value("hepato").equals("true")) {
        terms.addAll(r.generateTerms("hepato"));
        terms.addAll(r.generateTerms("GI"));
        terms.addAll(r.generateTerms("gastro"));
        terms.addAll(r.generateTerms("intestinal"));
        terms.addAll(r.generateTerms("gastrointestinal"));
      }
      if (qm.value("psycho").equals("true")) {
        terms.addAll(r.generateTerms("psych"));
        terms.addAll(r.generateTerms("psycho"));
        terms.addAll(r.generateTerms("psychological"));
      }
      if (qm.value("ortho").equals("true")) {
        terms.addAll(r.generateTerms("ortho"));

      }
      if (qm.value("repro").equals("true")) {
        terms.addAll(r.generateTerms("reproductive"));

      }
      // System.out.println("HEPA TEST: " + qm.value("hepa"));
      // List<Template> patientForms =
      // formDb.getAllForms(Integer.parseInt(currID));

      // getAllForms probably wrong?
      List<Template> patientForms =
          r.getFormsDatabase().getAllForms(Integer.parseInt(currID));

      for (int i = 0; i < patientForms.size(); i++) {
        Template form = patientForms.get(i);

        // form.setTrueContent(form.getFields().getContent());
        List<String> trueContent = new ArrayList<String>();
        // System.out.println(r.parseForMe(form.getFields().getContent()));
        trueContent.addAll(r.parseForMe(form.getFields().getContent()));
        form.setTrueContent(trueContent);
        // for (String s : form.getTrueContent()) {
        // System.out.println("IN TRUE CONTENT: " + s);
        // }
      }

      // String currSearch = "heart";

      // System.out.println("BEGINNING DUMMY METHOD");
      // System.out.println("ID: " + Integer.parseInt(currID));
      // r.getFormsDatabase().dummyMethod(Integer.parseInt(currID));
      // System.out.println("ENDED DUMMY METHOD");
      // for (Template t : patientForms) {
      // System.out.println("Template id: " + t.getTemplateId());
      // System.out.println(t.getTags().size());
      // System.out.println(t.getFields().getContent());
      // }

      for (String term : terms) {
        System.out.println("CURRENT TERMS: " + term);
      }
      List<Map.Entry<Template, AtomicDouble>> sorted =
          r.getRankings(terms, null, patientForms);

      List<Template> sortedForms = new ArrayList<Template>();
      List<Double> tfidfs = new ArrayList<Double>();
      for (Map.Entry<Template, AtomicDouble> e : sorted) {
        System.out.println("RANKING CURRENTLY: "
            + e.getKey().getFields().getContent().subList(0, 3)
            + " , "
            + e.getValue());
        sortedForms.add(e.getKey());
        tfidfs.add(e.getValue().doubleValue());
      }
      // System.out.println(patientForms);

      // Map<String, Object> vars = ImmutableMap.of("forms",
      // patientForms,
      // "id",
      // currID);
      Map<String, Object> vars =
          ImmutableMap.of("forms", sortedForms, "id", currID, "vals", tfidfs);

      return gson.toJson(vars);
    }
  }

  // /**
  // * Patient Handler, essentially the handler for patient information / the
  // * patient timeline.
  // * @author juliannerudner
  // *
  // */
  // private static class PatientHandler implements TemplateViewRoute {
  // private FormsDatabase formsDb;
  // private PatientDatabase patientDb =
  // new PatientDatabase("data/database/members.sqlite3");
  //
  // @Override
  // public ModelAndView handle(Request arg0, Response arg1) throws Exception {
  // // TODO Auto-generated method stub
  // String id = arg0.params(":patientId");
  // currID = id;
  // String name = "";
  // try {
  // name = patientDb.getPatient(Integer.parseInt(id)).getName();
  // } catch (NumberFormatException e) {
  // System.out.println(
  // "ERROR: number format exception, patient profile handler.");
  // // e.printStackTrace();
  // } catch (SQLException e) {
  // // TODO Auto-generated catch block
  // System.out.println("ERROR: SQL exception, patient profile handler.");
  // // e.printStackTrace();
  // }
  //
  // Map<String,
  // Object> variables = ImmutableMap.of("title",
  // "pc+: My Dashboard",
  // "content",
  // "",
  // "id1",
  // id,
  // "name",
  // name);
  //
  // return new ModelAndView(variables, "timeline.ftl");
  // }
  // }
  //
  // /**
  // * RelevanceTimelineHandler uses our relevance algorithm to filter in the
  // * timeline
  // * @author yma37
  // *
  // */
  // public class RelevanceTimelineHandler implements Route {
  // private final Gson GSON = new Gson();
  // // private PatientDatabase patientDb = new
  // // PatientDatabase("data/database/members.sqlite3");
  // //
  // // private FormsDatabase formDb = new
  // // FormsDatabase("data/database/forms.sqlite3");
  // private Relevance r;
  //
  // /**
  // * Constructor for searchDDHandler.
  // */
  // public RelevanceTimelineHandler() {
  // r = new Relevance();
  // }
  //
  // @Override
  // public String handle(Request arg0, Response arg1) throws Exception {
  // // TODO Auto-generated method stub
  // QueryParamsMap qm = arg0.queryMap();
  // System.out.println(currID);
  //
  // // getAllForms probably wrong?
  // List<Template> patientForms =
  // r.getFormsDatabase().getAllForms(Integer.parseInt(currID));
  //
  // // System.out.println("BEGINNING DUMMY METHOD");
  // // System.out.println("ID: " + Integer.parseInt(currID));
  // // r.getFormsDatabase().dummyMethod(Integer.parseInt(currID));
  // // System.out.println("ENDED DUMMY METHOD");
  // for (Template t : patientForms) {
  // System.out.println("Template id: " + t.getTemplateId());
  // System.out.println(t.getTags().size());
  // System.out.println(t.getFields().getContent());
  // }
  // // System.out.println(patientForms);
  //
  // Map<String, Object> vars =
  // ImmutableMap.of("forms", patientForms, "id", currID);
  //
  // return GSON.toJson(vars);
  // }
  // }

  /**
   * Handle requests to the front page.
   *
   * @author jj
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "pc+ home", "message", "", "content", "");
      return new ModelAndView(variables, "main.ftl");
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   * @author jj
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

}
