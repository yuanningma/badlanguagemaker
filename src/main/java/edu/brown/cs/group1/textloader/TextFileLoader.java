package edu.brown.cs.group1.textloader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class loads in text file and converts the contents
 * into a string list.
 */
public class TextFileLoader {
  private String filepath;

  TextFileLoader(String filepath) {
    this.filepath = filepath;
  }

 /**
   * This is a method that loads in the contents of a file.
  * @return
  *     a list of strings containing the contents of the file
  */
  public List<String> fileLoader() {
    List<String> contents = new ArrayList<>();
    BufferedReader reader;

    try {

      reader = new BufferedReader(new FileReader(filepath));
      String currentLine = reader.readLine();

      while (currentLine != null) {
        contents.add(currentLine);
        currentLine = reader.readLine();
      }

    } catch (FileNotFoundException ioe) {
      ioe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return contents;
  }
}
