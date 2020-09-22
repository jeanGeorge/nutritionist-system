package clinic;

import java.util.List;

import food.Food;

public class Diet {
  private int id;
  private Patient patient;
  private float calorieLimit;
  private List<Food> foods;

  public Diet() {
  }

  public Diet(int id, Patient patient, float calorieLimit, List<Food> foods) {
    this.id = id;
    this.patient = patient;
    this.calorieLimit = calorieLimit;
    this.foods = foods;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  private String foodsToString() {
    String foodsString = "";
    for (Food food : foods) {
      foodsString += food.getName() + " | ";
    }
    return foodsString.substring(0, foodsString.length() - 3);
  }

  public String toString() {
    return "Dieta " + id + " - Paciente: " + patient.getName() + ", Limite de Calorias: " + calorieLimit
        + ", Alimentos: " + foodsToString();
  }
}
