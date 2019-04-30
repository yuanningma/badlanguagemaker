package edu.brown.cs.group1.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import edu.brown.cs.group1.database.FormsDatabase;
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
  private TagsDatabase tdb;
  private FormsDatabase fdb;
  private TerminologyAssociation rootterm;
  private List<TerminologyAssociation> assocs;

  public Relevance() {
    search = new Search();
    mddb = new MedicalDictionaryDatabase("data/database/medicalDictionary.sqlite3");
    mpdb = new MedicalProcedureDatabase("data/database/medicalProcedures.sqlite3");
    tdb = new TagsDatabase("data/database/largeTags.sqlite3");
    fdb = new FormsDatabase("data/database/largeForm.sqlite3");

    rootterm = new TerminologyAssociation("root", null);
    assocs = rootterm.readTerminologyAssociations("data/medicalTerminology/medicalChecklistAssociations.txt");
  }

  /*
   * TODO: What do we want here?
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
    // TODO: GO THROUGH ALL FORMS AND CHECK FOR THE TAGS
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
      // TODO Auto-generated catch block
      e.printStackTrace();
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
