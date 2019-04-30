package edu.brown.cs.group1.search;

import org.junit.Test;

public class RelevanceTest {
  @Test
  public void testGenerateTerms() {
    Relevance r = new Relevance();
    r.generateTerms("cardiovascular");
  }
}
