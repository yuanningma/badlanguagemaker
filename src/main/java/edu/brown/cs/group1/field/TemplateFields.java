package edu.brown.cs.group1.field;

import java.util.ArrayList;
import java.util.List;

public class TemplateFields implements Fields {

  private List<String> fields;

  public TemplateFields(List<String> fields) {
    this.fields = fields;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (String field : fields) {
      result.append(field).append(";");
    }
    return result.toString();
  }

  public List<String> getContent() {
    return fields;
  }

  /**
   * Returns TemplateFields object with field names.
   * @param fieldsString
   *          String representation of a fields object.
   * @return List of fields and optionally associated values if a form.
   */
  public static TemplateFields valueOf(String fieldsString) {
    List<String> fields = new ArrayList<>();
    String[] fieldsArr = fieldsString.split(";");
    for (String field : fieldsArr) {
      fields.add(field);
    }
    TemplateFields templateFields = new TemplateFields(fields);
    return templateFields;
  }

}
