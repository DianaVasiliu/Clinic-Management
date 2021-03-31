package employees;

import utilities.Date;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public abstract class Employee {
    protected String firstName;
    protected String lastName;
    protected Date birthday;
    protected char sex;
    protected int age;
    protected double salary;
    protected int experience;
    protected int daysWorked;
    private long ID;
    private ArrayList<String> notifications = new ArrayList<>();
    public static long noOfEmployees;

    public Employee(String firstName, String lastName, Date birthday, char sex, int experience) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthday);
        setSex(sex);
        setAge();
        setExperience(experience);
        this.salary = calculateSalary();
    }

    public Employee() {}

    public Employee(Employee employee) {
        if (employee != null) {
            this.firstName = employee.firstName;
            this.lastName = employee.lastName;
            this.birthday = new Date(employee.birthday);
            this.sex = employee.sex;
            this.age = employee.age;
            this.experience = employee.experience;
            this.salary = employee.salary;
            this.daysWorked = employee.daysWorked;
            this.notifications = new ArrayList<>(employee.notifications);
            this.ID = employee.ID;
        }
    }

    {
        noOfEmployees++;
        this.ID = noOfEmployees;
    }

    public abstract double calculateSalary();

    /* setters & getters */

    public void setFirstName(String firstName) {
        if (firstName != null && firstName.matches("[A-Z][a-z]+")) {
            this.firstName = firstName;
        }
        else {
            System.err.println("Invalid first name");
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null && lastName.matches("[A-Z][a-z]+")) {
            this.lastName = lastName;
        }
        else {
            System.err.println("Invalid last name");
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = new Date(birthday);
    }

    public void setSex(char sex) {
        sex = Character.toUpperCase(sex);
        if (sex == 'F' || sex == 'M') {
            this.sex = sex;
        }
        else {
            System.err.println("Invalid sex");
        }
    }

    public void setSalary(double salary) {
        if (salary > 0) {
            this.salary = salary;
        }
    }

    public void setExperience(int experience) {
        if (experience > this.age - 25) {
            System.err.println("Invalid experience for " + this.firstName);
            return;
        }
        this.experience = experience;
    }

    public void setAge() {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(this.getBirthday().getYear(), this.getBirthday().getMonth(), this.getBirthday().getDay());
        Period p = Period.between(birthday, today);
        this.age = p.getYears();
    }

    public void setDaysWorked(int daysWorked) {
        if (daysWorked >= 0 && daysWorked <= 30) {
            this.daysWorked = daysWorked;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return new Date(birthday);
    }

    public char getSex() {
        return sex;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public long getID() {
        return ID;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public int getExperience() {
        return experience;
    }

    public static long getNoOfEmployees() {
        return noOfEmployees;
    }
}
