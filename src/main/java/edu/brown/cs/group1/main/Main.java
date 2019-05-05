package edu.brown.cs.group1.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    parser.accepts("port")
        .withRequiredArg()
        .ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);
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

    // TODO: separate long and string version of date
    @Override
    public String handle(Request arg0, Response arg1) {
      // TODO Auto-generated method stub
      QueryParamsMap qm = arg0.queryMap();
      System.out.println(currID);

      // System.out.println("VALUES: " + qm.values());

      String startDate = qm.value("startDate").trim();
      String endDate = qm.value("endDate").trim();
      boolean usingDates = false;
      if (!startDate.equals("") && !endDate.equals("")) {
        usingDates = true;
      }
      Date sD = null;
      Date eD = null;
      if (usingDates) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
          sD = sdf.parse(startDate);
          eD = sdf.parse(endDate);
          Calendar c = Calendar.getInstance();
          c.setTime(eD);
          c.add(Calendar.DAY_OF_MONTH, 1);
          eD = sdf.parse(sdf.format(c.getTime()));
          if (sD.compareTo(eD) > 0) {
            System.out.println(
                "ERROR: Please make sure the start date comes before the end!");
            usingDates = false;
          }
          System.out.println("SD IS: " + sD + " ED IS: " + eD);
        } catch (ParseException e1) {
          System.out.println("ERROR: Failed to parse date!");
          usingDates = false;
        }
      }

      String searchTerm = qm.value("search");

      searchTerm = searchTerm.replaceAll("[^A-za-z ]", "").trim();
      // System.out.println("SEARCHTERM IS: x" + searchTerm + "x");
      String[] words = searchTerm.split("\\s+");

      System.out.println("SEARCHED: " + searchTerm);
      boolean isEmptyQuery = searchTerm.equals("");
      List<String> terms = new ArrayList<String>();
      System.out.println("WORDS IS: " + words);
      for (String s : words) {
        terms.addAll(r.generateTerms(s));
      }
      if (qm.value("cardio").equals("true")) {
        System.out.println(r.generateTerms("cardio"));
        terms.addAll(r.generateTerms("cardio"));
        terms.addAll(r.generateTerms("cardiovascular"));
        isEmptyQuery = false;
      }
      if (qm.value("respiro").equals("true")) {
        terms.addAll(r.generateTerms("respiratory"));
        terms.addAll(r.generateTerms("lung"));
        isEmptyQuery = false;
      }
      if (qm.value("neuro").equals("true")) {
        terms.addAll(r.generateTerms("neuro"));
        isEmptyQuery = false;
      }
      if (Boolean.getBoolean(qm.value("endo"))) {
        terms.addAll(r.generateTerms("endo"));
        isEmptyQuery = false;
      }
      if (qm.value("reno").equals("true")) {
        terms.addAll(r.generateTerms("reno"));
        terms.addAll(r.generateTerms("rena"));
        terms.addAll(r.generateTerms("renal"));
        terms.addAll(r.generateTerms("kidney"));
        isEmptyQuery = false;
      }
      if (qm.value("hepato").equals("true")) {
        terms.addAll(r.generateTerms("hepato"));
        terms.addAll(r.generateTerms("GI"));
        terms.addAll(r.generateTerms("gastro"));
        terms.addAll(r.generateTerms("intestinal"));
        terms.addAll(r.generateTerms("gastrointestinal"));
        isEmptyQuery = false;
      }
      if (qm.value("psycho").equals("true")) {
        terms.addAll(r.generateTerms("psych"));
        terms.addAll(r.generateTerms("psycho"));
        terms.addAll(r.generateTerms("psychological"));
        isEmptyQuery = false;
      }
      if (qm.value("ortho").equals("true")) {
        terms.addAll(r.generateTerms("ortho"));
        isEmptyQuery = false;
      }
      if (qm.value("repro").equals("true")) {
        terms.addAll(r.generateTerms("reproductive"));
        isEmptyQuery = false;
      }
      // System.out.println("TERMS IS: " + terms);
      List<Template> patientForms =
          r.getFormsDatabase().getAllForms(Integer.parseInt(currID));

      for (int i = 0; i < patientForms.size(); i++) {
        Template form = patientForms.get(i);
        if (usingDates) {
          try {
            if (form.getDate().compareTo(sD) >= 0
                && form.getDate().compareTo(eD) <= 0) {
              List<String> trueContent = new ArrayList<String>();
              trueContent.addAll(r.parseForMe(form.getFields().getContent()));
              form.setTrueContent(trueContent);
            } else {
              System.out.println("REMOVING " + patientForms.get(i).getDate());
              patientForms.remove(i);
            }
          } catch (NullPointerException e) {
            System.out.println("ERROR: Dates entered in incorrectly!");
            usingDates = false;
            continue;
          }

        } else {
          List<String> trueContent = new ArrayList<String>();
          trueContent.addAll(r.parseForMe(form.getFields().getContent()));
          form.setTrueContent(trueContent);
        }

      }

      List<Map.Entry<Template, AtomicDouble>> sorted =
          r.getRankings(terms, null, patientForms);

      Double maxRanking = sorted.get(0).getValue().doubleValue();
      List<Template> sortedForms = new ArrayList<Template>();

      List<Double> tfidfs = new ArrayList<Double>();

      Map<Template, Double> dateSort = new HashMap<Template, Double>();
      for (Map.Entry<Template, AtomicDouble> e : sorted) {
        sortedForms.add(e.getKey());
        System.out.println(terms.size());
        if (!isEmptyQuery) {
          System.out.println("TRUE TFIDF: " + e.getValue().doubleValue());
          tfidfs
              .add(Math.floor((100) * e.getValue().doubleValue() / maxRanking));

          dateSort.put(e.getKey(),
              Math.floor((100) * e.getValue().doubleValue() / maxRanking));
        } else {
          tfidfs.add(-1.0);
          dateSort.put(e.getKey(), -1.0);
        }

      }

      List<Map.Entry<Template, Double>> dateEntries =
          new ArrayList<Map.Entry<Template, Double>>(dateSort.entrySet());
      Collections.sort(dateEntries,
          new Comparator<Map.Entry<Template, Double>>() {
            public int compare(Entry<Template, Double> arg0,
                Entry<Template, Double> arg1) {
              if (arg0.getKey().getDate() == null) {
                return 0;
              }
              if (arg1.getKey().getDate() == null) {
                return -1;
              }
              return ((arg1.getKey()
                  .getDate()
                  .compareTo(arg0.getKey().getDate())));
            }
          });

      for (Entry<Template, Double> i : dateEntries) {
        System.out.println(i.getKey().getTimeForFront());
      }
      List<Template> dateSortedTemps = new ArrayList<Template>();
      List<Double> newTfIdfs = new ArrayList<Double>();
      for (Entry<Template, Double> entry : dateEntries) {
        dateSortedTemps.add(entry.getKey());
        newTfIdfs.add(entry.getValue());
      }
      Map<String, Object> vars = ImmutableMap
          .of("forms", dateSortedTemps, "id", currID, "vals", newTfIdfs);

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
