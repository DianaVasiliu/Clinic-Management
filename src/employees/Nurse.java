package employees;

import utilities.Date;
import utilities.LoggingCSV;

public class Nurse extends Employee {

    private static final double salaryPerHour;
    private static final int hoursPerDay;

    static {
        salaryPerHour = 17.9;
        hoursPerDay = 10;
    }

    public Nurse(String firstName, String lastName, Date birthday, char sex, int experience) {
        super(firstName, lastName, birthday, sex, experience);
    }

    public Nurse(Nurse nurse) {
        super(nurse);
    }

    @Override
    public double calculateSalary() {
        LoggingCSV.log("Calculating the salary of " + this.firstName + " " + this.lastName);
        double salary = this.getDaysWorked() * salaryPerHour * hoursPerDay;
        double bonus = salary * (5 + experience) / 100;
        salary += bonus;
        return salary;
    }

    public static int getHoursPerDay() {
        return hoursPerDay;
    }

    public static double getSalaryPerHour() {
        return salaryPerHour;
    }

    @Override
    public String toString() {
        return "Nurse        {" +
                "ID = " + getID() + ", " +
                firstName + " " +
                lastName + "}";
    }
}
