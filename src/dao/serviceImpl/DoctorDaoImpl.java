package dao.serviceImpl;

import dao.DoctorDao;
import dao.GenericDao;
import database.Database;
import exception.StackOverflowException;
import model.Department;
import model.Doctor;
import model.Hospital;

import java.util.*;

public class DoctorDaoImpl implements DoctorDao, GenericDao<Doctor> {
    @Override
    public String add(Long hospitalId, Doctor doctor) {
        boolean isDoctorExists;
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(h -> h.getId().equals(hospitalId))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больницы с таким id не существует!"));

                isDoctorExists = hospital.getDoctors().stream()
                        .anyMatch(hospitalDoctor -> hospitalDoctor.getFirstName().equalsIgnoreCase(doctor.getFirstName()) &&
                                hospitalDoctor.getLastName().equalsIgnoreCase(doctor.getLastName()));

                if (!isDoctorExists) {
                    hospital.setDoctor(doctor);
                    return "Доктор успешно добавлен!";
                } else {
                    throw new StackOverflowException("Доктор с таким именем уже существует!");
                }
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
        boolean isDoctorExists;
        try {
            if (!Database.hospitals.isEmpty()) {
                isDoctorExists = Database.hospitals.stream()
                        .anyMatch(hospital -> hospital.getDoctors().stream().anyMatch(doctor -> Objects.equals(doctor.getId(), id)));

                if (isDoctorExists) {
                    Database.hospitals.forEach(hospital -> {
                        hospital.getDoctors().removeIf(doctor -> Objects.equals(doctor.getId(), id));
                        hospital.getDepartments().forEach(department -> department.getDoctors().removeIf(doctor -> Objects.equals(doctor.getId(), id)));
                        System.out.println("Доктор успешно удалён!");
                    });
                } else {
                    throw new StackOverflowException("Доктор с id-" + id + " не найден!");
                }
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        boolean isDoctorExists;
        try {
            if (!Database.hospitals.isEmpty()) {
                isDoctorExists = Database.hospitals.stream()
                        .anyMatch(hospital -> hospital.getDoctors().stream()
                                .anyMatch(d -> Objects.equals(d.getId(), id)));

                if (isDoctorExists) {
                    for (Hospital hospital : Database.hospitals) {
                        for (Doctor d : hospital.getDoctors()) {
                            if (Objects.equals(d.getId(), id)) {
                                int index = hospital.getDoctors().indexOf(d);
                                hospital.getDoctors().set(index, doctor);
                                for (Department department : hospital.getDepartments()) {
                                    department.getDoctors().replaceAll(d1 -> Objects.equals(d1.getId(), id) ? doctor : d1);
                                }
                                return "Доктор успешно обновлен!";
                            }
                        }
                    }
                }
                throw new StackOverflowException("Доктор с id-" + id + " не найден!");
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }


    @Override
    public Doctor findDoctorById(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                for (Hospital hospital : Database.hospitals) {
                    for (Doctor doctor : hospital.getDoctors()) {
                        if (Objects.equals(doctor.getId(), id)) {
                            return doctor;
                        }
                    }
                }
                throw new StackOverflowException("Доктор с id-" + id + " не найден!");
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        List<Integer> anotherDoctors = new ArrayList<>();
        try {
            if (!Database.hospitals.isEmpty()) {
                for (Hospital hospital : Database.hospitals) {
                    for (Department department : hospital.getDepartments()) {
                        if (department.getId().equals(departmentId)) {
                            for (Long doctorId : doctorsId) {
                                Doctor doctor = findDoctorById(doctorId);
                                if (doctor != null) {
                                    department.getDoctors().add(doctor);
                                } else {
                                    anotherDoctors.add(doctorId.intValue());
                                }
                            }
                            if (!anotherDoctors.isEmpty()) {
                                throw new StackOverflowException(anotherDoctors.size() == 1 ? "Доктор с идентификатором " + anotherDoctors.getFirst() + " не найден" : "Доктора с идентификатороми " + anotherDoctors + " не найдены");
                            }
                            return "Докторы успешно назначены в отделение с id: " + departmentId;
                        }
                    }
                }
                throw new StackOverflowException("Отделение с id: " + departmentId + " не найдено");
            } else {
                throw new StackOverflowException("На данный момент у вас нет ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(h -> h.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Больница с id-" + id + " не найдена!"));

                return hospital.getDoctors();
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Department department = Database.hospitals.stream()
                        .flatMap(hospital -> hospital.getDepartments().stream())
                        .filter(d -> d.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Отделение с id-" + id + " не найдено!"));

                return department.getDoctors();
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одной больницы!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
