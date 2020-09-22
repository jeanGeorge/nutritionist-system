package system.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
  public static String readStringFromKeyboardAndValidate(List<String> validOptions, String label, String errorMessage) {
    Scanner scan = new Scanner(System.in);
    String string = "";
    boolean isValid = false;
    while (!isValid) {
      if (label != "") {
        System.out.print(label);
      }
      string = scan.next();
      if (validOptions.contains(string.toUpperCase())) {
        isValid = true;
      } else {
        if (errorMessage != "") {
          System.out.print(errorMessage);
        }
      }
    }
    return string;
  }

  public static String readStringFromKeyboard(String label) {
    Scanner scan = new Scanner(System.in);
    if (label != "") {
      System.out.print(label);
    }
    String string = scan.next();
    return string;
  }

  public static List<String> readListStringFromKeyboard(String label, String delimiter) {
    Scanner scan = new Scanner(System.in);
    if (label != "") {
      System.out.print(label);
    }
    String string = scan.next();
    String[] stringArraySplited = string.split(",");
    List<String> listString = new ArrayList<>();
    for (String splitedString : stringArraySplited) {
      listString.add(splitedString);
    }
    return listString;
  }

  public static float readFloatFromKeyboard(String label, String errorMessage) {
    Scanner scan = new Scanner(System.in);
    float number = 0;
    String numberString = "";
    boolean isValid = false;
    while (!isValid) {
      try {
        if (label != "") {
          System.out.print(label);
        }
        numberString = scan.next();
        number = Float.parseFloat(numberString);
        isValid = true;
      } catch (NumberFormatException e) {
        if (errorMessage != "") {
          System.out.print(errorMessage);
        }
      }
    }
    return number;
  }

  public static int readIntegerFromKeyboard(String label, String errorMessage) {
    Scanner scan = new Scanner(System.in);
    int number = 0;
    String numberString = "";
    boolean isValid = false;
    while (!isValid) {
      try {
        if (label != "") {
          System.out.print(label);
        }
        numberString = scan.next();
        number = Integer.parseInt(numberString);
        isValid = true;
      } catch (NumberFormatException e) {
        if (errorMessage != "") {
          System.out.print(errorMessage);
        }
      }
    }
    return number;
  }

}
