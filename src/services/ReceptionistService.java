package services;

import clinic.Clinic;
import employees.utils.Specialization;
import patients.Appointment;
import patients.Patient;

public class ReceptionistService extends EmployeesService {

    public void addAppointment(Patient patient, Appointment appointment) {
        if (patient != null) {
            patient.getAppointments().add(appointment);
            Specialization spec = appointment.getDoctor().getSpecialization();
            double price = spec.getConsultationPrice();
            changePatientDebt(patient, price);
        }
    }

    public void cancelAppointment(Patient patient, Appointment appointment) {
        if (patient != null) {
            if (patient.getAppointments().contains(appointment)) {
                patient.getAppointments().remove(appointment);
                Specialization spec = appointment.getDoctor().getSpecialization();
                double price = spec.getConsultationPrice();
                changePatientDebt(patient, -price);
            }
            else {
                System.err.println("Patient does not have this appointment");
            }
        }
    }

    public void changePatientDebt(Patient patient, double amount) {
        if (patient != null) {
            patient.setDebt(patient.getDebt() + amount);
        }
    }

    public void addPatient(Patient patient) {
        if (patient != null) {
            Clinic clinic = Clinic.getInstance();
            clinic.getPatients().add(patient);
        }
    }

}
