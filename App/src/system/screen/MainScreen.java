package system.screen;

import java.util.Arrays;
import java.util.List;

import system.screen.options.Option;
import system.screen.options.OptionTypeEnum;

public class MainScreen extends Screen<String> {
  public MainScreen() {
    this.title = "Menu Principal";
    this.options = Arrays.asList(new Option(OptionTypeEnum.CONTROL_FOOD, "Controlar Alimentos"),
        new Option(OptionTypeEnum.CONTROL_PATIENTS, "Controlar Pacientes"),
        new Option(OptionTypeEnum.CONTROL_DIETS, "Controlar Dietas"),
        new Option(OptionTypeEnum.CONTROL_CONSULTS, "Controlar Consultas"));
    this.footer = new Option(OptionTypeEnum.EXIT, "Sair do Sistema");
  }

  public boolean loadData() {
    return true;
  }

  public boolean saveData() {
    return true;
  }

  public void showData() {

  }

  public boolean addData(int id) {
    return true;
  }

  public boolean editData() {
    return true;
  }

  public boolean removeData(int id, boolean needInteraction) {
    return true;
  }

  public String findById(int id) {
    return "";
  }

  protected int generateId() {
    return 0;
  }

  protected boolean isValidId(int id) {
    return true;
  }
}
