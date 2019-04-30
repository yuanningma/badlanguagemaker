package edu.brown.cs.group1.search;

import java.util.List;

import org.junit.Test;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.template.Template;

public class RelevanceTest {
  @Test
  public void testGenerateTerms() {
    Relevance r = new Relevance();

    FormsDatabase fdb = new FormsDatabase("data/database/form.sqlite3");
    List<Template> forms = fdb.getAllForms();
    System.out.println("ALL");
    System.out.println(forms.size());
    for (Template form : forms) {
      System.out.println(form.getFields().getContent().size());
    }
    // for (Template form : forms) {
    // System.out.println(form.getFields().getContent());
    // }

    List<String> terms = r.generateTerms("cardiovascular");
    List<Template> sorted = r.rankFormsWithTag(terms, null, forms);
    System.out.println("SEARCH ONLY");
    System.out.println(sorted.size());
  }
}
