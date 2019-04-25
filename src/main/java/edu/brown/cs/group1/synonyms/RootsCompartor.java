package edu.brown.cs.group1.synonyms;
import java.util.Comparator;

/**
 * Roots Compartor class that implements the Comparator interface.
 * Roots are compared based on there lengths.
 */
public class RootsCompartor implements Comparator<String> {

  @Override
   public int compare(String s1, String s2) {
    return s2.length() - s1.length();
  }
}
