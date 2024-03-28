package dao.serviceImpl;

import dao.GenericDao;
import dao.PatientDao;
import database.Database;
import exception.StackOverflowException;
import model.Hospital;
import model.Patient;

import java.util.*;

public class PatientDaoImpl implements PatientDao, GenericDao<Patient> {
    @Override
    public String add(Long hospitalId, Patient patient) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(x -> x.getId().equals(hospitalId))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больницы с таким id не существует!"));

                hospital.setPatient(patient);
                return "Пациент успешно добавлен!";
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeById(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                boolean hospitalFound = Database.hospitals.stream()
                        .anyMatch(hospital -> hospital.getPatients().removeIf(patient -> Objects.equals(patient.getId(), id)));

                if (hospitalFound) {
                    System.out.println("Пациент успешно удалён!");
                } else {
                    throw new StackOverflowException("Больницы с таким id не существует!");
                }
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Patient patient) {
        try {
            if (!Database.hospitals.isEmpty()) {
                for (Hospital hospital : Database.hospitals) {
                    for (int i = 0; i < hospital.getPatients().size(); i++) {
                        if (Objects.equals(hospital.getPatients().get(i).getId(), id)) {
                            hospital.getPatients().set(i, patient);
                            return "Пациент успешно обновлен!";
                        }
                    }
                }
                throw new StackOverflowException("Больницы с таким id не существует!");
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(h -> Objects.equals(h.getId(), id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больницы с таким id не существует!"));

                hospital.getPatients().addAll(patients);
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Patient getPatientById(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                return Database.hospitals.stream()
                        .flatMap(hospital -> hospital.getPatients().stream())
                        .filter(patient -> Objects.equals(patient.getId(), id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Пациента с таким id не существует!"));
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Integer, Patient> getPatientByAge(int age) {
        Map<Integer, Patient> patientsByAge = new HashMap<>();
        try {
            if (!Database.hospitals.isEmpty()) {
                Database.hospitals.stream()
                        .flatMap(hospital -> hospital.getPatients().stream())
                        .filter(patient -> patient.getAge() == age)
                        .forEach(patient -> patientsByAge.put(patient.getId().intValue(), patient));

                if (patientsByAge.isEmpty()) {
                    throw new StackOverflowException("Пациенты с таким возрастом не существуют!");
                }
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return patientsByAge;
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        List<Patient> sortedPatients = new ArrayList<>();
        try {
            if (!Database.hospitals.isEmpty()) {
                Database.hospitals.stream()
                        .flatMap(hospital -> hospital.getPatients().stream())
                        .sorted(Comparator.comparingInt(Patient::getAge)
                                .thenComparing(Patient::getId))
                        .forEach(sortedPatients::add);

                if (ascOrDesc.equalsIgnoreCase("desc")) {
                    sortedPatients = sortedPatients.reversed();
                }
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return sortedPatients;
    }
}
