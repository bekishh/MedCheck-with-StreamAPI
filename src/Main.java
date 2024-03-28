import database.Database;
import enums.Gender;
import exception.StackOverflowException;
import generator.GeneratorId;
import model.*;
import service.serviceImpl.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scannerNum = new Scanner(System.in);
        Scanner scannerLn = new Scanner(System.in);

        PersonServiceImpl personService = new PersonServiceImpl();

        HospitalServiceImpl hospitalService = new HospitalServiceImpl();
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl();
        DoctorServiceImpl doctorService = new DoctorServiceImpl();
        PatientServiceImpl patientService = new PatientServiceImpl();


        Database.persons.add(new Person(GeneratorId.genPersonId(), "Admin", "Admin", "a", "a", Gender.MALE));


        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String wish;

        if (time.getHour() > 6 && time.getHour() <= 12) {
            wish = "Доброе утро";
        } else if (time.getHour() > 12 && time.getHour() <= 18) {
            wish = "Добрый день";
        } else if (time.getHour() > 18 && time.getHour() <= 21) {
            wish = "Добрый вечер";
        } else {
            wish = "Спокойной ночи";
        }

        System.out.println(date + "\n" + wish + ". Время -> " + time.getHour() + ":" + time.getMinute());

        while (true) {
            System.out.println("""
                    1) Вход
                    2) Зарегистрироваться
                    3) Забыли пароль
                    """);
            try {
                switch (scannerNum.nextInt()) {
                    case 1:
                        try {
                            int command = 0;
                            System.out.println("Введите электронную почту: ");
                            String authEmail = scannerLn.nextLine();

                            System.out.println("Введите пароль: ");
                            String authPassword = scannerLn.nextLine();

                            boolean isAuth = false;

                            for (Person person : Database.persons) {
                                if (person.getEmail().equalsIgnoreCase(authEmail) && person.getPassword().equalsIgnoreCase(authPassword)) {
                                    isAuth = true;
                                    break;
                                }
                            }

                            if (isAuth) {
                                while (command != 26) {
                                    System.out.println("""
                                            |----------------------------------------------------------------------------------------|
                                            |--------------------------Добро пожаловать в клинику MedCheck---------------------------|
                                            |----------------------------------Выберите операцию-------------------------------------|
                                            |----------------------------------------------------------------------------------------|
                                            | 1 -> Добавить новую больницу                   14 -> Удалить доктора по id             |
                                            | 2 -> Найти больницу по id                      15 -> Найти доктора по id               |
                                            | 3 -> Получить все больницы                     16 -> Назначить доктора в отделение     |
                                            | 4 -> Получить всех пациентов больницы          17 -> Получить всех докторов больницы   |
                                            | 5 -> Удалить больницу по id                    18 -> Получить всех докторов отделения  |
                                            | 6 -> Получить все больницы по адрес            19 -> Добавить пациента в больницу      |
                                            | 7 -> Добавить отделение в больницу             20 -> Обновить пациента по id           |
                                            | 8 -> Обновить отделение по id                  21 -> Удалить пациента по id            |
                                            | 9  -> Удалить отделение по id                  22 -> Добавить множество пациентов      |
                                            | 10 -> Получить все отделенения больницы        23 -> Получить данные пациента по id    |
                                            | 11 -> Найти отделение по имени                 24 -> Получить пациентов по возрасту    |
                                            | 12 -> Добавить доктора в больницу              25 -> Сортировака пациентов по возрасту |
                                            | 13 -> Обновить доктора по id                   26 -> Выйти                             |
                                            |----------------------------------------------------------------------------------------|
                                            """);

                                    try {
                                        switch (command = scannerNum.nextInt()) {
                                            case 1 -> {
                                                System.out.println("Введите название больницы: ");
                                                String hospitalName = scannerLn.nextLine();

                                                System.out.println("Введите адрес больницы: ");
                                                String address = scannerLn.nextLine();

                                                Hospital hospital = new Hospital(GeneratorId.genHospitalId(), hospitalName, address, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                                                System.out.println(hospitalService.addHospital(hospital));
                                            }
                                            case 2 -> {
                                                System.out.println("Введите id больницы: ");
                                                System.out.println(hospitalService.findHospitalById(scannerNum.nextLong()));
                                            }
                                            case 3 -> {
                                                System.out.println(hospitalService.getAllHospital());
                                            }
                                            case 4 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы: ");
                                                    System.out.println(hospitalService.getAllPatientFromHospital(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
                                                }
                                            }
                                            case 5 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы");
                                                    System.out.println(hospitalService.deleteHospitalById(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
                                                }
                                            }
                                            case 6 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите адрес: ");
                                                    System.out.println(hospitalService.getAllHospitalByAddress(scannerLn.nextLine()).values());
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
                                                }
                                            }
                                            case 7 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы: ");
                                                    Long hospitalId = scannerNum.nextLong();

                                                    System.out.println("Введите название отделения: ");
                                                    String departmentName = scannerLn.nextLine();

                                                    Department department = new Department(GeneratorId.genDepartmentId(), departmentName, new ArrayList<>());
                                                    System.out.println(departmentService.add(hospitalId, department));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
                                                }
                                            }
                                            case 8 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id отделения: ");
                                                    Long departmentId = scannerNum.nextLong();

                                                    System.out.println("Введите новое название отделения: ");
                                                    String departmentName = scannerLn.nextLine();

                                                    Department updatedDepartment = new Department(departmentId, departmentName, new ArrayList<>());

                                                    System.out.println(departmentService.updateById(departmentId, updatedDepartment));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 9 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id отделения: ");
                                                    departmentService.removeById(scannerNum.nextLong());
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 10 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы: ");
                                                    System.out.println(departmentService.getAllDepartmentByHospital(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
                                                }
                                            }
                                            case 11 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите название отделения: ");
                                                    System.out.println(departmentService.findDepartmentByName(scannerLn.nextLine()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 12 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы: ");
                                                    Long hospitalId = scannerNum.nextLong();

                                                    System.out.println("Введите имя доктора: ");
                                                    String firstName = scannerLn.nextLine();

                                                    System.out.println("Введите фамилию доктора: ");
                                                    String lastName = scannerLn.nextLine();

                                                    System.out.println("Выберите пол доктора (М/Ж): ");
                                                    String genderLn = scannerLn.nextLine();

                                                    Gender gender;

                                                    if (genderLn.equalsIgnoreCase("М") || genderLn.equalsIgnoreCase("M")) {
                                                        gender = Gender.MALE;
                                                    } else if (genderLn.equalsIgnoreCase("Ж")) {
                                                        gender = Gender.FEMALE;
                                                    } else {
                                                        throw new StackOverflowException("Не правильный пол!");
                                                    }

                                                    System.out.println("Введите стаж работы доктора: ");
                                                    int experienceYear = scannerNum.nextInt();

                                                    Doctor doctor = new Doctor(GeneratorId.genDoctorId(), firstName, lastName, gender, experienceYear);
                                                    System.out.println(doctorService.add(hospitalId, doctor));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
                                                }
                                            }
                                            case 13 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id доктора которого хотите обновить: ");
                                                    Long doctorId = scannerNum.nextLong();

                                                    System.out.println("Введите новое имя доктора: ");
                                                    String firstName = scannerLn.nextLine();

                                                    System.out.println("Введите новую фамилию доктора: ");
                                                    String lastName = scannerLn.nextLine();

                                                    System.out.println("Введите новый пол доктора (М/Ж): ");
                                                    String genderLn = scannerLn.nextLine();

                                                    Gender gender;

                                                    if (genderLn.equalsIgnoreCase("М") || genderLn.equalsIgnoreCase("M")) {
                                                        gender = Gender.MALE;
                                                    } else if (genderLn.equalsIgnoreCase("Ж")) {
                                                        gender = Gender.FEMALE;
                                                    } else {
                                                        throw new StackOverflowException("Не правильный пол!");
                                                    }

                                                    System.out.println("Введите стаж работы доктора: ");
                                                    int experienceYear = scannerNum.nextInt();

                                                    Doctor updatedDoctor = new Doctor(doctorId, firstName, lastName, gender, experienceYear);
                                                    System.out.println(doctorService.updateById(doctorId, updatedDoctor));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и доктора!");
                                                }
                                            }
                                            case 14 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id доктора: ");
                                                    doctorService.removeById(scannerNum.nextLong());
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и доктора!");
                                                }
                                            }
                                            case 15 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id доктора: ");
                                                    System.out.println(doctorService.findDoctorById(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и доктора!");
                                                }
                                            }
                                            case 16 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id отделения: ");
                                                    Long departmentId = scannerNum.nextLong();

                                                    List<Long> doctorsId = new ArrayList<>();

                                                    do {
                                                        System.out.println("Введите id доктора которого хотите назначить в это отделение: ");
                                                        doctorsId.add(scannerNum.nextLong());

                                                        System.out.print("Хотите назначить в это отеление еще одного доктора?(Д/Н): ");
                                                    } while (!scannerLn.nextLine().equalsIgnoreCase("Н"));

                                                    doctorService.assignDoctorToDepartment(departmentId, doctorsId);
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 17 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы: ");
                                                    System.out.println(doctorService.getAllDoctorsByHospitalId(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и доктора!");
                                                }
                                            }
                                            case 18 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id отделения: ");
                                                    System.out.println(doctorService.getAllDoctorsByDepartmentId(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 19 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id больницы: ");
                                                    Long hospitalId = scannerNum.nextLong();

                                                    System.out.println("Введите имя пациента: ");
                                                    String firstName = scannerLn.nextLine();

                                                    System.out.println("Введите фамилию пациента: ");
                                                    String lastName = scannerLn.nextLine();

                                                    System.out.println("Введите возраст пациента: ");
                                                    int age = scannerNum.nextInt();

                                                    System.out.println("Выберите пол пациента (М/Ж): ");
                                                    String genderLn = scannerLn.nextLine();

                                                    Gender gender;

                                                    if (genderLn.equalsIgnoreCase("М") || genderLn.equalsIgnoreCase("M")) {
                                                        gender = Gender.MALE;
                                                    } else if (genderLn.equalsIgnoreCase("Ж")) {
                                                        gender = Gender.FEMALE;
                                                    } else {
                                                        throw new StackOverflowException("Не правильный пол!");
                                                    }

                                                    Patient patient = new Patient(GeneratorId.genPatientId(), firstName, lastName, age, gender);
                                                    System.out.println(patientService.add(hospitalId, patient));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 20 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id пациента которого хотите обновить: ");
                                                    Long patientId = scannerNum.nextLong();

                                                    System.out.println("Введите новое имя пациента: ");
                                                    String firstName = scannerLn.nextLine();

                                                    System.out.println("Введите новую фамилию пациента: ");
                                                    String lastName = scannerLn.nextLine();

                                                    System.out.println("Введите новый возраст пациента: ");
                                                    int age = scannerNum.nextInt();

                                                    System.out.println("Выберите пол пациента (М/Ж): ");
                                                    String genderLn = scannerLn.nextLine();

                                                    Gender gender;

                                                    if (genderLn.equalsIgnoreCase("М") || genderLn.equalsIgnoreCase("M")) {
                                                        gender = Gender.MALE;
                                                    } else if (genderLn.equalsIgnoreCase("Ж")) {
                                                        gender = Gender.FEMALE;
                                                    } else {
                                                        throw new StackOverflowException("Не правильный пол!");
                                                    }

                                                    Patient updatedPatient = new Patient(patientId, firstName, lastName, age, gender);
                                                    System.out.println(patientService.updateById(patientId, updatedPatient));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 21 -> {
                                                System.out.println("Введите id пациента: ");
                                                patientService.removeById(scannerNum.nextLong());
                                            }
                                            case 22 -> {
                                                System.out.println("Введите id больницы: ");
                                                Long hospitalId = scannerNum.nextLong();

                                                List<Patient> patients = new ArrayList<>();

                                                do {
                                                    System.out.println("Введите имя пациента: ");
                                                    String firstName = scannerLn.nextLine();

                                                    System.out.println("Введите фамилию пациента: ");
                                                    String lastName = scannerLn.nextLine();

                                                    System.out.println("Введите возраст пациента: ");
                                                    int age = scannerNum.nextInt();

                                                    System.out.println("Выберите пол пациента (М/Ж): ");
                                                    String genderLn = scannerLn.nextLine();

                                                    Gender gender;

                                                    if (genderLn.equalsIgnoreCase("М") || genderLn.equalsIgnoreCase("M")) {
                                                        gender = Gender.MALE;
                                                    } else if (genderLn.equalsIgnoreCase("Ж")) {
                                                        gender = Gender.FEMALE;
                                                    } else {
                                                        throw new StackOverflowException("Не правильный пол!");
                                                    }

                                                    Patient patient = new Patient(GeneratorId.genPatientId(), firstName, lastName, age, gender);
                                                    patients.add(patient);

                                                    System.out.print("Хотите добавить в эту больницу еще одного пациента?(Д/Н): ");
                                                } while (!scannerLn.nextLine().equalsIgnoreCase("Н"));

                                                System.out.println(patientService.addPatientsToHospital(hospitalId, patients));
                                            }
                                            case 23 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите id пациента: ");
                                                    System.out.println(patientService.getPatientById(scannerNum.nextLong()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 24 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Введите возраст: ");
                                                    System.out.println(patientService.getPatientByAge(scannerNum.nextInt()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 25 -> {
                                                if (!Database.hospitals.isEmpty()) {
                                                    System.out.println("Выберите сортировку (asc/desc): ");
                                                    System.out.println(patientService.sortPatientsByAge(scannerLn.nextLine()));
                                                } else {
                                                    throw new StackOverflowException("На данный момент у вас нету ни одной больницы и отделения!");
                                                }
                                            }
                                            case 26 -> {
                                                System.out.println("Выход успешно выполнен!");
                                            }
                                        }
                                    } catch (StackOverflowException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } else {
                                throw new StackOverflowException("Неверный адрес электронной почты или пароль.\nПопробуйте еще раз");
                            }
                        } catch (StackOverflowException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Введите имя: ");
                        String firstName = scannerLn.nextLine();

                        System.out.println("Введите фамилию: ");
                        String lastName = scannerLn.nextLine();

                        System.out.println("Введите адрес электронной почты: ");
                        String email = scannerLn.nextLine();

                        System.out.println("Введите пароль: ");
                        String password = scannerLn.nextLine();

                        System.out.println("Выберите пол (М/Ж): ");
                        String genderLn = scannerLn.nextLine();

                        Gender gender;

                        if (genderLn.equalsIgnoreCase("М") || genderLn.equalsIgnoreCase("M")) {
                            gender = Gender.MALE;
                        } else if (genderLn.equalsIgnoreCase("Ж")) {
                            gender = Gender.FEMALE;
                        } else {
                            throw new StackOverflowException("Не правильный пол!");
                        }
                        System.out.println(personService.addPerson(new Person(GeneratorId.genPersonId(), firstName, lastName, email, password, gender)));
                        break;
                    case 3:
                        System.out.println("Введите свой адрес электронной почты: ");
                        String emailScan = scannerLn.nextLine();
                        System.out.println("Создайте новый пароль (минимум 8 символов)");
                        String passwordScan = scannerLn.nextLine();
                        try {
                            boolean isPersonExists = false;

                            for (Person person : Database.persons) {
                                if (person.getEmail().equalsIgnoreCase(emailScan) && passwordScan.length() > 7) {
                                    System.out.println("Пароль успешно изменен!");
                                    isPersonExists = true;
                                    person.setPassword(passwordScan);
                                    System.out.println(person);
                                    break;
                                }
                            }

                            if (!isPersonExists) {
                                throw new StackOverflowException("Неверное создание пароля");
                            }
                        } catch (StackOverflowException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Доступны только номера 1-2");
                }
            } catch (StackOverflowException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Вводите только цифры");
                break;
            }
        }
    }
}