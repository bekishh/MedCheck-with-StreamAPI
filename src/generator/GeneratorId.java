package generator;

public class GeneratorId {
    public static Long departmentId = 0L;
    public static Long doctorId = 0L;
    public static Long hospitalId = 0L;
    public static Long patientId = 0L;
    public static Long personId = 0L;

    public static Long genDepartmentId() {
        return ++departmentId;
    }

    public static Long genDoctorId() {
        return ++doctorId;
    }

    public static Long genHospitalId() {
        return ++hospitalId;
    }

    public static Long genPatientId() {
        return ++patientId;
    }

    public static Long genPersonId() {
        return ++personId;
    }
}

