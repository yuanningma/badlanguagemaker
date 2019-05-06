package edu.brown.cs.TestDatabase;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.group1.database.FormsDatabase;
import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.template.Template;
import edu.brown.cs.group1.textloader.TextFileLoader;

public class FormDatabaseTest {
    FormsDatabase formDatabase;
    Template template;
    TextFileLoader textFileLoader;

    @Before
    public void setUp() {
        formDatabase = new FormsDatabase("data/database/forms.sqlite3");
        template = new Template(2,
                TemplateFields.valueOf("Name;Procedure;"), "Procedure Documentation");
        textFileLoader =
                new TextFileLoader("data/medicalTerminology/MOCK_DATA (1).csv");
    }

    @Test
    public void TestSaveForms() {
////        //dd/MM/yyyy HH:mm:ss
//        template = new Template(5,
//                TemplateFields.valueOf("Name,Veronica Rich Cole,Procedure,Re-entry operation aorta, Notes," +
//                        "Minor Complication Pre-Surgery: Low Blood Pressure"), "Aorta Procedure");
//        formDatabase.saveForm(template, 1, "05/31/2010 10:41:43");
//        template = new Template(3,
//                TemplateFields.valueOf("Procedure, Intravascular pressure measurement of intrathoracic arteries"), "Blood Pressure Measurement");
//        formDatabase.saveForm(template, 1, "01/06/2017 08:41:23");
//        template = new Template(2, TemplateFields.valueOf("Procedure, Rhythm electrocardiogram,"), "Cardiogram");
//        formDatabase.saveForm(template, 1, "10/04/2017 10:34:32");
//        template = new Template(3,
//                TemplateFields.valueOf("Procedure, Esophagostomy,"), "Esophagostomy");
//        formDatabase.saveForm(template, 1, "04/07/2019 10:20:18");
//        template = new Template(6, TemplateFields.valueOf("Procedure, Other intrathoracic cascular " +
//                "shunt or bypass,Secondary Procedure, Spinal traction using skull device, Notes, Patient was in massive car accident,"), "Car Accident");
//        formDatabase.saveForm(template, 1, "10/07/2018 06:34:12");
//        template = new Template(5,
//                TemplateFields.valueOf("Procedure, Removal of carotid sinus stimulation pulse generator only,"), "Removal of Carotid");
//        formDatabase.saveForm(template, 1, "10/08/2018 05:42:12");
//        template = new Template(5,
//                TemplateFields.valueOf("Procedure, Atrial cardioversion,"), "Cardioversion");
//        formDatabase.saveForm(template, 1, "10/08/2018 09:31:12");
//        template = new Template(3,
//                TemplateFields.valueOf("Procedure, Measurement fo systematic arterial blood gases,"), "Blood gases");
//        formDatabase.saveForm(template, 1, "10/17/2018 05:12:12");
//        template = new Template(4, TemplateFields.valueOf("First Procedure, Removal of skull plate, SecondaryProcedure, Insertion bone growth simulator,"), "Bone Repair");
//        formDatabase.saveForm(template, 1, "10/21/2018 04:21:12");
//        template = new Template(5, TemplateFields.valueOf("Procedure;Lobectomy of liver;"), "One Procedure");
//        formDatabase.saveForm(template, 1, "08/12/2003 03:12:11");
//        template = new Template(1,
//                TemplateFields.valueOf("Name,Veronica Rich,Procedure," +
//                        " Endovascular (total) embolization or occlusion of head and neck vessels, Secondary Procedure, Pulmonary artrey wedge monitoring,"), "Head and Neck Repair");
//        formDatabase.saveForm(template, 6, "11/25/2018 01:21:12");
//        template = new Template(3,
//                TemplateFields.valueOf("Procedure, Closed reduction of dislocation of hand and finger,"), "Finger Dislocation");
//        formDatabase.saveForm(template, 6, "10/07/2018 02:12:23");


//    List<String> strings = textFileLoader.fileLoader();
//    for (int i = 1; i < strings.size(); i++) {
//      String[] arr = strings.get(i).split(",");
//      switch (arr.length) {

//     case 4:
//              switch (arr[3]) {
//                  case "":
//                      template = new Template(Integer.parseInt(arr[0]),
//                              TemplateFields.valueOf(
//                                      "Emergency Contact;" + arr[4] + ";" + "Procedure;" + arr[2] + ";"), "Procedure Documentation");
//                      break;
//                  default:
//                      template = new Template(Integer.parseInt(arr[0]),
//                              TemplateFields.valueOf("Procedure;" + arr[2]
//                                      + ";"
//                                      + "SecondaryProcedure;"
//                                      + arr[3]),"Two Procedures");
//                      formDatabase.saveForm(template, Integer.parseInt(arr[1]));
//                      break;
//              }
//          case 3:
//              template = new Template(Integer.parseInt(arr[0]),
//                      TemplateFields.valueOf("Procedure;" + arr[2] + ";"), "One Procedure");
////              formDatabase.saveForm(template, Integer.parseInt(arr[1]));
////              break;
//      default:
//        template = new Template(Integer.parseInt(arr[0]),
//            TemplateFields.valueOf("Procedure;"
//                + arr[2]
//                + ";"
//                + "SecondaryProcedure;"
//                + arr[3]), arr[5]);
//        formDatabase.saveForm(template, Integer.parseInt(arr[1]));
//        break;
//
//      case 0:
//        break;
//      }
//    }
//  }

    }
}
