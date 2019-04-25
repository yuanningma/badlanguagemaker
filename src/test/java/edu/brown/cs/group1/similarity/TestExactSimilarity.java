package edu.brown.cs.group1.similarity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs.group1.field.FormFields;
import edu.brown.cs.group1.template.Template;

public class TestExactSimilarity {

  @Test
  public void testConstructor() {
    ExactSimilarity checker = new ExactSimilarity("not_a_db");
    assertNotNull(checker);
  }

  @Test
  public void testTwoFormsSimil() {
    ExactSimilarity checker = new ExactSimilarity("not_a_db");
    FormFields fields1 =
        FormFields.valueOf("Name;Eric;Age;12;Weight;234;DOB;071296;");
    FormFields fields2 =
        FormFields.valueOf("Name;Eric;Age;12;Weight;234;Height;071296;");
    Template form1 = new Template(1, fields1);
    Template form2 = new Template(2, fields2);
    double ans = checker.twoFormsSimil(form1, form2);
    assertTrue(ans == 0.6);
  }

}
