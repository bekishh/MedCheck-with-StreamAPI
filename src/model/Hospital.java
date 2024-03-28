package model;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private Long id;
    private String hospitalName;
    private String address;
    private List<Department> departments;
    private List<Doctor> doctors;
    private List<Patient> patients;

    public Hospital() {
    }

    public Hospital(Long id, String hospitalName, String address, List<Department> departments, List<Patient> patients, List<Doctor> doctors) {
        this.id = id;
        this.hospitalName = hospitalName;
        this.address = address;
        this.departments = departments;
        this.patients = patients;
        this.doctors = doctors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void setDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void setPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    @Override
    public String toString() {
        return "\nHospital{" +
                "id=" + id +
                ", hospitalName='" + hospitalName + '\'' +
                ", address='" + address + '\'' +
                ", departments=" + departments +
                ", doctors=" + doctors +
                ", patients=" + patients +
                '}';
    }
}
