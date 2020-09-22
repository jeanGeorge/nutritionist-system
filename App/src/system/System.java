package system;

import system.screen.ConsultScreen;
import system.screen.DietScreen;
import system.screen.FoodScreen;
import system.screen.Screen;
import system.screen.options.OptionTypeEnum;
import system.utils.Util;
import system.screen.MainScreen;
import system.screen.PatientScreen;

public class System {
  private boolean running;

  private Screen currentScreen;
  private MainScreen mainScreen;
  private DietScreen dietScreen;
  private FoodScreen foodScreen;
  private PatientScreen patientScreen;
  private ConsultScreen consultScreen;

  private void executeOption(String option) {
    if (currentScreen instanceof MainScreen) {
      if (option.equals(OptionTypeEnum.EXIT.getDescription())) {
        running = false;
      } else if (option.equals(OptionTypeEnum.CONTROL_FOOD.getDescription())) {
        currentScreen = foodScreen;
      } else if (option.equals(OptionTypeEnum.CONTROL_PATIENTS.getDescription())) {
        currentScreen = patientScreen;
      } else if (option.equals(OptionTypeEnum.CONTROL_DIETS.getDescription())) {
        currentScreen = dietScreen;
      } else if (option.equals(OptionTypeEnum.CONTROL_CONSULTS.getDescription())) {
        currentScreen = consultScreen;
      }
    } else if (currentScreen instanceof DietScreen) {
      if (option.equals(OptionTypeEnum.BACK.getDescription())) {
        currentScreen = mainScreen;
      } else if (option.equals(OptionTypeEnum.SHOW.getDescription())) {
        currentScreen.showData();
      }
    } else {
      if (option.equals(OptionTypeEnum.BACK.getDescription())) {
        currentScreen = mainScreen;
      } else if (option.equals(OptionTypeEnum.SHOW.getDescription())) {
        currentScreen.showData();
      } else if (option.equals(OptionTypeEnum.REGISTER.getDescription())) {
        currentScreen.addData(0);
      } else if (option.equals(OptionTypeEnum.EDIT.getDescription())) {
        currentScreen.editData();
      } else if (option.equals(OptionTypeEnum.DELETE.getDescription())) {
        currentScreen.removeData(0, true);
      }
    }
  }

  private boolean loadSystemData() {
    if (!dietScreen.loadData()) {
      return false;
    }
    if (!foodScreen.loadData()) {
      return false;
    }
    if (!patientScreen.loadData()) {
      return false;
    }
    if (!consultScreen.loadData()) {
      return false;
    }
    return true;
  }

  private void initSystemLoop() {
    do {
      currentScreen.print();
      String optionReadered = Util.readStringFromKeyboard("");
      if (currentScreen.isValidOption(optionReadered.toUpperCase())) {
        executeOption(optionReadered.toUpperCase());
      } else {
        java.lang.System.out.println("Opção inválida!");
        java.lang.System.out.println();
      }
    } while (running);
  }

  private boolean saveSystemData() {
    if (!dietScreen.saveData()) {
      return false;
    }
    if (!foodScreen.saveData()) {
      return false;
    }
    if (!patientScreen.saveData()) {
      return false;
    }
    if (!consultScreen.saveData()) {
      return false;
    }
    return true;
  }

  public System() {
    running = true;

    mainScreen = new MainScreen();
    foodScreen = new FoodScreen();
    patientScreen = new PatientScreen();
    dietScreen = new DietScreen(foodScreen, patientScreen);
    consultScreen = new ConsultScreen(foodScreen, patientScreen, dietScreen);

    currentScreen = mainScreen;

    if (loadSystemData()) {
      initSystemLoop();
      if (saveSystemData()) {
        java.lang.System.out.println("Dados do sistema salvos corretamente... o programa será finalizado.");
      } else {
        java.lang.System.out.println("Ocorreu um problema e não foi possível salvar os dados do sistema.");
      }
    } else {
      java.lang.System.out.println("Ocorreu um problema e não foi possível carregar os dados do sistema.");
    }
  }
}
