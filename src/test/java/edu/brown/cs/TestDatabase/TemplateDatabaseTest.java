package edu.brown.cs.TestDatabase;

import edu.brown.cs.group1.database.TemplatesDatabase;
import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.template.Template;
import edu.brown.cs.group1.textloader.TextFileLoader;
import org.junit.Before;
import org.junit.Test;

public class TemplateDatabaseTest {
    TemplatesDatabase td;
    Template template1;
    Template template2;
    Template template3;
    Template template4;

    @Before
    public void setUp() {
        td = new TemplatesDatabase("data/database/forms.sqlite3");
        template1 = new Template(1,
                TemplateFields.valueOf("Name;Procedure;SecondaryProcedure;"), "Procedure History");
        template2 = new Template(2, TemplateFields.valueOf("Name;Procedure;"),
                "Procedure Documentation");
        template3 = new Template(3, TemplateFields.valueOf("Procedure;"),
                "One Procedure");
        template4 = new Template(4, TemplateFields.valueOf("Procedure;SecondaryProcedure;"),
                "Two Procedures");
    }

    @Test
    public void TestSaveTemplate() {
//        td.saveTemplate(template1);
//        td.saveTemplate(template2);
//        td.saveTemplate(template3);
//        td.saveTemplate(template4);
    }
}
