package edu.brown.cs.group1.similarity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.group1.field.FormFields;
import edu.brown.cs.group1.template.Template;

public class TestExactSimilaritySorter {

  @Test
  public void testSort() {
    FormFields fields1 =
        FormFields.valueOf("Name;Eric;Age;12;Weight;234;DOB;071296;");
    FormFields fields2 =
        FormFields.valueOf("Name;Eric;Age;12;Weight;234;Height;071296;");
    Template form1 = new Template(1, fields1);
    Template form2 = new Template(2, fields2);
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
