package employees;

import repositories.AdministratorRepo;
import utilities.Date;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public abstract class Employee implements Comparable<Employee> {
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

    {
        this.ID = ++noOfEmployees;
    }

    public Employee(String firstName, String lastName, Date birthday, char sex, int experience) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthday);
        setSex(sex);
        setAge();
        setExperience(experience);
        setSalary(calculateSalary());
    }

    @Override
    public int compareTo(Employee o) {
        return Long.compare(this.getID(), o.getID());
    }

    public Employee() {}

    public Employee(Employee employee) {
        if (employee != null) {
            this.firstName = employee.firstName;
            this.lastName = employee.lastName;
            this.birthday = employee.birthday;
            this.sex = employee.sex;
            this.age = employee.age;
            this.experience = employee.experience;
            this.salary = employee.salary;
            this.daysWorked = employee.daysWorked;
            this.notifications = new ArrayList<>(employee.notifications);
            this.ID = employee.ID;
        }
    }

    public abstract double calculateSalary();

    /* setters & getters */

    public void setFirstName(String firstName) {
        if (firstName != null && firstName.matches("[A-Z][a-z]+")) {
            this.firstName = firstName;
            AdministratorRepo.getInstance().updateEmployee(
                    "id = " + this.getID(),
                    "first_name",
                    this.firstName
            );
        }
        else {
            System.err.println("Invalid first name");
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null && lastName.matches("[A-Z][a-z]+")) {
            this.lastName = lastName;
            AdministratorRepo.getInstance().updateEmployee(
                    "id = " + this.getID(),
                    "last_name",
                    this.lastName
            );
        }
        else {
            System.err.println("Invalid last name");
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        this.setAge();
        AdministratorRepo.getInstance().updateEmployee(
                "id = " + this.getID(),
                "birthday",
                this.birthday.getFullDate()
        );
    }

    public void setSex(char sex) {
        sex = Character.toUpperCase(sex);
        if (sex == 'F' || sex == 'M') {
            this.sex = sex;
            AdministratorRepo.getInstance().updateEmployee(
                    "id = " + this.getID(),
                    "sex",
                    String.valueOf(this.sex)
            );
        }
        else {
            System.err.println("Invalid sex");
        }
    }

    public void setSalary(double salary) {
        if (salary > 0) {
            this.salary = salary;
            AdministratorRepo.getInstance().updateEmployee(
                    "id = " + this.getID(),
                    "salary",
                    String.valueOf(this.salary)
            );
        }
    }

    public void setExperience(int experience) {
        if (experience > this.age - 25) {
            System.err.println("Invalid experience for " + this.firstName);
            return;
        }
        this.experience = experience;
        AdministratorRepo.getInstance().updateEmployee(
                "id = " + this.getID(),
                "experience",
                String.valueOf(this.experience)
        );
    }

    public void setAge() {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(this.getBirthday().getYear(), this.getBirthday().getMonth(), this.getBirthday().getDay());
        Period p = Period.between(birthday, today);
        this.age = p.getYears();
        AdministratorRepo.getInstance().updateEmployee(
                "id = " + this.getID(),
                "age",
                String.valueOf(this.age)
        );
    }

    public void setDaysWorked(int daysWorked) {
        if (daysWorked >= 0 && daysWorked <= 30) {
            this.daysWorked = daysWorked;
            AdministratorRepo.getInstance().updateEmployee(
                    "id = " + this.getID(),
                    "days_worked",
                    String.valueOf(this.daysWorked)
            );
        }
    }

    public void setID(long ID) {
        this.ID = ID;
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
