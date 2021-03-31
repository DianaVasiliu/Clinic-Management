package patients;

import patients.utils.cmpAppointmentDate;

import java.util.ArrayList;

public class Patient {

    private String firstName;
    private String lastName;
    private String SSN;
    private int age;
    private char sex;
    private int height;
    private double weight;
    private final ArrayList<Appointment> appointments = new ArrayList<>() {
        @Override
        public boolean add(Appointment appointment) {
            super.add(appointment);
            appointments.sort(new cmpAppointmentDate());
            return true;
        }
    };
    private final ArrayList<Diagnostic> diagnostics = new ArrayList<>();
    private final ArrayList<Prescription> prescriptions = new ArrayList<>();
    private double debt;

    public Patient(String firstName, String lastName, String SSN, int age, char sex, int height, double weight) {
        setFirstName(firstName);
        setLastName(lastName);
        setSSN(SSN);
        setAge(age);
        setSex(sex);
        setHeight(height);
        setWeight(weight);
    }

    /* setters & getters */

    public void setFirstName(String firstName) {
        if (firstName != null) {
            if (firstName.matches("[A-Z][a-z]*")) {
                this.firstName = firstName;
            }
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            if (lastName.matches("[A-Z][A-Za-z]*")) {
                this.lastName = lastName;
            }
        }
    }

    public void setSSN(String SSN) {
        if (SSN != null) {
            String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
            if (SSN.matches(regex)) {
                this.SSN = SSN;
            }
        }
    }

    public void setAge(int age) {
        if (age >= 0 && age <= 110) {
            this.age = age;
        }
    }

    public void setSex(char sex) {
        sex = Character.toUpperCase(sex);
        if (sex == 'M' || sex == 'F') {
            this.sex = sex;
        }
    }

    public void setHeight(int height) {
        if (height > 40) {
            this.height = height;
        }
    }

    public void setWeight(double weight) {
        if (weight > 0.5) {
            this.weight = weight;
        }
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSSN() {
        return SSN;
    }

    public int getAge() {
        return age;
    }

    public char getSex() {
        return sex;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getDebt() {
        return debt;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Diagnostic> getDiagnostics() {
        return diagnostics;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    @Override
    public String toString() {
        return "Patient   {" +
                firstName + " " + lastName +
                ", SSN='" + SSN + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
