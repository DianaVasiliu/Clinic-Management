package services;

import patients.Patient;

public class PatientService extends CommonService {

    public void pay(Patient patient, double amount) {
        if (patient != null) {
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
