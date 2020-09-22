package system.screen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import clinic.Consult;
import clinic.Diet;
import clinic.Patient;
import food.Animal;
import food.Food;
import food.Mineral;
import food.Vegetable;
import system.screen.options.Option;
import system.screen.options.OptionTypeEnum;
import system.utils.Util;

public class ConsultScreen extends Screen<Consult> {
  private FoodScreen foodScreen;
  private PatientScreen patientScreen;
  private DietScreen dietScreen;

  public ConsultScreen(FoodScreen foodScreen, PatientScreen patientScreen, DietScreen dietScreen) {
    this.foodScreen = foodScreen;
    this.patientScreen = patientScreen;
    this.dietScreen = dietScreen;
    databasePath = "../database/Consult.txt";
    if (!loadData()) {
      System.out.println("Ocorreu um problema e não foi possível carregar as Consultas.");
    }
    this.title = "Controle de Consultas";
    this.options = Arrays.asList(new Option(OptionTypeEnum.SHOW, "Mostrar Consultas"),
        new Option(OptionTypeEnum.REGISTER, "Realizar Consulta"), new Option(OptionTypeEnum.EDIT, "Editar Consulta"),
        new Option(OptionTypeEnum.DELETE, "Remover Consulta"));
    this.footer = new Option(OptionTypeEnum.BACK, "Voltar para o Menu Principal");
  }

  public Consult findById(int id) {
    for (Consult consult : data) {
      if (consult.getId() == id) {
        return consult;
      }
    }
    return new Consult();
  }

  public int generateId() {
    int newId = 0;
    for (Consult Diet : data) {
      if (Diet.getId() >= newId) {
        newId = Diet.getId() + 1;
      }
    }
    return newId;
  }

  protected boolean isValidId(int id) {
    boolean valid = false;
    for (Consult Diet : data) {
      if (Diet.getId() == id) {
        valid = true;
      }
    }
    return valid;
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
          String dietIdString = lineScanner.next();
          String date = lineScanner.next();
          String schedule = lineScanner.next();
          String weightString = lineScanner.next();
          String bodyFatPercentageString = lineScanner.next();
          String physicalSensation = lineScanner.next();
          String dietaryRestrictions = lineScanner.next();
          int id, patientId, dietId;
          Patient patient = new Patient();
          Diet diet = new Diet();
          float weight, bodyFatPercentage;
          try {
            id = Integer.parseInt(idString);
            patientId = Integer.parseInt(patientIdString);
            dietId = Integer.parseInt(dietIdString);
            weight = Float.parseFloat(weightString);
            bodyFatPercentage = Float.parseFloat(bodyFatPercentageString);
            if (patientScreen.isValidId(patientId)) {
              patient = patientScreen.findById(patientId);
            }
            if (dietScreen.isValidId(dietId)) {
              diet = dietScreen.findById(dietId);
            }
            data.add(new Consult(id, patient, diet, date, schedule, weight, bodyFatPercentage, physicalSensation,
                dietaryRestrictions));
          } catch (NumberFormatException e) {
            e.printStackTrace();
            continue;
          }
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
    for (Consult consult : data) {
      System.out.println(consult.toString());
    }
  }

