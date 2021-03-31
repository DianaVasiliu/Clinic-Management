package patients;

import employees.Doctor;

import java.util.ArrayList;
import utilities.Date;

public class Prescription {

    private ArrayList<Medicine> medicines;
    private Doctor doctor;                  // agregare
    private Date prescriptionDate;          // compozitie

    public Prescription(ArrayList<Medicine> medicines, Doctor doctor, Date prescriptionDate) {
        this.medicines = new ArrayList<>(medicines);
        this.doctor = doctor;
        this.prescriptionDate = new Date(prescriptionDate);
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = new ArrayList<>(medicines);
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = new Date(prescriptionDate);
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Date getPrescriptionDate() {
        return new Date(prescriptionDate);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Prescription\n");
        out.append("date: ").append(prescriptionDate.toString()).append("\n");
        for (Medicine medicine : medicines) {
            out.append("\t").append(medicine.toString()).append("\n");
        }
        return out.toString();
    }
}
