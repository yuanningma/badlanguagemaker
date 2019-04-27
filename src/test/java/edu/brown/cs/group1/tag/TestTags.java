package edu.brown.cs.group1.tag;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class TestTags {

  @Test
  public void testConstructor() {
    Tags tags = new Tags(new HashMap<>());
    assertNotNull(tags);
  }

  @Test
  public void testRegisterNewTag() {
    Tags tags = new Tags(new HashMap<>());
    tags.registerNewTag("blue", "bone");
    assertTrue(tags.containsTag("blue"));
    assertTrue(tags.containsKeyword("bone"));

    // Whether method does not register a new tag if the new keyword already
    // exists.
    tags.registerNewTag("white", "bone");
    assertTrue(tags.containsTag("blue"));
    assertTrue(tags.containsKeyword("bone"));

    // Whether method registers a new keyword if the tag already
    // exists.
    tags.registerNewTag("blue", "blood");
    assertTrue(tags.containsTag("blue"));
    assertTrue(tags.containsKeyword("blood"));
  }
}
