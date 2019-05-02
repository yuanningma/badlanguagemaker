package edu.brown.cs.group1.template;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.group1.field.TemplateFields;

/**
 * Template class provides ability to create new forms and customize fields.
 * @author wchoi11
 *
 */
public class Template {
  private int templateId;
  private String name;
  private TemplateFields fields;
  private List<String> tags;
  private List<String> trueContent;

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
    this.tags = new ArrayList<String>();
    this.trueContent = new ArrayList<String>();
  }

  /**
   * Constructor for saving to database.
   * @param fields
   *          Field names and optionally associated values if object is a form.
   */
  public Template(TemplateFields fields) {
    this.fields = fields;
  }

  public List<String> getTrueContent() {
    return this.trueContent;
  }

  public void setTrueContent(List<String> c) {
    this.trueContent = c;
  }

  public List<String> getTags() {
    return this.tags;
  }

  public void setTags(List<String> t) {
    this.tags = t;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // TODO: Method for extracting fields and associated values from existing
  // form.

}
