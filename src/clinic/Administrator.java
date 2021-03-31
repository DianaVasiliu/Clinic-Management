package clinic;

import employees.Employee;

public class Administrator extends Employee {

    private static Administrator administrator;
    private static final double costPerHour;
    protected static final int hoursPerDay;

    static {
        costPerHour = 10;
        hoursPerDay = 6;
    }

    private Administrator() {
    }

    @Override
    public double calculateSalary() {
        double salary = this.getDaysWorked() * costPerHour * hoursPerDay;
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

