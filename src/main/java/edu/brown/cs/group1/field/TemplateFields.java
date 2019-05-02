package edu.brown.cs.group1.field;

import java.util.ArrayList;
import java.util.List;

/**
 * TemplateFields class provides ability to get labels depending on whether
 * template is a form and parsing a string for fields.
 * @author wchoi11
 *
 */
public class TemplateFields {

  private List<String> fields;

  /**
   * Constructor.
   * @param fields
   *          Fields for this template.
   */
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

  /**
   * Return fields of template (the entire form for relevance search).
   * @return Fields of template.
   */
  public List<String> getContent() {
    return fields;
  }

  /**
   * Gets labels of all the fields from template.
   * @param isForm
   *          true if template is a form, meaning field values are included.
   *          false if template.
   * @return All labels.
   */
  public List<String> getLabels(boolean isForm) {
    if (isForm) {
      List<String> labels = new ArrayList<>();
      for (int i = 0; i < fields.size(); i += 2) {
        labels.add(fields.get(i));
      }
      return labels;
    } else {
      return fields;
    }
  }

  /**
   * Returns TemplateFields object with field names.
   * @param fieldsString
   *          String representation of a fields object.
   * @return List of fields and optionally associated values if a form.
   */
  public static TemplateFields valueOf(String fieldsString) {
    List<String> fields = new ArrayList<>();
    // String[] fieldsArr = fieldsString.split(";");
    String[] fieldsArr = fieldsString.split(",");
    if (fieldsArr.length == 1) {
      fieldsArr = fieldsString.split(";");
    }

    for (String field : fieldsArr) {
      fields.add(field);
    }
    TemplateFields templateFields = new TemplateFields(fields);
    return templateFields;
  }

}
