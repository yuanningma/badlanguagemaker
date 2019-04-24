package edu.brown.cs.group1.field;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FormFields implements Fields {

  private Map<String, String> fields;

  public FormFields(Map<String, String> fields) {
    this.fields = fields;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  @Override
  public String toString() {
    Set<String> keys = fields.keySet();
    Iterator<String> it = keys.iterator();
    StringBuilder result = new StringBuilder();
    while (it.hasNext()) {
      String key = it.next();
      result.append(key).append(";").append(fields.get(key)).append(";");
    }
    return result.toString();
  }

  /**
   * Returns FormFields object with field names and associated values.
   * @param fieldsString
   *          String representation of a fields object.
   * @return List of fields and optionally associated values if a form.
   */
  public static FormFields valueOf(String fieldsString) {
    Map<String, String> fields = new LinkedHashMap<>();
    String[] fieldsArr = fieldsString.split(";");
    for (int i = 0; i < fieldsArr.length; i += 2) {
      fields.put(fieldsArr[i], fieldsArr[i + 1]);
    }
    FormFields formFields = new FormFields(fields);
    return formFields;
  }

}
