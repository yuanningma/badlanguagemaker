package edu.brown.cs.group1.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.util.concurrent.AtomicDouble;

import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.database.PatientDatabase;
import edu.brown.cs.group1.database.TagsDatabase;
import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import edu.brown.cs.group1.template.Template;

/**
 * The Relevance class integrates the Search class, which is fairly
 * generalizable, with the rest of our project.
 * @author yma37
 *
 */
public class Relevance {

  private Search search;
  private MedicalDictionaryDatabase mddb;
  private MedicalProcedureDatabase mpdb;
  private PatientDatabase pdb;
  private TagsDatabase tdb;
  private FormsDatabase fdb;
  private TerminologyAssociation rootterm;
  private List<TerminologyAssociation> assocs;

  /**
   * Relevance Constructor.
   */
  public Relevance() {
    search = new Search();
    mddb = new MedicalDictionaryDatabase(
        "data/database/medicalDictionary.sqlite3");
    mpdb =
        new MedicalProcedureDatabase("data/database/medicalProcedures.sqlite3");
    tdb = new TagsDatabase("data/database/largeTags.sqlite3");
    fdb = new FormsDatabase("data/database/forms.sqlite3");
    pdb = new PatientDatabase("data/database/members.sqlite3");

    rootterm = new TerminologyAssociation("root", null);
    assocs = rootterm.readTerminologyAssociations(
        "data/medicalTerminology/medicalChecklistAssociations.txt");
  }

  /**
   * Getter for forms database.
   * @return A forms Database.
   */
  public FormsDatabase getFormsDatabase() {
    return fdb;
  }

  /**
   * Getter for a patient database.
   * @return A patient database.
   */
  public PatientDatabase getPatientDatabase() {
    return pdb;
  }

  /**
   * Parser for search input.
   * @param toparse
   *          search.
   * @return A list of possible terms after parsing.
   */
  public List<String> parseForMe(List<String> toparse) {
    List<String> toret = new ArrayList<String>();
    for (String s : toparse) {
      toret.addAll(
          Arrays.asList(s.replaceAll("[^A-Za-z0-9]", " ").trim().split(" ")));
    }
    return toret;
  }

  /**
   * Ranking without the use of threads.
   * @param terms
   *          a list of terms.
   * @param tags
   *          a list of tags.
   * @param forms
   *          a list of forms.
   * @return a ranked list of forms.
   */
  public List<Template> unthreadedRank(List<String> terms,
      List<String> tags,
      List<Template> forms) {
    search.setSize(forms.size());
    return search.rankTemplates(terms, forms);
  }

  /*
   * What do we want here?
   * 
   * We want a method that ranks all given forms, with or without tags, for a
   * given set of search terms
   * 
   * Do we want the ability to generate search terms here? Take in a word, then
   * search for synonyms of that word to put into the tf-idf?
   */
  public List<Template> rankFormsWithTag(List<String> terms,
      List<String> tags,
      List<Template> forms) {
    // GO THROUGH ALL FORMS AND CHECK FOR THE TAGS
    List<Template> goodForms = new ArrayList<Template>();
    List<List<String>> docs = new ArrayList<List<String>>();

    if (tags == null) {
      for (Template t : forms) {
        docs.add(t.getFields().getContent());
      }
      goodForms = forms;
    } else {
      for (Template t : forms) {
        for (String s : tags) {
          if (t.getTags().contains(s)) {
            docs.add(t.getFields().getContent());
            goodForms.add(t);
            break;
          }
        }
      }
    }

    try {
      return search.threadedRankTemplates(terms, goodForms);
    } catch (InterruptedException e) {
      System.out.println("ERROR: Interrupted Exception!");
    }

    // List<List<String>> sorted = new ArrayList<List<String>>();
    // try {
    // sorted = search.threadedRankDocs(terms, docs);
    // } catch (InterruptedException e) {
    // System.out.println("ERROR: Interrupted!");
    // }
    //
    // return goodForms;
    return goodForms;
  }

  /**
   * Sets the "true content" of a List of Templates for easy parsing.
   * @param forms
   *          a list of forms.
   * @return a list of templates.
   */
  public List<Template> trueContentGenerator(List<Template> forms) {
    List<Template> temp = forms;
    for (int i = 0; i < temp.size(); i++) {
      Template form = temp.get(i);
      List<String> trueContent = new ArrayList<String>();
      // System.out.println(r.parseForMe(form.getFields().getContent()));
      trueContent.addAll(this.parseForMe(form.getFields().getContent()));
      form.setTrueContent(trueContent);
    }
    return temp;
  }

  public List<Map.Entry<Template, AtomicDouble>>
      getRankings(List<String> terms, List<String> tags, List<Template> forms) {
    List<Template> goodForms = new ArrayList<Template>();
    List<List<String>> docs = new ArrayList<List<String>>();

    if (tags == null) {
      for (Template t : forms) {
        docs.add(t.getFields().getContent());
      }
      goodForms = forms;
    } else {
      for (Template t : forms) {
        for (String s : tags) {
          if (t.getTags().contains(s)) {
            docs.add(t.getFields().getContent());
            goodForms.add(t);
            break;
          }
        }
      }
    }

    try {
      return search.mapRankTemplate(terms, tags, goodForms);
    } catch (InterruptedException e) {
      System.out.println("ERROR: Interrupted Exception!");
    }

    // List<List<String>> sorted = new ArrayList<List<String>>();
    // try {
    // sorted = search.threadedRankDocs(terms, docs);
    // } catch (InterruptedException e) {
    // System.out.println("ERROR: Interrupted!");
    // }
    //
    // return goodForms;
    return null;
  }

  public List<String> generateTerms(String s) {
    List<String> terms = new ArrayList<String>();
    terms.add(s);

    String t;
    try {
      t = tdb.getTag(s);
      terms.addAll(tdb.getWords(t));
      for (TerminologyAssociation a : assocs) {
        // System.out.println(a.getTerm() + " " + a.getRoots().toString());
        for (String d : a.getRoots()) {
          if (s.equals(d) || s.equals(a.getTerm())) {
            String tag = tdb.getTag(d);
            for (String tmp : tdb.getWords(tag)) {
              if (!terms.contains(tmp)) {
                terms.add(tmp);
              }
            }
            // terms.addAll(tdb.getWords(tag));
          }

        }
        // try {
        // terms.addAll(mddb.query(a));
        // terms.addAll(mpdb.query(a));
        // } catch (SQLException e) {
        // System.out.println("ERROR: SQL error");
        // }
      }
    } catch (SQLException e) {
      System.out.println("ERROR: SQL Exception");
    }
    // for (String term : terms) {
    // System.out.println("TERM: " + term);
    // }
    // for (String a : terms) {
    // System.out.println(a);
    // }
    // System.out.println("SIZE IS: " + terms.size());
    return terms;
  }
  // public List<String> generateSynonyms

}
