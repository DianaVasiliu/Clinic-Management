package services;

import clinic.Clinic;
import equipment.Equipment;
import patients.Medicine;

import java.util.ArrayList;

public class EmployeesService extends CommonService {

    public void showMedicines() {
        Clinic clinic = Clinic.getInstance();
        ArrayList<Medicine> medicines = clinic.getMedicines();
        for (Medicine medicine : medicines) {
            System.out.println(medicine);
        }
    }

    public void showEquipment() {
        Clinic clinic = Clinic.getInstance();
        ArrayList<Equipment> equipment = clinic.getEquipment();
        for (Equipment e : equipment) {
            System.out.println(e);
        }
    }

}
