package edu.brown.cs.group1.template;

import edu.brown.cs.group1.field.TemplateFields;
import edu.brown.cs.group1.tag.Tags;

/**
 * Template class provides ability to create new forms and customize fields.
 * @author wchoi11
 *
 */
public class Template {
  private int templateId;
  private TemplateFields fields;
  private Tags tags;

  /**
   * Constructor.
   * @param templateId
   *          Form id.
   * @param fields
   *          Field names and optionally associated values if object is a form.
   */
  public Template(int templateId, TemplateFields fields) {
    this.templateId = templateId;
    this.fields = fields;
  }

  /**
   * Constructor for saving to database.
   * @param fields
   *          Field names and optionally associated values if object is a form.
   */
  public Template(TemplateFields fields) {
    this.fields = fields;
  }

  public Tags getTags() {
    return this.tags;
  }

  public int getTemplateId() {
    return templateId;
  }

  public void setTemplateId(int templateId) {
    this.templateId = templateId;
  }

  public TemplateFields getFields() {
    return fields;
  }

  public void setFields(TemplateFields fields) {
    this.fields = fields;
  }

  // TODO: Method for extracting fields and associated values from existing
  // form.

}
