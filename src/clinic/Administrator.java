package clinic;

import employees.Employee;
import utilities.LoggingCSV;

public class Administrator extends Employee {

    private static Administrator administrator;
    private static final double salaryPerHour;
    protected static final int hoursPerDay;

    static {
        salaryPerHour = 10;
        hoursPerDay = 6;
    }

    private Administrator() {
    }

    @Override
    public double calculateSalary() {
        LoggingCSV.log("Calculating salary for administrator");
        double salary = this.getDaysWorked() * salaryPerHour * hoursPerDay;
        double bonus = salary * (5 + experience) / 100;
        salary += bonus;
        return salary;
    }

    public static Administrator getInstance() {
        if (administrator == null) {
            administrator = new Administrator();
        }
        return administrator;
    }

    public static double getSalaryPerHour() {
        return salaryPerHour;
    }

    public static int getHoursPerDay() {
        return hoursPerDay;
    }

    public String toString() {
        if (this.sex == 'F') {
            return "Mrs. " + firstName + " " + lastName;
        }
        else if (this.sex == 'M') {
            return "Mr. " + firstName + " " + lastName;
        }
        else {
            return firstName + " " + lastName;  
        }
    }

}
