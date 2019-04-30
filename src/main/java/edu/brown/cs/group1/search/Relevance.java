package edu.brown.cs.group1.search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.TerminologyDatabase.MedicalDictionaryDatabase;
import edu.brown.cs.group1.TerminologyDatabase.MedicalProcedureDatabase;
import edu.brown.cs.group1.synonyms.TerminologyAssociation;
import edu.brown.cs.group1.template.Template;

public class Relevance {

  private Search search;
  private MedicalDictionaryDatabase mddb;
  private MedicalProcedureDatabase mpdb;

  public Relevance() {
    search = new Search();
    mddb = new MedicalDictionaryDatabase("data/database/medicalDictionary.sqlite3");
    mpdb = new MedicalProcedureDatabase("data/database/medicalProcedures.sqlite3");
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
  public Template rankFormsWithTag(List<String> terms,
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
          if (t.getTags().containsTag(s)) {
            docs.add(t.getFields().getContent());
            goodForms.add(t);
            break;
          }
        }
      }
    }
    List<List<String>> sorted = new ArrayList<List<String>>();
    try {
      sorted = search.threadedRankDocs(terms, docs);
    } catch (InterruptedException e) {
      System.out.println("ERROR: Interrupted!");
    }

    return null;
  }

  public List<String> generateTerms(String s) {
    List<String> terms = new ArrayList<String>();
    terms.add(s);
    TerminologyAssociation rootterm = new TerminologyAssociation(s, null);
    List<TerminologyAssociation> assocs = rootterm.readTerminologyAssociations("data/medicalTerminology/medicalChecklistAssociations.txt");

    for (TerminologyAssociation a : assocs) {
      System.out.println(a.getTerm());
      try {
        terms.addAll(mddb.query(a));
        terms.addAll(mpdb.query(a));
      } catch (SQLException e) {
        System.out.println("ERROR: SQL error");
      }
    }

    // for (String a : terms) {
    // System.out.println(a);
    // }
    // System.out.println("SIZE IS: " + terms.size());
    return terms;
  }
  // public List<String> generateSynonyms

}
