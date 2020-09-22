package system.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import clinic.Patient;
import system.screen.options.Option;
import system.screen.options.OptionTypeEnum;
import system.utils.Util;

public class PatientScreen extends Screen<Patient> {
  public PatientScreen() {
    databasePath = "../database/Patient.txt";
    if (!loadData()) {
      System.out.println("Ocorreu um problema e não foi possível carregar os Pacientes.");
    }
    this.title = "Controle de Pacientes";
    this.options = Arrays.asList(new Option(OptionTypeEnum.SHOW, "Mostrar Pacientes"),
        new Option(OptionTypeEnum.REGISTER, "Cadastrar Paciente"), new Option(OptionTypeEnum.EDIT, "Editar Paciente"),
        new Option(OptionTypeEnum.DELETE, "Remover Paciente"));
    this.footer = new Option(OptionTypeEnum.BACK, "Voltar para o Menu Principal");
  }

  public boolean loadData() {
    data = new ArrayList<>();
    try {
      Scanner read = new Scanner(new File(databasePath));
      read.nextLine();
      while (read.hasNextLine()) {
        Scanner lineScanner = new Scanner(read.nextLine());
        lineScanner.useDelimiter(";");
        String idString = lineScanner.next();
        int id = 0;
        try {
          id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
          e.printStackTrace();
          return false;
        }
        String name = lineScanner.next();
        String address = lineScanner.next();
        String phoneString = lineScanner.next();
        List<String> phones = new ArrayList<String>();
        Scanner phoneScanner = new Scanner(phoneString);
        phoneScanner.useDelimiter(",");
        while (phoneScanner.hasNext()) {
          phones.add(phoneScanner.next());
        }
        String email = lineScanner.next();
        String birthdate = lineScanner.next();
        data.add(new Patient(id, name, address, phones, email, birthdate));
        phoneScanner.close();
      }
      read.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean saveData() {
    // WIP
    return true;
  }

  public void showData() {
    if (data.size() > 0) {
      System.out.println("Pacientes cadastrados:");
      for (Patient patient : data) {
        System.out.println(patient.toString());
      }
    } else {
      System.out.println("Nenhum paciente cadastrado.");
    }
  }

  public boolean addData(int id) {
    if (id == 0) {
      id = generateId();
      System.out.println("\nCadastro de Pacientes");
    }
    String name, address, email, birthdate;
    List<String> phones = new ArrayList<>();
    name = Util.readStringFromKeyboard("Digite o nome do Paciente: ");
    address = Util.readStringFromKeyboard("Digite o endereço do Paciente: ");
    phones = Util.readListStringFromKeyboard("Digite os telefones do Paciente, separados por vírgula (,): ", ",");
    email = Util.readStringFromKeyboard("Digite o e-mail do Paciente: ");
    birthdate = Util.readStringFromKeyboard("Digite a data de nascimento do Paciente: ");
    data.add(new Patient(id, name, address, phones, email, birthdate));
    return true;
  }

  public boolean editData() {
    int id;
    System.out.println("\nEdição de Pacientes");
    showData();
    id = Util.readIntegerFromKeyboard("Selecione o Paciente a ser editado: ", "Paciente inválido. ");
    if (!isValidId(id)) {
      System.out.println("Paciente inválido. ");
      return false;
    }
    if (removeData(id, false)) {
      if (addData(id)) {
        System.out.println("Paciente editado com sucesso!");
        return true;
      }

    }
    return false;
  }

  public boolean removeData(int id, boolean needInteraction) {
    if (id == 0) {
      System.out.println("\nRemoção de Pacientes");
      showData();
      id = Util.readIntegerFromKeyboard("Selecione o Paciente a ser removido: ", "Paciente inválido. ");
      if (!isValidId(id)) {
        System.out.println("Paciente inválido. ");
        return false;
      }
    }
    ListIterator<Patient> iterator = data.listIterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        if (needInteraction) {
          System.out.println("Paciente removido com sucesso!");
        }
        return true;
      }
    }
    return false;
  }

  public Patient findById(int id) {
    for (Patient patient : data) {
      if (patient.getId() == id) {
        return patient;
      }
    }
    return new Patient();
  }

  public int generateId() {
    int newId = 0;
    for (Patient patient : data) {
      if (patient.getId() >= newId) {
        newId = patient.getId() + 1;
      }
    }
    return newId;
  }

  protected boolean isValidId(int id) {
    boolean valid = false;
    for (Patient patient : data) {
      if (patient.getId() == id) {
        valid = true;
      }
    }
    return valid;
  }
}
