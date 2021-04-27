package employees;

import utilities.Date;
import utilities.LoggingCSV;

public class Receptionist extends Employee {

    private static final double costPerHour;
    protected static final int hoursPerDay;

    static {
        costPerHour = 12.5;
        hoursPerDay = 8;
    }

    public Receptionist(String firstName, String lastName, Date birthday, char sex, int experience) {
        super(firstName, lastName, birthday, sex, experience);
    }

    public Receptionist(Receptionist receptionist) {
        super(receptionist);
    }

    @Override
    public void setExperience(int experience) {
        if (experience > this.age - 18) {
            System.err.println("Invalid experience for " + this.firstName);
            return;
        }
        this.experience = experience;
    }

    @Override
    public double calculateSalary() {
        LoggingCSV.log("Calculating the salary of " + this.firstName + " " + this.lastName);
        double salary = this.getDaysWorked() * costPerHour * hoursPerDay;
        double bonus = salary * (5 + experience) / 100;
        salary += bonus;
        return salary;
    }

    @Override
    public String toString() {
        return "Receptionist {" +
                "ID = " + getID() + ", " +
                firstName + " " +
                lastName + "}";
    }

}
