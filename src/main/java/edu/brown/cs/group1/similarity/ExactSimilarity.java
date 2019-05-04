package edu.brown.cs.group1.similarity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs.group1.database.TemplatesDatabase;
import edu.brown.cs.group1.template.Template;

/**
 * ExactSimilarity class provides ability to return similarity as a double by
 * checking exact matches for field names. Similarity between two templates is
 * calculated by finding the total number of fields that match and dividing that
 * number by the total number of unique field names from both templates.
 * @author wchoi11
 *
 */
public class ExactSimilarity {

  private TemplatesDatabase tempDb;

  /**
   * Constructor.
   * @param tempDb
   *          Templates database.
   */
  public ExactSimilarity(TemplatesDatabase tempDb) {
    this.tempDb = tempDb;
  }

  /**
   * Calculates similarity between two templates.
   * @param template1
   *          First template.
   * @param template2
   *          Second template.
   * @return Value between 0 and 1 representing similarity between two
   *         templates.
   */
  public static double twoTempsSimil(Template template1, Template template2) {
    // Get total number of unique fields.
    double unique = uniqueFieldCount(template1, template2);
    // Get total number of matching fields.
    double matches = matchFieldCount(template1, template2);

    // double tot1 = template1.getFields().getLabels(false).size();
    // tot1 += template2.getFields().getLabels(false).size();
    // Return quotient.
    // if (template1.getTemplateName().equals("Two Procedures")
    // || template2.getTemplateName().equals("Two Procedures")) {
    // System.out.println("UNIQUE IS: " + unique
    // + " AND MATCHES IS: "
    // + matches
    // + " AND RETURNED IS: "
    // + matches / unique);
    // }
    // System.out.println(
    // "COMPARING: NEW VS OLD " + unique / tot1 + ", " + matches / unique);
    // return unique / tot1;

    return matches / Math.ceil((unique + matches) / 2);
    // return matches / unique;
  }

  /**
   * Returns list of templates from greatest similarity to least similarity with
   * given template.
   * @param template1
   *          Form to compare with.
   * @param min
   *          Minimum similarity to add to list.
   * @return List of templates ordered by similarity to template1.
   */
  public List<Template> mostSimil(Template template1, double min) {
    // Get all templates from database.
    List<Template> templates = tempDb.getAllTemplates();
    // for (Template t : templates) {
    // System.out.println("TEMPLATE IS: " + t.getTemplateName());
    // }
    // Run twoFormsSimil on all templates and put into hashmap results of each
    // similarity check.
    List<Template> results = new ArrayList<>();
    for (Template template : templates) {
      // System.out.println("SIMILARITY WAS: " + twoTempsSimil(template1,
      // template)
      // + " FOR TEMPLATES "
      // + template1.getTemplateName()
      // + " AND "
      // + template.getTemplateName());
      if (twoTempsSimil(template1, template) > min) {

        results.add(template);
      }
    }
    ExactSimilaritySorter sorter = new ExactSimilaritySorter(template1);
    results.sort(sorter);
    return results;
  }

  /**
   * Returns number of unique fields among both templates.
   * @param template1
   *          First template.
   * @param template2
   *          Second template.
   * @return Number of unique fields.
   */
  private static int uniqueFieldCount(Template template1, Template template2) {
    // Add fields to a set.
    List<String> fields1 = template1.getFields().getLabels(false);
    List<String> fields2 = template2.getFields().getLabels(false);
    Set<String> allFields = new HashSet<>();
    allFields.addAll(fields1);
    allFields.addAll(fields2);

    // if (template1.getTemplateName().equals("Two Procedures")
    // || template2.getTemplateName().equals("Two Procedures")) {
    // for (String one : fields1) {
    // System.out.println("IN FIELDS 1: x" + one + "x");
    // }
    // for (String two : fields2) {
    // System.out.println("IN FIELDS 2: x" + two + "x");
    // }
    // for (String three : allFields) {
    // System.out.println("IN ALL FIELDS: x" + three + "x");
    // }
    // }
    return allFields.size();
  }

  /**
   * Returns number of labels that match between both templates.
   * @param template1
   *          First template.
   * @param template2
   *          Second template.
   * @return Number of matches.
   */
  private static int matchFieldCount(Template template1, Template template2) {
    List<String> labels1 = template1.getFields().getLabels(false);
    List<String> labels2 = template2.getFields().getLabels(false);
    Set<String> someLabels = new HashSet<>();
    someLabels.addAll(labels1);
    int count = 0;
    for (String label : labels2) {
      if (someLabels.contains(label)) {
        count++;
      }
    }
    return count;
  }

}
