package system.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import system.screen.options.Option;
import system.screen.options.OptionTypeEnum;
import system.utils.Util;

import food.Animal;
import food.Food;
import food.FoodTypeEnum;
import food.Mineral;
import food.Vegetable;

public class FoodScreen extends Screen<Food> {
  public FoodScreen() {
    databasePath = "./App/database/Food.txt";
    if (!loadData()) {
      System.out.println("Ocorreu um problema e não foi possível carregar os Alimentos.");
    }
    this.title = "Controle de Alimentos";
    this.options = Arrays.asList(new Option(OptionTypeEnum.SHOW, "Mostrar Alimentos"),
        new Option(OptionTypeEnum.REGISTER, "Cadastrar Alimento"), new Option(OptionTypeEnum.EDIT, "Editar Alimento"),
        new Option(OptionTypeEnum.DELETE, "Remover Alimento"));
    this.footer = new Option(OptionTypeEnum.BACK, "Voltar para o Menu Principal");
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
          String name = lineScanner.next();
          String caloriesString = lineScanner.next();
          String unitOfMeasurement = lineScanner.next();
          String type = lineScanner.next();
          int id = 0;
          float calories = 0;
          try {
            id = Integer.parseInt(idString);
            calories = Float.parseFloat(caloriesString);
          } catch (NumberFormatException e) {
            e.printStackTrace();
            continue;
          }
          if (type.toUpperCase().equals(FoodTypeEnum.ANIMAL.getDescription())) {
            data.add(new Animal(id, name, calories, unitOfMeasurement));
          } else if (type.toUpperCase().equals(FoodTypeEnum.VEGETABLE.getDescription())) {
            data.add(new Vegetable(id, name, calories, unitOfMeasurement));
          } else if (type.toUpperCase().equals(FoodTypeEnum.MINERAL.getDescription())) {
            data.add(new Mineral(id, name, calories, unitOfMeasurement));
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
    if (data.size() > 0) {
      System.out.println("\nAlimentos Cadastrados:");
      for (Food food : data) {
        System.out.println(food.toString());
      }
    } else {
      System.out.println("\nNenhum Alimento cadastrado.");
    }
  }

  public boolean addData(int id) {
    if (id == 0) {
      id = generateId();
      System.out.println("\nCadastro de Alimentos");
    }
    String type, name, unitOfMeasurement;
    float calories;
    type = Util.readStringFromKeyboardAndValidate(
        new ArrayList<>(Arrays.asList(FoodTypeEnum.ANIMAL.getDescription(), FoodTypeEnum.VEGETABLE.getDescription(),
            FoodTypeEnum.MINERAL.getDescription())),
        "Digite o Tipo do Alimento (" + FoodTypeEnum.ANIMAL.getDescription() + ", "
            + FoodTypeEnum.VEGETABLE.getDescription() + ", ou " + FoodTypeEnum.MINERAL.getDescription() + "): ",
        "Tipo do Alimento inválido. ");
    name = Util.readStringFromKeyboard("Digite o nome do Alimento: ");
    calories = Util.readFloatFromKeyboard("Digite a quantidade de calorias do Alimento: ", "Quantidade inválida. ");
    unitOfMeasurement = Util.readStringFromKeyboard("Digite a unidade de medida do alimento: ");
    if (type.toUpperCase().equals(FoodTypeEnum.ANIMAL.getDescription())) {
      data.add(new Animal(id, name, calories, unitOfMeasurement));
    } else if (type.equals(FoodTypeEnum.VEGETABLE.getDescription())) {
      data.add(new Vegetable(id, name, calories, unitOfMeasurement));
    } else if (type.equals(FoodTypeEnum.MINERAL.getDescription())) {
      data.add(new Mineral(id, name, calories, unitOfMeasurement));
    } else {
      return false;
    }
    return true;
  }

  public boolean editData() {
    int id;
    System.out.println("\nEdição de Alimentos");
    showData();
    id = Util.readIntegerFromKeyboard("Selecione o Alimento a ser editado: ", "Alimento inválido. ");
    if (!isValidId(id)) {
      System.out.println("Alimento inválido. ");
      return false;
    }
    if (removeData(id, false)) {
      if (addData(id)) {
        System.out.println("Alimento editado com sucesso!");
        return true;
      }
    }
    return false;
  }

  public boolean removeData(int id, boolean needInteraction) {
    if (id == 0) {
      System.out.println("\nRemoção de Alimentos");
      showData();
      id = Util.readIntegerFromKeyboard("Selecione o Alimento a ser removido: ", "Alimento inválido. ");
      if (!isValidId(id)) {
        System.out.println("Alimento inválido. ");
        return false;
      }
    }
    ListIterator<Food> iterator = data.listIterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        if (needInteraction) {
          System.out.println("Alimento removido com sucesso!");
        }
        return true;
      }
    }
    return false;
  }

  public Food findById(int id) {
    for (Food food : data) {
      if (food.getId() == id) {
        return food;
      }
    }
    return new Food();
  }

  protected int generateId() {
    int newId = 0;
    for (Food food : data) {
      if (food.getId() >= newId) {
        newId = food.getId() + 1;
      }
    }
    return newId;
  }

  protected boolean isValidId(int id) {
    boolean valid = false;
    for (Food food : data) {
      if (food.getId() == id) {
        valid = true;
      }
    }
    return valid;
  }
}
