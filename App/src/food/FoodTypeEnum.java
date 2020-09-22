package food;

public enum FoodTypeEnum {
  ANIMAL("ANIMAL"), VEGETABLE("VEGETAL"), MINERAL("MINERAL");

  private String description;

  private FoodTypeEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
