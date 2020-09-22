package food;

public class Food {
  private int id;
  private String name;
  private float calories;
  private String unitOfMeasurement;
  protected String type;

  public Food() {
  }

  public Food(int id, String name, float calories, String unitOfMeasurement) {
    this.id = id;
    this.name = name;
    this.calories = calories;
    this.unitOfMeasurement = unitOfMeasurement;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return "Alimento: " + id + " - Nome: " + name + ", Tipo: " + type + ", Unidade de Medida: " + unitOfMeasurement
        + ", Calorias: " + calories + ".";
  }

  public float getCalories() {
    return calories;
  }
}
