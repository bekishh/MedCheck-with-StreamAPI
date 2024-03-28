package dao.serviceImpl;

import dao.DepartmentDao;
import dao.GenericDao;
import database.Database;
import exception.StackOverflowException;
import model.Department;
import model.Hospital;

import java.util.List;
import java.util.Objects;

public class DepartmentDaoImpl implements DepartmentDao, GenericDao<Department> {
    @Override
    public String add(Long hospitalId, Department department) {
        try {
            if (!Database.hospitals.isEmpty()) {
                Hospital hospital = Database.hospitals.stream()
                        .filter(x -> x.getId().equals(hospitalId))
                        .findFirst()
                        .orElseThrow(() -> new StackOverflowException("Госпиталя с таким id не существует!"));

                for (Department hospitalDepartment : hospital.getDepartments()) {
                    if (hospitalDepartment.getDepartmentName().equalsIgnoreCase(department.getDepartmentName())) {
                        throw new StackOverflowException("Департмент с таким названием уже существует!");
                    }
                }

                hospital.setDepartment(department);
                return "Департмент успешно добавлен!";
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одного госпиталя!");
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
                        .anyMatch(hospital -> hospital.getDepartments().removeIf(department -> Objects.equals(department.getId(), id)));

                if (hospitalFound) {
                    System.out.println("Отделение успешно удалено!");
                } else {
                    throw new StackOverflowException("Отделения с таким id не существует!");
                }
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одного госпиталя!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Department department) {
        try {
            if (!Database.hospitals.isEmpty()) {
                for (Hospital hospital : Database.hospitals) {
                    for (int i = 0; i < hospital.getDepartments().size(); i++) {
                        if (Objects.equals(hospital.getDepartments().get(i).getId(), id)) {
                            hospital.getDepartments().set(i, department);
                            return "Департмент успешно обновлен!";
                        }
                    }
                }
                throw new StackOverflowException("Госпиталя с таким id не существует!");
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одного госпиталя!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        try {
            if (!Database.hospitals.isEmpty()) {
                for (Hospital hospital : Database.hospitals) {
                    if (Objects.equals(hospital.getId(), id)) {
                        return hospital.getDepartments();
                    }
                }
                throw new StackOverflowException("Госпиталя с таким id не существует!");
            } else {
                throw new StackOverflowException("На данный момент у вас нету ни одного госпиталя!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Department findDepartmentByName(String name) {
        try {
            if (!Database.hospitals.isEmpty()) {
                for (Hospital hospital : Database.hospitals) {
                    for (Department department : hospital.getDepartments()) {
                        if (department.getDepartmentName().equalsIgnoreCase(name)) {
                            return department;
                        }
                    }
                }
                throw new StackOverflowException("Госпиталя с таким id не существует!");
            } else {
                throw new StackOverflowException("У вас еще нету ни одного госпиталя!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
