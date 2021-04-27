package services;

import patients.Patient;
import utilities.LoggingCSV;

public class PatientService extends CommonService {

    public void pay(Patient patient, double amount) {
        if (patient != null) {
            LoggingCSV.log("Patient " + patient.getFirstName() + " " + patient.getLastName() + " paying the sum of " + amount);
            if (patient.getDebt() <= 0) {
                System.out.println("There is nothing to pay");
            }
            else {
                double newDebt = patient.getDebt() - amount;
                patient.setDebt(newDebt);
                System.out.println("Payment successful");
            }
        }
    }

}