  public boolean addData(int id) {
    if (id == 0) {
      id = generateId();
      System.out.println("\nCadastro de Dietas");
    }
    int patientId, possibleDietId;
    float calorieLimit, bodyFatPercentage, weight;
    String date, schedule, physicalSensation, dietaryRestrictions;
    Map<Integer, List<Food>> possibleDiets;
    List<Food> foods;
    patientScreen.showData();
    patientId = Util.readIntegerFromKeyboard("Selecione o Paciente que receberá a dieta: ", "Paciente inválido. ");
    if (!patientScreen.isValidId(patientId)) {
      System.out.println("Paciente inválido.");
      return false;
    }
    Patient patient = patientScreen.findById(patientId);
    if (patient.equals(null)) {
      System.out.println("Paciente inválido.");
      return false;
    }
    calorieLimit = Util.readFloatFromKeyboard("Digite a quantidade de calorias máxima da Dieta: ",
        "Quantidade inválida. ");
    possibleDiets = calculatePossibleDiets(calorieLimit);
    showPossibleDiets(possibleDiets);
    possibleDietId = Util.readIntegerFromKeyboard("Selecione a Dieta: ", "Valor inválido. ");
    foods = possibleDiets.get(possibleDietId);
    if (foods.equals(null)) {
      System.out.println("Valor inválido.");
      return false;
    }
    Diet diet = new Diet(0, patient, calorieLimit, foods);
    dietScreen.insertDiet(diet);
    date = Util.readStringFromKeyboard("Digite a Data do Atendimento: ");
    schedule = Util.readStringFromKeyboard("Digite o Horário do Atendimento: ");
    weight = Util.readFloatFromKeyboard("Digite o Peso do Paciente: ", "% inválido.");
    bodyFatPercentage = Util.readFloatFromKeyboard("Digite o % de gordura do Paciente: ", "Peso inválido.");
    physicalSensation = Util.readStringFromKeyboard("Digite a Sensação Física do Paciente: ");
    dietaryRestrictions = Util.readStringFromKeyboard("Digite a Restrição de Dieta do Paciente: ");
    data.add(new Consult(id, patient, diet, date, schedule, weight, bodyFatPercentage, physicalSensation,
        dietaryRestrictions));
    return true;
  }

  public boolean editData() {
    int id;
    System.out.println("\nEdição de Consulta");
    showData();
    id = Util.readIntegerFromKeyboard("Selecione a Consulta a ser editada: ", "Consulta inválida. ");
    if (!isValidId(id)) {
      System.out.println("Consulta inválida. ");
      return false;
    }
    if (removeData(id, false)) {
      if (addData(id)) {
        System.out.println("Consulta editada com sucesso!");
        return true;
      } else {
        System.out.println("Ocorreu um problema na edição da Consulta1.");
      }
    } else {
      System.out.println("Ocorreu um problema na edição da Consulta.2");
    }
    return false;
  }

  public boolean removeData(int id, boolean needInteraction) {
    if (id == 0) {
      System.out.println("\nRemoção de Consultas");
      showData();
      id = Util.readIntegerFromKeyboard("Selecione a Consulta a ser removida: ", "Consulta inválida. ");
      if (!isValidId(id)) {
        System.out.println("Consulta inválida. ");
        return false;
      }
    }
    ListIterator<Consult> iterator = data.listIterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        if (needInteraction) {
          System.out.println("Consulta removida com sucesso!");
        }
        return true;
      }
    }
    return false;
  }

  private void showPossibleDiets(Map<Integer, List<Food>> possibleDiets) {
    for (Map.Entry<Integer, List<Food>> entry : possibleDiets.entrySet()) {
      System.out.println("Possível dieta " + entry.getKey() + ":");
      for (Food food : entry.getValue()) {
        System.out.println(food);
      }
      System.out.println();
    }
  }

  private Map<Integer, List<Food>> calculatePossibleDiets(float calorieLimit) {
    Map<Integer, List<Food>> possibleDiets = new HashMap<>();
    List<Animal> animalFoods = new ArrayList<>();
    List<Vegetable> vegetableFoods = new ArrayList<>();
    List<Mineral> mineralFoods = new ArrayList<>();
    int validDiets = 0;

    for (Food food : foodScreen.getData()) {
      if (food instanceof Animal) {
        animalFoods.add((Animal) food);
      } else if (food instanceof Vegetable) {
        vegetableFoods.add((Vegetable) food);
      } else if (food instanceof Mineral) {
        mineralFoods.add((Mineral) food);
      }
    }

    for (Animal animalFood : animalFoods) {
      if (animalFood.getCalories() >= calorieLimit) {
        continue;
      }
      for (Vegetable vegetableFood : vegetableFoods) {
        if (animalFood.getCalories() + vegetableFood.getCalories() >= calorieLimit) {
          continue;
        }
        for (Mineral mineralFood : mineralFoods) {
          if (animalFood.getCalories() + vegetableFood.getCalories() + mineralFood.getCalories() > calorieLimit) {
            continue;
          }
          validDiets++;
          List<Food> validFoodCombination = new ArrayList<>();
          validFoodCombination.add(animalFood);
          validFoodCombination.add(vegetableFood);
          validFoodCombination.add(mineralFood);
          possibleDiets.put(validDiets, validFoodCombination);
        }
      }
    }
    return possibleDiets;
  }
}
