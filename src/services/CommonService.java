package services;

import clinic.Clinic;
import employees.Doctor;
import employees.Nurse;
import employees.Receptionist;
import employees.utils.Specialization;
import patients.Diagnostic;
import patients.Medicine;
import patients.Patient;
import patients.Prescription;
import patients.utils.cmpPrescriptionDate;
import utilities.Errors;
import utilities.LoggingCSV;

import java.util.ArrayList;

public class CommonService {

    public void showDoctors() {
        LoggingCSV.log("Showing all doctors");
        Clinic clinic = Clinic.getInstance();
        ArrayList<Doctor> doctors = clinic.getDoctors();
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    public void showNurses() {
        LoggingCSV.log("Showing all nurses");
        Clinic clinic = Clinic.getInstance();
        ArrayList<Nurse> nurses = clinic.getNurses();
        for (Nurse nurse : nurses) {
            System.out.println(nurse);
        }
    }

    public void showReceptionists() {
        LoggingCSV.log("Showing all receptionists");
        Clinic clinic = Clinic.getInstance();
        ArrayList<Receptionist> receptionists = clinic.getReceptionists();
        for (Receptionist receptionist : receptionists) {
            System.out.println(receptionist);
        }
    }

    public void showDoctorsBySpecialization(Specialization specialization) {
        if (specialization != null) {
            LoggingCSV.log("Showing all doctors by specialization");
            Clinic clinic = Clinic.getInstance();
            ArrayList<Doctor> doctors = clinic.getDoctors();
            StringBuilder out = new StringBuilder(specialization.toString());
            out.append("\n");
            boolean exists = false;
            for (Doctor doctor : doctors) {
                if (doctor.getSpecialization() == specialization) {
                    out.append("\t").append(doctor).append("\n");
                    exists = true;
                }
            }

            if (!exists) {
                out.append("No doctors with this specialization");
            }

            System.out.println(out);
        }
    }

    public void showPrescriptionHistory(Patient patient) {
        if (patient != null) {
            LoggingCSV.log("Showing prescription history for patient " + patient.getFirstName() + " " + patient.getLastName());
            System.out.println("Patient: " +
                    patient.getFirstName() + " " +
                    patient.getLastName() + ", SSN: " +
                    patient.getSSN());
            ArrayList<Prescription> prescriptions = patient.getPrescriptions();
            prescriptions.sort(new cmpPrescriptionDate());
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }
        }
    }

    public void showDiagnosticHistory(Patient patient) {
        if (patient != null) {
            LoggingCSV.log("Showing diagnostic history for " + patient.getFirstName() + " " + patient.getLastName());
            System.out.println("Patient: " +
                        patient.getFirstName() + " " +
                        patient.getLastName());
            ArrayList<Diagnostic> diagnostics = patient.getDiagnostics();
            if (diagnostics.size() == 0) {
                System.out.println("\tNo diagnostic history");
                return;
            }
            for (Diagnostic diagnostic : diagnostics) {
                System.out.println("\t" + diagnostic);
            }
        }
        else {
            System.err.println(Errors.INVALID_PATIENT);
        }
    }

    public double getTotalPrescriptionPrice(Prescription prescription) {
        LoggingCSV.log("Calculating the total price of a prescription");
        double total = 0.0;
        if (prescription != null) {
            ArrayList<Medicine> medicines = prescription.getMedicines();
            for (Medicine medicine : medicines) {
                total += medicine.getPrice();
            }
        }
        return total;
    }
}
