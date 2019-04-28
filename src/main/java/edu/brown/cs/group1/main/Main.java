package edu.brown.cs.group1.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.group1.handler.CreateFormHandler;
import edu.brown.cs.group1.handler.DDHandler;
import edu.brown.cs.group1.handler.FormHandler;
import edu.brown.cs.group1.handler.GraphHandler;
import edu.brown.cs.group1.handler.LoginHandler;
import edu.brown.cs.group1.handler.NewFormHandler;
import edu.brown.cs.group1.handler.PastFormsHandler;
import edu.brown.cs.group1.handler.PatientProfileHandler;
import edu.brown.cs.group1.handler.XRayHandler;
import edu.brown.cs.group1.handler.relevanceHandler;
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

    // TODO: Pass in dbPath to handlers.
    Spark.get("/patients/:patientId/forms", new PastFormsHandler(), freeMarker);
    Spark.get("/patients/:patientId/forms/:formId",
        new FormHandler(),
        freeMarker);
    Spark.get("/patients/:patientId/profile",
        new PatientProfileHandler(),
        freeMarker);
    Spark.get("/forms/new", new NewFormHandler(), freeMarker);
    Spark.post("/forms/create", new CreateFormHandler());
    Spark.get("/imaging", new XRayHandler(), freeMarker);
    Spark.get("/data", new GraphHandler(), freeMarker);
    Spark
        .get("/patients/:patientId/timeline", new PatientHandler(), freeMarker);
    Spark.post("/relevance", new relevanceHandler(), freeMarker);

  }

  private static class PatientHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) throws Exception {
      // TODO Auto-generated method stub
      Map<String, Object> variables =
          ImmutableMap.of("title", "pc+ home", "message", "", "content", "");
      return new ModelAndView(variables, "timeline.ftl");
    }

  }

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
