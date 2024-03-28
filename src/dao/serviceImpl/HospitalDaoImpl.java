package dao.serviceImpl;

import dao.HospitalDao;
import database.Database;
import exception.StackOverflowException;
import model.Hospital;
import model.Patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HospitalDaoImpl implements HospitalDao {
    @Override
    public String addHospital(Hospital hospital) {
        Database.hospitals.add(hospital);
        return "Больница успешно добавлена!";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                return Database.hospitals.stream()
                        .filter(x -> x.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больница с id-" + id + " не найдена!"));
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAllHospital() {
        try {
            if (Database.hospitals.isEmpty()) {
                throw new StackOverflowException("На данный момент у вас нет ни одной больницы!");
            }
            return Database.hospitals;
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return Database.hospitals;
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(x -> x.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больница с id-" + id + " не найдена!"));

                return hospital.getPatients();
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteHospitalById(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(x -> x.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больница с id-" + id + " не найдена!"));
                Database.hospitals.remove(hospital);
                return "Больница с id-" + id + " успешно удалена!";
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Map<String, Hospital> hospitalsByAddress = Database.hospitals.stream()
                        .filter(x -> x.getAddress().equalsIgnoreCase(address))
                        .collect(Collectors.toMap(Hospital::getHospitalName, Function.identity()));

                if (hospitalsByAddress.isEmpty()) {
                    throw new StackOverflowException("Больница с адресом " + address + " не найдена!");
                }
                return hospitalsByAddress;
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return new HashMap<>();
    }
}
