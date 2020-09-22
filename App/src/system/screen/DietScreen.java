package system.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import clinic.Diet;
import clinic.Patient;
import food.Food;
import system.screen.options.Option;
import system.screen.options.OptionTypeEnum;

public class DietScreen extends Screen<Diet> {
  private FoodScreen foodScreen;
  private PatientScreen patientScreen;

  public DietScreen(FoodScreen foodScreen, PatientScreen patientScreen) {
    databasePath = "./App/database/Diet.txt";
    this.foodScreen = foodScreen;
    this.patientScreen = patientScreen;
    if (!loadData()) {
      System.out.println("Ocorreu um problema e não foi possível carregar as dietas.");
    }
    this.title = "Controle de Dietas";
    this.options = Arrays.asList(new Option(OptionTypeEnum.SHOW, "Mostrar Dietas"),
        new Option(OptionTypeEnum.DELETE, "Remover Dieta"));
    this.footer = new Option(OptionTypeEnum.BACK, "Voltar para o Menu Principal");
  }

  public Diet findById(int id) {
    for (Diet diet : data) {
      if (diet.getId() == id) {
        return diet;
      }
    }
    return new Diet();
  }

  public void insertDiet(Diet diet) {
    diet.setId(generateId());
    data.add(diet);
  }

  public boolean loadData() {
    data = new ArrayList<>();
    try {
      Scanner read = new Scanner(new File(databasePath));
      read.nextLine();
      while (read.hasNextLine()) {
        Scanner lineScanner = new Scanner(read.nextLine());
        lineScanner.useDelimiter(";");
        while (lineScanner.hasNext()) {
          String idString = lineScanner.next();
          String patientIdString = lineScanner.next();
          String calorieLimitString = lineScanner.next();
          String foodsString = lineScanner.next();
          int id, patientId;
          float calorieLimit;
          Patient myPatient = new Patient();
          List<Food> foods = new ArrayList<>();
          Scanner foodsScanner = new Scanner(foodsString);
          foodsScanner.useDelimiter(",");
          try {
            id = Integer.parseInt(idString);
            patientId = Integer.parseInt(patientIdString);
            calorieLimit = Float.parseFloat(calorieLimitString);
            while (foodsScanner.hasNext()) {
              String foodIdString = foodsScanner.next();
              int foodId = Integer.parseInt(foodIdString);
              for (Food food : foodScreen.getData()) {
                if (food.getId() == foodId) {
                  foods.add(food);
                  break;
                }
              }
            }
            for (Patient patient : patientScreen.getData()) {
              if (patient.getId() == patientId) {
                myPatient = patient;
                break;
              }
            }
          } catch (NumberFormatException e) {
            e.printStackTrace();
            continue;
          }
          data.add(new Diet(id, myPatient, calorieLimit, foods));
        }
      }
      read.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean saveData() {
    // WIP
    return true;
  }

  public void showData() {
    if (data.size() > 0) {
      System.out.println("\nDietas cadastradas: ");
      for (Diet diet : data) {
        System.out.println(diet.toString());
      }
    } else {
      System.out.println("\nNenhuma Dieta Cadastrada.");
    }
  }

  public boolean addData(int id) {
    return true;
  }

  public boolean editData() {
    return true;
  }

  public boolean removeData(int id, boolean needInteraction) {
    return false;
  }

  public int generateId() {
    int newId = 0;
    for (Diet Diet : data) {
      if (Diet.getId() >= newId) {
        newId = Diet.getId() + 1;
      }
    }
    return newId;
  }

  protected boolean isValidId(int id) {
    boolean valid = false;
    for (Diet Diet : data) {
      if (Diet.getId() == id) {
        valid = true;
      }
    }
    return valid;
  }
}
