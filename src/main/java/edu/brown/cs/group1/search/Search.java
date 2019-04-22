package edu.brown.cs.group1.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search {

  private int totalSize;
  private Map<String, Double> frequencies;

  public Search() {
  }

  public Search(List<List<String>> docs, int featSize) {
    this.init(docs, featSize);
  }

  public void init(List<List<String>> docs, int featSize) {
    // TODO: Create a Synonyms or Vocabulary class that contains
    // all of the words in all of the documents, then calculate
    // idf for each term in the vocabulary, then put them all in
    // our map

    totalSize = docs.size();
    frequencies = new HashMap<String, Double>();
  }

  public double termFrequency(String term, List<String> doc) {
    double freq = 0;

    for (int i = 0; i < doc.size(); i++) {
      if (term.equals(doc.get(i))) {
        freq += 1;
      }
    }

    return freq;
  }

  public double inverseDocumentFrequency(String term, List<List<String>> docs) {
    double numDocs = 0;
    for (List<String> doc : docs) {
      if (doc.contains(term)) {
        numDocs += 1;
      }
    }

    return Math.log(totalSize / (numDocs + 1));
  }

  public double tfIdf(String term, List<String> doc) {
    double tf = termFrequency(term, doc);
    double idf;

    if (frequencies.containsKey(term)) {
      idf = frequencies.get(term);
    } else {
      idf = Math.log(totalSize);
    }

    return tf * idf;
  }
}
