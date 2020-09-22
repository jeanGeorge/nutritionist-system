package clinic;

public class Consult {
  private int id;
  private Patient patient;
  private Diet diet;
  private String date;
  private String schedule;
  private float weight;
  private float bodyFatPercentage;
  private String physicalSensation;
  private String dietaryRestrictions;

  public Consult() {

  }

  public Consult(int id, Patient patient, Diet diet, String date, String schedule, float weight,
      float bodyFatPercentage, String physicalSensation, String dietaryRestrictions) {
    this.id = id;
    this.patient = patient;
    this.diet = diet;
    this.date = date;
    this.schedule = schedule;
    this.weight = weight;
    this.bodyFatPercentage = bodyFatPercentage;
    this.physicalSensation = physicalSensation;
    this.dietaryRestrictions = dietaryRestrictions;
  }

  public int getId() {
    return id;
  }

  public String toString() {
    return "Consulta " + id + " - Paciente: " + patient.getName() + ", Dieta: {" + diet.toString() + "}, Data: " + date
        + ", Horário: " + schedule + ", Peso: " + weight + ", % de gordura: " + bodyFatPercentage
        + ", Sensação Física: " + physicalSensation + ", Restrições: " + dietaryRestrictions;
  }
}
