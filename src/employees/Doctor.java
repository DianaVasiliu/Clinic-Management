package employees;

import services.AdministratorService;
import utilities.Date;
import employees.utils.Specialization;
import utilities.LoggingCSV;

import java.time.LocalDate;
import java.util.HashMap;

public class Doctor extends Employee {

    private Specialization specialization;
    private static final double costPerHour;
    protected static final int hoursPerDay;

    static {
        costPerHour = 21.7;
        hoursPerDay = 12;
    }

    public Doctor(String firstName, String lastName, Date birthday, char sex, Specialization specialization, int experience) {
        super(firstName, lastName, birthday, sex, experience);
        this.specialization = specialization;
    }

    public Doctor(Doctor doctor) {
        super(doctor);
        if (doctor != null) {
            this.specialization = doctor.specialization;
        }
    }

    @Override
    public double calculateSalary() {
        LoggingCSV.log("Calculating " + this.firstName + " " + this.lastName + "'s salary");
        Specialization specialization = this.getSpecialization();
        double consultationPrice = specialization.getConsultationPrice();
        AdministratorService administratorService = AdministratorService.getInstance();
        HashMap<String, Integer> map = administratorService
                .getNumberOfPatientsOnMonthOnSpec(LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        int numberOfPatients = 0;

        if (map != null) {
            if (map.containsKey(specialization.toString())) {
                numberOfPatients = map.get(specialization.toString());
            }
        }
        double salary = this.getDaysWorked() * costPerHour * hoursPerDay;
        double bonus = numberOfPatients * consultationPrice + salary * (5 + experience) / 100;
        salary += bonus;

        return salary;
    }

    /* setters & getters */

    public Specialization getSpecialization() {
        if (specialization != null) {
            return specialization;
        }
        return Specialization.UNKNOWN;
    }

    public void setSpecialization(Specialization specialization) {
        if (specialization != null) {
            this.specialization = specialization;
        }
        else {
            this.specialization = Specialization.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return "Doctor       {" +
                "ID = " + getID() + ", " +
                firstName + " " +
                lastName +
                ", specialization = '" + specialization + "'" +
                "}";
    }
}
