package edu.brown.cs.group1.template;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.brown.cs.group1.field.TemplateFields;

/**
 * Template class provides ability to create new forms and customize fields.
 * @author wchoi11
 *
 */
public class Template {
  private int templateId;
  private TemplateFields fields;
  private List<String> tags;
  private List<String> trueContent;
  private String templateName;
  private String time;
  private String timeForFront;
  private Date date;

  /**
   * Constructor.
   * @param templateId
   *          Form id.
   * @param fields
   *          Field names and optionally associated values if object is a form.
   * @param templateName
   *          name of template.
   */
  public Template(int templateId, TemplateFields fields, String templateName) {
    this.templateId = templateId;
    this.fields = fields;
    this.templateName = templateName;
    this.tags = new ArrayList<String>();
    this.trueContent = new ArrayList<String>();
    this.time = new String();
    this.timeForFront = null;
    this.date = null;
  }

  /**
   * Constructor for saving to database.
   * @param fields
   *          Field names and optionally associated values if object is a form.
   */
  public Template(TemplateFields fields) {
    this.fields = fields;
  }

  /**
   * Getter for the time of this form.
   * @return Time of the form.
   */
  public String getTime() {
    return this.time;
  }

  /**
   * Getter for String representation of time.
   * @return Time of the form.
   */
  public String getTimeForFront() {
    return this.timeForFront;
  }

  /**
   * Setter for String time.
   * @param s
   *          String time.
   */
  public void setTimeForFront(String s) {
    this.timeForFront = s;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    try {
      this.date = sdf.parse(s);
      System.out.println("PARSED: THIS DATE IS: " + this.date);
    } catch (ParseException e) {
      System.out.println("ERROR: Parsing failed on date!");
    }
  }

  /**
   * Getter for date.
   * @return date.
   */
  public Date getDate() {
    return this.date;
  }

  /**
   * Setter for time.
   * @param t
   *          Time/date of this form.
   */
  public void setTime(String t) {
    this.time = t;
  }

  /**
   * Get all the form's content.
   * @return Form's content.
   */
  public List<String> getTrueContent() {
    return this.trueContent;
  }

  /**
   * Set all of form's content.
   * @param c
   *          List of strings representing form's content.
   */
  public void setTrueContent(List<String> c) {
    this.trueContent = c;
  }

  /**
   * Get form's tags.
   * @return List of tags.
   */
  public List<String> getTags() {
    return this.tags;
  }

  /**
   * Set form's tags.
   * @param t
   *          Tags.
   */
  public void setTags(List<String> t) {
    this.tags = t;
  }

  /**
   * Get template id.
   * @return Template's id.
   */
  public int getTemplateId() {
    return templateId;
  }

  /**
   * Set template id.
   * @param templateId
   *          id.
   */
  public void setTemplateId(int templateId) {
    this.templateId = templateId;
  }

  /**
   * Get the fields of template as a TemplateFields object.
   * @return TemplateFields object representing fields.
   */
  public TemplateFields getFields() {
    return fields;
  }

  /**
   * Set fields of template as a TemplateFields object.
   * @param fields
   *          TemplateFields object.
   */
  public void setFields(TemplateFields fields) {
    this.fields = fields;
  }

  /**
   * Template name set by user.
   * @return Template name.
   */
  public String getTemplateName() {
    return this.templateName;
  }

  /**
   * Set template name given by user.
   * @param templateName
   *          Template name.
   */
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

}
