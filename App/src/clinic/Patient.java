package clinic;

import java.util.List;

public class Patient {
  private int id;
  private String name;
  private String address;
  private List<String> phones;
  private String email;
  private String birthdate;

  public Patient() {
  }

  public Patient(int id, String name, String address, List<String> phones, String email, String birthdate) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phones = phones;
    this.email = email;
    this.birthdate = birthdate;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  private String phonesToString() {
    String phonesString = "";
    for (String phone : phones) {
      phonesString += phone + " | ";
    }
    return phonesString.substring(0, phonesString.length() - 3);
  }

  public String toString() {
    return " Paciente: " + id + " - Nome: " + name + ", Endereço: " + address + ", Telefones: " + phonesToString()
        + ", E-mail: " + email + ", Data de Nascimento: " + birthdate;
  }
}
