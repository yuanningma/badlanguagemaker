package edu.brown.cs.group1.search;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.template.Template;

public class Relevance {

  private Search search;

  public Relevance() {
    search = new Search();
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
    List<List<String>> docs = new ArrayList<List<String>>();

    if (tags == null) {
      for (Template t : forms) {
        docs.add(t.getFields().getContent());
      }
    } else {
      for (Template t : forms) {
        for (String s : tags) {
          if (t.getTags().containsTag(s)) {
            docs.add(t.getFields().getContent());
            break;
          }
        }
      }
    }

    List<List<String>> sorted = search.rankDocs(terms, docs);

    return null;
  }

  // public List<String> generateSynonyms

}
