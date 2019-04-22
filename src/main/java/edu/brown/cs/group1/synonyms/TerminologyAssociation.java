package edu.brown.cs.group1.synonyms;

/**
 * A class that associates body parts to common roots.
 */
public class TerminologyAssociation {
  private String term;
  private String[] roots;

  TerminologyAssociation(String term, String[] roots) {
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
  public String[] getRoots() {
    return roots;
  }
    /**
     * Setter method for the root.
     * @param roots
     *          a string array to set bodyPart
     */
  public void setRoots(String[] roots) {
    this.roots = roots;
  }
}
