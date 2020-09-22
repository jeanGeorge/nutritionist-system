package food;

public class Mineral extends Food {
  public Mineral(int id, String name, float calories, String unitOfMeasurement) {
    super(id, name, calories, unitOfMeasurement);
    type = FoodTypeEnum.MINERAL.getDescription();
  }
}
