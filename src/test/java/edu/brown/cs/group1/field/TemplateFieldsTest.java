package edu.brown.cs.group1.field;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TemplateFieldsTest {

  @Test
  public void testToString() {
    TemplateFields fields = TemplateFields.valueOf("Name;Age;Weight;DOB;");
    assertTrue(fields.toString().equals("Name;Age;Weight;DOB;"));
  }

  @Test
  public void testValueOf() {
    TemplateFields fields = TemplateFields.valueOf("Name;Age;Weight;DOB;");
    assertTrue(fields.getFields().get(0).equals("Name"));
    assertTrue(fields.getFields().get(1).equals("Age"));
    assertTrue(fields.getFields().get(2).equals("Weight"));
    assertTrue(fields.getFields().get(3).equals("DOB"));
  }
}
