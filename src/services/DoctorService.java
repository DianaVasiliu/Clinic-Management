package services;

import patients.Diagnostic;
import patients.Medicine;
import patients.Patient;
import patients.Prescription;

import java.util.ArrayList;

public class DoctorService extends EmployeesService {

    public void addPrescription(Patient patient, Prescription prescription) {
        if (patient != null) {
            ArrayList<Prescription> prescriptions = patient.getPrescriptions();
            prescriptions.add(prescription);
        }
    }

    public void addDiagnostic(Patient patient, Diagnostic diagnostic) {
        if (patient != null) {
            ArrayList<Diagnostic> diagnostics = patient.getDiagnostics();
            diagnostics.add(diagnostic);
        }
    }

    public void addMedicineOnPrescription(Medicine medicine, Prescription prescription) {
        if (prescription != null) {
            ArrayList<Medicine> medicines = prescription.getMedicines();
            medicines.add(medicine);
        }
    }
}
