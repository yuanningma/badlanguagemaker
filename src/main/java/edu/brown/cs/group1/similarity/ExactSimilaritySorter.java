package edu.brown.cs.group1.similarity;

import java.util.Comparator;

import edu.brown.cs.group1.template.Template;

/**
 * ExactSimilaritySorter class provides ability to sort forms from greatest
 * similarity to least similarity with given form in constructor.
 * @author wchoi11
 *
 */
public class ExactSimilaritySorter implements Comparator<Template> {

  private Template form;

  /**
   * Constructor.
   * @param form
   *          Form to compare with.
   */
  public ExactSimilaritySorter(Template form) {
    this.form = form;
  }

  @Override
  public int compare(Template o1, Template o2) {
    double s1 = ExactSimilarity.twoFormsSimil(form, o1);
    double s2 = ExactSimilarity.twoFormsSimil(form, o2);
    return Double.compare(s2, s1);
  }

}
