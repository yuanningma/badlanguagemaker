package edu.brown.cs.group1.synonyms;

import java.util.HashSet;

/**
 * A class that associates body parts to common roots.
 */
public class TerminologyAssociation {
  private String term;
  private HashSet<String> roots;

  TerminologyAssociation(String term, HashSet<String> roots) {
    this.term = term;
    this.roots = roots;
  }

/**
  * Getter method for the term.
  * @return
  *        a term string
  */
  public String getTerm() {
    return term;
  }

    /**
     * Setter method for the bodyPart.
     * @param term
     *          a string to set bodyPart
     */
  public void setTerm(String term) {
    this.term = term;
  }
 /**
  * Getter method for the bodyPart.
  * @return
  *        a bodyPart string
  */
  public HashSet<String> getRoots() {
    return roots;
  }
    /**
     * Setter method for the root.
     * @param roots
     *          a string array to set bodyPart
     */
  public void setRoots(HashSet<String> roots) {
    this.roots = roots;
  }

    /**
     * Method that checks if a root is contain in roots.
     * @param root
     *          a string representing a root
     * @return
     *        a boolean true is root is contain in the
     *        objects root, false otherwise.
     */
  public boolean containsRoot(String root) {
    return roots.contains(root);
  }

    /**
     * Inserts a root if not present in the hashSet.
     * @param root
     *           a root to be added to roots.
     */
  public void addRoot(String root) {
    if (!containsRoot(root)) {
      roots.add(root);
    }
  }
}
