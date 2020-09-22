package food;

public class Animal extends Food {
  public Animal(int id, String name, float calories, String unitOfMeasurement) {
    super(id, name, calories, unitOfMeasurement);
    type = FoodTypeEnum.ANIMAL.getDescription();
  }
}
