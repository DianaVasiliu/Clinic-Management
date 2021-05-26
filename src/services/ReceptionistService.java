package services;

import clinic.Clinic;
import employees.utils.Specialization;
import patients.Appointment;
import patients.Patient;
import repositories.ReceptionistRepo;
import utilities.LoggingCSV;

import java.util.ArrayList;

public class ReceptionistService extends EmployeesService {

    private static ReceptionistService instance;

    private ReceptionistService() {}

    public static ReceptionistService getInstance() {
        if (instance == null) {
            instance = new ReceptionistService();
        }
        return instance;
    }

    public void addAppointment(Patient patient, Appointment appointment) {
        if (patient != null) {
            LoggingCSV.log("Adding appointment for " + patient.getFirstName() + " " + patient.getLastName());
            patient.getAppointments().add(appointment);
            Specialization spec = appointment.getDoctor().getSpecialization();
            double price = spec.getConsultationPrice();
            changePatientDebt(patient, price);

            ReceptionistRepo.getInstance().insertAppointment(appointment);
        }
    }

    public void cancelAppointment(Patient patient, Appointment appointment) {
        if (patient != null) {
            LoggingCSV.log("Canceling appointment for " + patient.getFirstName() + " " + patient.getLastName());
            if (patient.getAppointments().contains(appointment)) {
                patient.getAppointments().remove(appointment);
                Specialization spec = appointment.getDoctor().getSpecialization();
                double price = spec.getConsultationPrice();
                changePatientDebt(patient, -price);

                ReceptionistRepo.getInstance().deleteAppointment("id = " + appointment.getID());
            }
            else {
                System.err.println("Patient does not have this appointment");
            }
        }
    }

    public void changePatientDebt(Patient patient, double amount) {
        if (patient != null) {
            LoggingCSV.log("Changing debt for " + patient.getFirstName() + " " + patient.getLastName());
            patient.setDebt(patient.getDebt() + amount);
        }
    }

    public void addPatient(Patient patient) {
        if (patient != null) {
            LoggingCSV.log("Adding new patient: " + patient.getFirstName() + " " + patient.getLastName());
            Clinic clinic = Clinic.getInstance();
            boolean ok = true;
            ArrayList<Patient> patients = clinic.getPatients();
            for (Patient p : patients) {
                if (patient.getSSN() != null && p.getSSN().compareTo(patient.getSSN()) == 0) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                patients.add(patient);
                ReceptionistRepo.getInstance().insertPatient(patient);
            }
            else {
                System.out.println("Patient already exists");
            }
        }
    }
}
