package edu.brown.cs.group1.synonyms;

import java.util.PriorityQueue;

/**
 * A class that associates body parts to common roots.
 */
public class TerminologyAssociation {
  private String term;
  private PriorityQueue<String> roots;

    /**
     * Terminology Association constructor.
     * @param term
     *           the principal term
     * @param roots
     *           a PriorityQueue that contains roots corresponding to the database.
     */
  public TerminologyAssociation(String term, PriorityQueue<String> roots) {
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
  public PriorityQueue<String> getRoots() {
    return roots;
  }
    /**
     * Setter method for the root.
     * @param roots
     *          a string array to set bodyPart
     */
  public void setRoots(PriorityQueue<String> roots) {
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
