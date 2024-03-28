package service;

import model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService {
    String addPatientsToHospital(Long id, List<Patient> patients);
    Patient getPatientById(Long id);
    Map<Integer, Patient> getPatientByAge(int age);
    List<Patient> sortPatientsByAge(String ascOrDesc);
}
