package services;

import clinic.Clinic;
import equipment.Equipment;
import patients.Medicine;
import utilities.LoggingCSV;

import java.util.ArrayList;

public class EmployeesService extends CommonService {

    public void showMedicines() {
        LoggingCSV.log("Showing all medicine");
        Clinic clinic = Clinic.getInstance();
        ArrayList<Medicine> medicines = clinic.getMedicines();
        for (Medicine medicine : medicines) {
            System.out.println(medicine);
        }
    }

    public void showEquipment() {
        LoggingCSV.log("Showing all equipment");
        Clinic clinic = Clinic.getInstance();
        ArrayList<Equipment> equipment = clinic.getEquipment();
        for (Equipment e : equipment) {
            System.out.println(e);
        }
    }

}
