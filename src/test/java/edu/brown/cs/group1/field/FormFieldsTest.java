package edu.brown.cs.group1.field;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FormFieldsTest {

  @Test
  public void testToString() {
    FormFields fields =
        FormFields.valueOf("Name;Eric;Age;12;Weight;234;DOB;071296;");
    assertTrue(
        fields.toString().equals("Name;Eric;Age;12;Weight;234;DOB;071296;"));
  }

  @Test
  public void testValueOf() {
    TemplateFields fields =
        TemplateFields.valueOf("Name;Eric;Age;12;Weight;234;DOB;071296;");
    assertTrue(fields.getFields().get(0).equals("Name"));
    assertTrue(fields.getFields().get(1).equals("Eric"));
    assertTrue(fields.getFields().get(2).equals("Age"));
    assertTrue(fields.getFields().get(3).equals("12"));
    assertTrue(fields.getFields().get(4).equals("Weight"));
    assertTrue(fields.getFields().get(5).equals("234"));
    assertTrue(fields.getFields().get(6).equals("DOB"));
    assertTrue(fields.getFields().get(7).equals("071296"));
  }

}
