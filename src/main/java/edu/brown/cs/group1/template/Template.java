package edu.brown.cs.group1.template;

import java.util.List;

/**
 * Template class provides ability to create new forms and customize fields.
 * @author wchoi11
 *
 */
public class Template {
  private int templateId;
  private List<String> fields;

  /**
   * Constructor.
   * @param templateId
   *          Form id.
   * @param fields
   *          Field names and optionally associated values if object is a form.
   */
  public Template(int templateId, List<String> fields) {
    this.templateId = templateId;
    this.fields = fields;
  }

  public int getTemplateId() {
    return templateId;
  }

  public void setTemplateId(int templateId) {
    this.templateId = templateId;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

  // TODO: Method for extracting fields and associated values from existing
  // form.

}
