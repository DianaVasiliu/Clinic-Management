package clinic;

import employees.Doctor;
import employees.Nurse;
import employees.Receptionist;
import equipment.Equipment;
import patients.Medicine;
import patients.Patient;

import java.util.ArrayList;

public class Clinic {

    private final Administrator administrator;
    private String name = "";
    private static Clinic clinic;
    private final ArrayList<Doctor> doctors = new ArrayList<>();
    private final ArrayList<Nurse> nurses = new ArrayList<>();
    private final ArrayList<Receptionist> receptionists = new ArrayList<>();
    private final ArrayList<Medicine> medicines = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Equipment> equipment = new ArrayList<>();

    private Clinic() {
        this.administrator = Administrator.getInstance();
    }

    public static Clinic getInstance() {
        if (clinic == null) {
            clinic = new Clinic();
        }
        return clinic;
    }

    @Override
    public String toString() {
        return "---------" + "-".repeat(name.length()) + "\n"
                + "Clinic '" + name + "'\n" +
                "---------" + "-".repeat(name.length()) + "\n" +
                "\tAdministrator: " + administrator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Administrator getAdministrator() {
        return Administrator.getInstance();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public ArrayList<Receptionist> getReceptionists() {
        return receptionists;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }
}
