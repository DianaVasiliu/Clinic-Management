package services;

import clinic.Clinic;
import employees.Doctor;
import employees.Employee;
import employees.Nurse;
import employees.Receptionist;
import employees.utils.Specialization;
import equipment.Equipment;
import patients.Appointment;
import patients.Medicine;
import patients.Patient;
import utilities.Errors;
import utilities.Months;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AdministratorService extends EmployeesService {

    public static AdministratorService instance;

    private AdministratorService() {}

    public static AdministratorService getInstance() {
        if (instance == null) {
            instance = new AdministratorService();
        }
        return instance;
    }

    public void addEmployee(Employee employee) {
        Clinic clinic = Clinic.getInstance();
        if (employee instanceof Doctor) {
            clinic.getDoctors().add((Doctor) employee);
        }
        else if (employee instanceof Nurse) {
            clinic.getNurses().add((Nurse) employee);
        }
        else if (employee instanceof Receptionist) {
            clinic.getReceptionists().add((Receptionist) employee);
        }
    }

    public ArrayList<Employee> getAllEmployees() {
        Clinic clinic = Clinic.getInstance();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.addAll(clinic.getDoctors());
        employees.addAll(clinic.getNurses());
        employees.addAll(clinic.getReceptionists());
        return employees;
    }

    public void addMedicine(Medicine medicine) {
        Clinic clinic = Clinic.getInstance();
        clinic.getMedicines().add(medicine);
    }

    public void addEquipment(Equipment equipment) {
        if (equipment != null) {
            Clinic clinic = Clinic.getInstance();
            clinic.getEquipment().add(equipment);
        }
    }

    public void raiseSalary(double percentage) {
        Clinic clinic = Clinic.getInstance();
        ArrayList<Doctor> doctors = clinic.getDoctors();
        ArrayList<Nurse> nurses = clinic.getNurses();
        ArrayList<Receptionist> receptionists = clinic.getReceptionists();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.addAll(doctors);
        employees.addAll(nurses);
        employees.addAll(receptionists);

        if (percentage < 0 || percentage > 100) {
            System.err.println(Errors.WRONG_PERCENTAGE);
            return;
        }
        else if (percentage > 1) {
            percentage = percentage / 100;
        }

        for (Employee employee : employees) {
            double salary = employee.getSalary();
            salary += salary * percentage;
            employee.setSalary(salary);
            employee.getNotifications().add("Raised salary by " + percentage * 100 + "%");
        }
    }

    public void paySalaries() {
        Clinic clinic = Clinic.getInstance();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.addAll(clinic.getDoctors());
        employees.addAll(clinic.getNurses());
        employees.addAll(clinic.getReceptionists());

        for (Employee employee : employees) {
            double salary;

            if (employee.getSalary() == 0) {
                salary = employee.calculateSalary();
            }
            else {
                salary = employee.getSalary();
            }

            salary = Math.round(salary * 100.0) / 100.0;
            employee.setSalary(salary);
            employee.getNotifications()
                    .add("Salary paid for month " + LocalDate.now().getMonth().toString() +
                            "; Value: " + salary);
        }
    }

    public double getTotalSpentOnMonth(int month, int year) {
        if (month >= 1 && month <= 12) {
            double total = 0.0;
            Clinic clinic = Clinic.getInstance();
            ArrayList<Equipment> equipment = clinic.getEquipment();
            for (Equipment e : equipment) {
                int buyMonth = e.getBuyDate().getMonth();
                int buyYear = e.getBuyDate().getYear();
                if (buyMonth == month && buyYear == year) {
                    total += e.getPrice();
                }
            }
            return total;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return 0.0;
        }
    }

    public double getTotalSpentOnMonth(String month, int year) {
        try {
            month = month.toUpperCase();
            int number = Months.getNumber(Months.valueOf(month));
            return getTotalSpentOnMonth(number, year);
        }
        catch (Exception e) {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public int getNumberOfPatientsOnMonth(int month, int year) {
        if (month >= 1 && month <= 12) {
            int number = 0;
            Clinic clinic = Clinic.getInstance();
            ArrayList<Patient> patients = clinic.getPatients();
            for (Patient patient : patients) {
                ArrayList<Appointment> appointments = patient.getAppointments();
                for (Appointment appointment : appointments) {
                    int appMonth = appointment.getDate().getMonth();
                    int appYear = appointment.getDate().getYear();
                    if (appMonth == month && appYear == year) {
                        number++;
                        break;
                    }
                }
            }
            return number;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public int getNumberOfPatientsOnMonth(String month, int year) {
        try {
            month = month.toUpperCase();
            int number = Months.getNumber(Months.valueOf(month));
            return getNumberOfPatientsOnMonth(number, year);
        }
        catch (Exception e) {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public HashMap<String, Integer> getNumberOfPatientsOnMonthOnSpec(int month, int year) {
        if (month >= 1 && month <= 12) {
            HashMap<String, Integer> patientsPerSpec = new HashMap<>();
            Clinic clinic = Clinic.getInstance();
            ArrayList<Patient> patients = clinic.getPatients();

            for (Patient patient : patients) {
                ArrayList<Appointment> appointments = patient.getAppointments();

                for (Appointment a : appointments) {
                    if (a.getDate().getMonth() == month && a.getDate().getYear() == year) {
                        String specialization = a.getDoctor().getSpecialization().toString();

                        if (patientsPerSpec.get(specialization) != null) {
                            int number = patientsPerSpec.get(specialization) + 1;
                            patientsPerSpec.replace(specialization, number);
                        } else {
                            patientsPerSpec.put(specialization, 1);
                        }
                    }
                }
            }
            return patientsPerSpec;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return new HashMap<>();
        }
    }

    public HashMap<String, Integer> getNumberOfPatientsOnMonthOnSpec(String month, int year) {
        try {
            month = month.toUpperCase();
            int number = Months.getNumber(Months.valueOf(month));
            return getNumberOfPatientsOnMonthOnSpec(number, year);
        }
        catch (Exception e) {
            System.err.println(Errors.INVALID_MONTH);
            return new HashMap<>();
        }
    }

    public double getTotalEarningsOnMonth(int month, int year) {
        if (month >= 1 && month <= 12) {
            HashMap<String, Integer> pacientsPerSpec = getNumberOfPatientsOnMonthOnSpec(month, year);
            double total = 0;

            for (String spec : pacientsPerSpec.keySet()) {
                Specialization specialization = Specialization.getSpecialization(spec);
                total += specialization.getConsultationPrice()
                        * pacientsPerSpec.get(spec);
            }

            return total;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public double getTotalEarningsOnMonth(String month, int year) {
        try {
            month = month.toUpperCase();
            int number = Months.getNumber(Months.valueOf(month));
            return getTotalEarningsOnMonth(number, year);
        }
        catch (Exception e) {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public double getProfitOnMonth(int month, int year) {
        if (month >= 1 && month <= 12) {
            double spent = getTotalSpentOnMonth(month, year);
            double earned = getTotalEarningsOnMonth(month, year);
            return earned - spent;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public double getProfitOnMonth(String month, int year) {
        try {
            month = month.toUpperCase();
            int number = Months.getNumber(Months.valueOf(month));
            return getProfitOnMonth(number, year);
        }
        catch (Exception e) {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public double getAverageProfitOnPatientOnMonth(int month, int year) {
        if (month >= 1 && month <= 12) {
            double profit = getProfitOnMonth(month, year);
            int noOfPatients = getNumberOfPatientsOnMonth(month, year);
            return profit / noOfPatients;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public double getAverageProfitOnPatientOnMonth(String month, int year) {
        try {
            month = month.toUpperCase();
            int number = Months.getNumber(Months.valueOf(month));
            return getAverageProfitOnPatientOnMonth(number, year);
        }
        catch (Exception e) {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

}
