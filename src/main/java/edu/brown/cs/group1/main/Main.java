package edu.brown.cs.group1.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

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
import edu.brown.cs.group1.handler.XRayHandler;
import edu.brown.cs.group1.handler.searchDDHandler;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {
  private static final int DEFAULT_PORT = 4567;

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
        new FormHandler("na"),
        freeMarker);
    Spark.get("/patients/:patientId/profile",
        new PatientProfileHandler(),
        freeMarker);
    Spark.get("/patients/:patientId/forms/:templateId/new",
        new NewFormHandler(),
        freeMarker);
    Spark.post("/forms/save", new SaveFormHandler("na"));
    Spark.get("/templates/new", new NewTemplateHandler(), freeMarker);

    Spark.post("/templates/create", new CreateTemplateHandler(tempDbPath));

    Spark.get("/imaging", new XRayHandler(), freeMarker);
    Spark.get("/data", new GraphHandler(), freeMarker);
    // Spark
    // .get("/patients/:patientId/timeline", new PatientHandler(), freeMarker);
    Spark.post("/searchDD", new searchDDHandler());
    // Spark.post("/relevance", new RelevanceTimelineHandler());

  }

  public static String currID = "0";

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
  // public class RelevanceTimelineHandler implements Route {
  // private final Gson GSON = new Gson();
  // private PatientDatabase patientDb =
  // new PatientDatabase("data/database/members.sqlite3");
  //
  // private FormsDatabase formDb =
  // new FormsDatabase("data/database/forms.sqlite3");
  //
  // /**
  // * Constructor for searchDDHandler.
  // */
  // public RelevanceTimelineHandler() {
  //
  // }
  //
  // @Override
  // public String handle(Request arg0, Response arg1) throws Exception {
  // // TODO Auto-generated method stub
  // QueryParamsMap qm = arg0.queryMap();
  // System.out.println(currID);
  // List<Template> patientForms =
  // formDb.getAllForms(Integer.parseInt(currID));
  //
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
