package edu.brown.cs.group1.similarity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.group1.database.TemplatesDatabase;
import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.template.Template;

public class TestExactSimilarity {

  private TemplatesDatabase tempDb;

  @Before
  public void setUp() {
    tempDb = new TemplatesDatabase("data/database/templates.sqlite3");
  }

  @Test
  public void testConstructor() {
    ExactSimilarity checker = new ExactSimilarity(tempDb);
    assertNotNull(checker);
  }

  @Test
  public void testTwoTempsSimil() {
    ExactSimilarity checker = new ExactSimilarity(tempDb);
    TemplateFields fields1 = TemplateFields.valueOf("Name,Age,Weight,DOB,");
    TemplateFields fields2 = TemplateFields.valueOf("Name,Age,Weight,Height,");
    Template form1 = new Template(1, fields1, "General");
    Template form2 = new Template(2, fields2, "General");
    double ans = ExactSimilarity.twoTempsSimil(form1, form2);
    assertTrue(ans == 0.75);
  }

  @Test
  public void testStandardizeTwoTempsSimil() {
    ExactSimilarity checker = new ExactSimilarity(tempDb);
    TemplateFields fields1 = TemplateFields.valueOf("Name,Age,Weight,DOB,");
    TemplateFields fields2 = TemplateFields.valueOf("Name,Ag  E,WeI  ght,Dob,");
    Template form1 = new Template(1, fields1, "General");
    Template form2 = new Template(2, fields2, "General");
    double ans = ExactSimilarity.twoTempsSimil(form1, form2);
    System.out.println(ans);
    assertTrue(ans == 1.0);
  }

}
