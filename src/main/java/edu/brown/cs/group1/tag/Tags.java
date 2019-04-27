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
    return tags.containsKey(tag);
  }

  /**
   * Returns whether keyword is in tags map.
   * @param keyword
   *          Keyword to check.
   * @return true if keyword exists in tags map. false otherwise.
   */
  public boolean containsKeyword(String keyword) {
    return tags.containsValue(keyword);
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
    if (!tags.containsValue(keyword)) {
      tags.put(tag, keyword);
    }
  }

}
