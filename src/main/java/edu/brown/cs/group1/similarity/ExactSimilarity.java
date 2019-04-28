package edu.brown.cs.group1.similarity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.template.Template;

/**
 * ExactSimilarity class provides ability to return similarity as a double by
 * checking exact matches for field names. Similarity between two forms is
 * calculated by finding the total number of fields that match and dividing that
 * number by the total number of unique field names from both forms.
 * @author wchoi11
 *
 */
public class ExactSimilarity {

  private FormsDatabase formsDb;

  /**
   * Constructor.
   * @param formsDb
   *          Path to forms database.
   */
  public ExactSimilarity(String formsDb) {
    this.formsDb = new FormsDatabase(formsDb);
  }

  /**
   * Calculates similarity between two forms.
   * @param form1
   *          First form.
   * @param form2
   *          Second form.
   * @return Value between 0 and 1 representing similarity between two forms.
   */
  public static double twoFormsSimil(Template form1, Template form2) {
    // Get total number of unique fields.
    double unique = uniqueFieldCount(form1, form2);
    // Get total number of matching fields.
    double matches = matchFieldCount(form1, form2);
    // Return quotient.
    return matches / unique;
  }

  /**
   * Returns list of forms from greatest similarity to least similarity with
   * given form.
   * @param form1
   *          Form to compare with.
   * @param min
   *          Minimum similarity to add to list.
   * @return List of forms ordered by similarity to form1.
   */
  public List<Template> mostSimil(Template form1, double min) {
    // Get all forms from database.
    List<Template> forms = formsDb.getAllForms();
    // Run twoFormsSimil on all forms and put into hashmap results of each
    // similarity check.
    List<Template> results = new ArrayList<>();
    for (Template form : forms) {
      if (twoFormsSimil(form1, form) > min) {
        results.add(form);
      }
    }
    ExactSimilaritySorter sorter = new ExactSimilaritySorter(form1);
    results.sort(sorter);
    return results;
  }

  /**
   * Returns number of unique fields among both forms.
   * @param form1
   *          First form.
   * @param form2
   *          Second form.
   * @return Number of unique fields.
   */
  private static int uniqueFieldCount(Template form1, Template form2) {
    // Add fields to a set.
    List<String> fields1 = form1.getFields().getLabels(true);
    List<String> fields2 = form2.getFields().getLabels(true);
    Set<String> allFields = new HashSet<>();
    allFields.addAll(fields1);
    allFields.addAll(fields2);
    return allFields.size();
  }

  /**
   * Returns number of labels that match between both forms.
   * @param form1
   *          First form.
   * @param form2
   *          Second form.
   * @return Number of matches.
   */
  private static int matchFieldCount(Template form1, Template form2) {
    List<String> labels1 = form1.getFields().getLabels(true);
    List<String> labels2 = form2.getFields().getLabels(true);
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
