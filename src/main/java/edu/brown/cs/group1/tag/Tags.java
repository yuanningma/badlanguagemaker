package edu.brown.cs.group1.tag;

import java.util.Map;

/**
 * Tags class provides ability to mark forms based on various keywords in order
 * to optimize searching for relevance.
 * @author wchoi11
 *
 */
public class Tags {

  private Map<String, String> tags;

  /**
   * Constructor.
   * @param tags
   *          Current map of tags to keywords.
   */
  public Tags(Map<String, String> tags) {
    this.tags = tags;
  }

  /**
   * Returns whether tag is in tags map.
   * @param tag
   *          Tag to check.
   * @return true if tag exists in tags map. false otherwise.
   */
  public boolean containsTag(String tag) {
    return tags.containsValue(tag);
  }

  /**
   * Returns whether keyword is in tags map.
   * @param keyword
   *          Keyword to check.
   * @return true if keyword exists in tags map. false otherwise.
   */
  public boolean containsKeyword(String keyword) {
    return tags.containsKey(keyword);
  }

  // [NOTE] registerNewTag does not check if tag already exists, meaning it will
  // overwrite the previous keyword with the new one. We might want to throw
  // some error if the new keyword already exists in the map.
  /**
   * Registers new tag with associated keyword to map of tags to keywords. This
   * method does not register a new tag if the new keyword already exists in the
   * map.
   * @param tag
   *          New tag.
   * @param keyword
   *          New keyword.
   */
  public void registerNewTag(String tag, String keyword) {
    if (!tags.containsKey(keyword)) {
      tags.put(keyword, tag);
    }
  }

  /**
   * Method the retrieves the associated tag with a given key word.
   * @param keyword
   *          a keyword in which to retrieve the needed tag.
   * @return a string representing the associated tag.
   */
  public String getTag(String keyword) {
    return tags.get(keyword);
  }

  // /**
  // * Parses string for tags map and returns
  // * @param tagsString
  // * @return
  // */
  // public static Tags valueOf(String tagsString) {
  // Map<String, String> tags = new HashMap<>();
  // String[] tagsArr = tagsString.split(";");
  // for (int i = 0; i < tagsArr.length; i += 2) {
  // tags.put(tagsArr[i], tagsArr[i + 1]);
  // }
  // Tags allTags = new Tags(tags);
  // return allTags;
  // }
}
