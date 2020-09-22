package system.screen.options;

public enum OptionTypeEnum {
  SHOW("1"), REGISTER("2"), EDIT("3"), DELETE("4"), BACK("V"), EXIT("S"), CONTROL_FOOD("A"), CONTROL_PATIENTS("P"),
  CONTROL_DIETS("D"), CONTROL_CONSULTS("C");

  private String description;

  private OptionTypeEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
