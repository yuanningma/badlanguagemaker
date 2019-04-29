package edu.brown.cs.TestDatabase;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.template.Template;
import edu.brown.cs.group1.textloader.TextFileLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import java.util.ArrayList;

public class FormDatabaseTest {
    FormsDatabase formDatabase;
    Template template;
    TextFileLoader textFileLoader;

    @Before
    public void setUp() {
        formDatabase = new FormsDatabase("data/database/form.sqlite3");
        template = new Template(1, TemplateFields.valueOf("Name;Procedure;SecondaryProcedure;") );
        textFileLoader = new TextFileLoader("data/medicalTerminology/MOCK_DATA (7).csv");
    }
//    @Test
//    public void TestSaveForms() {
//        List<String> strings = textFileLoader.fileLoader();
//        for(int i = 1; i < strings.size(); i++) {
//            String[] arr = strings.get(i).split(",");
//            System.out.println(arr[1]);
//            switch (arr.length) {
//                case 5: template = new
//                        Template(Integer.parseInt(arr[0]),
//                        TemplateFields.valueOf(
//                                "Name;" + arr[4] + ";" + "Procedure;" + arr[2] + ";" + "SecondaryProcedure;" + arr[3]));
//                formDatabase.saveForm(template, Integer.parseInt(arr[1]));
//                break;
//                case 4: switch (arr[3]) {
//                    case "" : template = new
//                            Template(Integer.parseInt(arr[0]),
//                            TemplateFields.valueOf(
//                                    "Name;" + arr[4] + ";" + "Procedure;" + arr[2] + ";"));
//                    break;
//                    default: template = new
//                            Template(Integer.parseInt(arr[0]),
//                            TemplateFields.valueOf(
//                                    "Procedure;" + arr[2] + ";" + "SecondaryProcedure;" + arr[3]));
//                        formDatabase.saveForm(template, Integer.parseInt(arr[1]));
//                        break;
//                }
//                case 3: template = new Template(
//                        Integer.parseInt(arr[0]),
//                        TemplateFields.valueOf("Procedure;" + arr[2] + ";"));
//                formDatabase.saveForm(template,Integer.parseInt(arr[1]));
//                break;
//                case 0: break;
//            }
//        }
//    }
}
