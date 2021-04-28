package services;

import patients.Diagnostic;
import patients.Medicine;
import patients.Patient;
import patients.Prescription;
import utilities.LoggingCSV;

import java.util.ArrayList;

public class DoctorService extends EmployeesService {

    private static DoctorService instance;

    private DoctorService() {}

    public static DoctorService getInstance() {
        if (instance == null) {
            instance = new DoctorService();
        }
        return instance;
    }

    public void addPrescription(Patient patient, Prescription prescription) {
        if (patient != null) {
            LoggingCSV.log("Adding prescription to " + patient.getFirstName() + " " + patient.getLastName());
            ArrayList<Prescription> prescriptions = patient.getPrescriptions();
            prescriptions.add(prescription);
        }
    }

    public void addDiagnostic(Patient patient, Diagnostic diagnostic) {
        if (patient != null) {
            LoggingCSV.log("Adding diagnostic to " + patient.getFirstName() + " " + patient.getLastName());
            ArrayList<Diagnostic> diagnostics = patient.getDiagnostics();
            diagnostics.add(diagnostic);
        }
    }

    public void addMedicineOnPrescription(Medicine medicine, Prescription prescription) {
        if (prescription != null) {
            LoggingCSV.log("Adding medicine to prescription");
            ArrayList<Medicine> medicines = prescription.getMedicines();
            medicines.add(medicine);
        }
    }
}
