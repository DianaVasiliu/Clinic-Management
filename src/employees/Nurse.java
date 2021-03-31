package employees;

import utilities.Date;

public class Nurse extends Employee {

    private static final double costPerHour;
    protected static final int hoursPerDay;

    static {
        costPerHour = 17.9;
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
        double salary = this.getDaysWorked() * costPerHour * hoursPerDay;
        double bonus = salary * (5 + experience) / 100;
        salary += bonus;
        return salary;
    }

    @Override
    public String toString() {
        return "Nurse        {" +
                "ID = " + getID() + ", " +
                firstName + " " +
                lastName + "}";
    }
}
