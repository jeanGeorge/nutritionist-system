package system.screen.options;

public class Option {
  private OptionTypeEnum abbreviation;
  private String description;

  public Option(OptionTypeEnum abbreviation, String description) {
    this.abbreviation = abbreviation;
    this.description = description;
  }

  public String getAbbreviation() {
    return abbreviation.getDescription();
  }

  public String toString() {
    return abbreviation.getDescription() + " - " + description;
  }
}
