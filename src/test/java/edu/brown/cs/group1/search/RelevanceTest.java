package edu.brown.cs.group1.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.template.Template;

public class RelevanceTest {
  @Test
  public void testGenerateTerms() {
    Relevance r = new Relevance();

    FormsDatabase fdb = new FormsDatabase("data/database/forms.sqlite3");
    List<Template> forms = fdb.getAllForms();
    System.out.println("ALL");
    System.out.println(forms.size());

    for (int i = 0; i < forms.size(); i++) {
      Template form = forms.get(i);
      List<String> trueContent = new ArrayList<String>();
      // System.out.println(r.parseForMe(form.getFields().getContent()));
      trueContent.addAll(r.parseForMe(form.getFields().getContent()));
      form.setTrueContent(trueContent);

    }
    // for (Template form : forms) {
    // System.out.println(form.getFields().getContent());
    // }

    List<String> terms = r.generateTerms("heart");
    terms.addAll(r.generateTerms("aorta"));
    terms.addAll(r.generateTerms("vaccination"));
    terms.addAll(r.generateTerms("aorto"));
    for (String s : terms) {
      System.out.println("TERM: " + s);
    }
    // List<Template> sorted = r.rankFormsWithTag(terms, null, forms);
    // System.out.println("SEARCH ONLY");
    // List<Template> sorted = r.unthreadedRank(terms, null, forms);
    //
    // System.out.println(sorted.size());
    // for (int i = 0; i < forms.size(); i++) {
    // System.out.println(sorted.get(i).getTrueContent());
    // }
    System.out.println("THREADED SORT");
    List<Template> s2 = r.unthreadedRank(terms, null, forms);
    for (int i = 0; i < 50; i++) {
      System.out.println(s2.get(i).getTrueContent());
    }
  }
}
