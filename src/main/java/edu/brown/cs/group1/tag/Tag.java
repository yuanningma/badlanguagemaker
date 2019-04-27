package edu.brown.cs.group1.tag;

import java.util.Map;

/**
 * Tag class provides ability to mark forms based on various keywords in order
 * to optimize searching for relevance.
 * @author wchoi11
 *
 */
public class Tag {

  private Map<String, String> tags;

  /**
   * Constructor.
   * @param tags
   *          Current map of tags to keywords.
   */
  public Tag(Map<String, String> tags) {
    this.tags = tags;
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
