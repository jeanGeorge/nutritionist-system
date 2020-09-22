package food;

public class Vegetable extends Food {
  public Vegetable(int id, String name, float calories, String unitOfMeasurement) {
    super(id, name, calories, unitOfMeasurement);
    type = FoodTypeEnum.VEGETABLE.getDescription();
  }
}
