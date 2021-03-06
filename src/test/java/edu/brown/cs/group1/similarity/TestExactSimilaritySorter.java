package edu.brown.cs.group1.similarity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.template.Template;

public class TestExactSimilaritySorter {

  @Test
  public void testSort() {
    TemplateFields fields1 =
        TemplateFields.valueOf("Name;Eric;Age;12;Weight;234;DOB;071296;");
    TemplateFields fields2 =
        TemplateFields.valueOf("Name;Eric;Age;12;Weight;234;Height;071296;");
    Template form1 = new Template(1, fields1, "General");
    Template form2 = new Template(2, fields2, "General");
    ExactSimilaritySorter sorter = new ExactSimilaritySorter(form1);
    List<Template> toSort = new ArrayList<>();
    toSort.add(form2);
    toSort.add(form1);
    toSort.sort(sorter);
    List<Template> ans = new ArrayList<>();
    ans.add(form1);
    ans.add(form2);
    assertTrue(ans.equals(toSort));
  }

}
