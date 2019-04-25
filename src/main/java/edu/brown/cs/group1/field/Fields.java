package edu.brown.cs.group1.field;

import java.util.Map;

/**
 * Provides a fields object for either forms or templates to access fields and
 * parse into a string in order to store in the database.
 * @author wchoi11
 *
 */
public interface Fields {

  String toString();

  Map<String, String> getContent();

}