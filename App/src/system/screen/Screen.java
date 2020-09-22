package system.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import system.screen.options.Option;

public abstract class Screen<T> {
  protected String title;
  protected String helper;
  protected List<Option> options;
  protected Option footer;
  protected List<T> data;
  protected String databasePath;

  public Screen() {
    helper = "Selecione uma opção válida:";
  }

  public void print() {
    System.out.println("------------------------------------------");
    System.out.println(title);
    System.out.println();
    System.out.println(helper);
    System.out.println();
    for (Option option : options) {
      System.out.println(option.toString());
    }
    System.out.println(footer.toString());
  }

  public boolean isValidOption(String readeredOption) {
    for (Option option : options) {
      if (option.getAbbreviation().toUpperCase().equals(readeredOption)) {
        return true;
      }
    }
    return footer.getAbbreviation().toUpperCase().equals(readeredOption);
  }

  public List<T> getData() {
    return data;
  }

  public abstract T findById(int id);

  public abstract boolean loadData();

  public abstract boolean saveData();

  public abstract void showData();

  public abstract boolean addData(int id);

  public abstract boolean editData();

  public abstract boolean removeData(int id, boolean needInteraction);

  protected abstract int generateId();

  protected abstract boolean isValidId(int id);
}
