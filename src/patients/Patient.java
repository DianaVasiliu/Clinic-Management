package patients;

import patients.utils.cmpAppointmentDate;

import java.util.ArrayList;

public class Patient implements Comparable<Patient> {

    private long ID;
    private static long noOfPatients;
    private String firstName;
    private String lastName;
    private String SSN;
    private int age;
    private char sex;
    private int height;
    private double weight;
    private double debt;
    private ArrayList<Appointment> appointments = new ArrayList<>() {
        @Override
        public boolean add(Appointment appointment) {
            super.add(appointment);
            appointments.sort(new cmpAppointmentDate());
            return true;
        }
    };
    private ArrayList<Diagnostic> diagnostics = new ArrayList<>();
    private ArrayList<Prescription> prescriptions = new ArrayList<>();

    public Patient(String firstName, String lastName, String SSN, int age, char sex, int height, double weight) {
        setFirstName(firstName);
        setLastName(lastName);
        setSSN(SSN);
        setAge(age);
        setSex(sex);
        setHeight(height);
        setWeight(weight);
    }

    public Patient(Patient patient) {
        if (patient != null) {
            this.ID = patient.getID();
            this.firstName = patient.getFirstName();
            this.lastName = patient.getLastName();
            this.SSN = patient.getSSN();
            this.age = patient.getAge();
            this.sex = patient.getSex();
            this.height = patient.getHeight();
            this.weight = patient.getWeight();
            this.debt = patient.getDebt();
            this.appointments = new ArrayList<>(patient.getAppointments());
            this.diagnostics = new ArrayList<>(patient.getDiagnostics());
            this.prescriptions = new ArrayList<>(patient.getPrescriptions());
        }
    }

    {
        noOfPatients++;
        this.ID = noOfPatients;
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

    public void setID(long ID) {
        this.ID = ID;
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

    public long getID() {
        return ID;
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
                "}";
    }

    @Override
    public int compareTo(Patient o) {
        return Long.compare(this.getID(), o.getID());
    }
}
