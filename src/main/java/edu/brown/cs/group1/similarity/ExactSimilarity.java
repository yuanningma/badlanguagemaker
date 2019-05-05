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
    List<String> fields1 = template1.getFields().getLabels(false);
    List<String> fields2 = template2.getFields().getLabels(false);
    fields1 = standardize(fields1);
    fields2 = standardize(fields2);
    // Get total number of unique fields.
    double unique = uniqueFieldCount(fields1, fields2);
    // Get total number of matching fields.
    double matches = matchFieldCount(fields1, fields2);
    // Return quotient.
    return matches / Math.ceil((unique + matches) / 2);
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
    // Run twoFormsSimil on all templates and put into hashmap results of each
    // similarity check.
    List<Template> results = new ArrayList<>();
    for (Template template : templates) {
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
   * @param fields1
   *          Fields from first template.
   * @param fields2
   *          Fields from second template.
   * @return Number of unique fields.
   */
  private static int uniqueFieldCount(List<String> fields1,
      List<String> fields2) {
    // Add fields to a set.
    Set<String> allFields = new HashSet<>();
    allFields.addAll(fields1);
    allFields.addAll(fields2);
    return allFields.size();
  }

  /**
   * Converts each string to lowercase and deletes spaces.
   * @param fields
   *          Fields.
   * @return List of standardized strings.
   */
  private static List<String> standardize(List<String> fields) {
    List<String> newFields = new ArrayList<>();
    for (String field : fields) {
      field = field.replaceAll("\\s", "");
      field = field.toLowerCase();
      newFields.add(field);
    }
    return newFields;
  }

  /**
   * Returns number of labels that match between both templates.
   * @param fields1
   *          Fields from first template.
   * @param fields2
   *          Fields from second template.
   * @return Number of matches.
   */
  private static int matchFieldCount(List<String> fields1,
      List<String> fields2) {
    Set<String> someLabels = new HashSet<>();
    someLabels.addAll(fields1);
    int count = 0;
    for (String label : fields2) {
      if (someLabels.contains(label)) {
        count++;
      }
    }
    return count;
  }

}
