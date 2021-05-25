package application;

import clinic.Administrator;
import clinic.Clinic;
import database.DatabaseOperations;
import employees.Doctor;
import employees.Employee;
import employees.Nurse;
import employees.Receptionist;
import employees.utils.cmpDoctorsByNameSpec;
import employees.utils.cmpDoctorsBySpecName;
import employees.utils.cmpName;
import equipment.Equipment;
import patients.*;
import patients.utils.cmpMedicineNameSubst;
import patients.utils.cmpMedicineSubstName;
import repositories.AdministratorRepo;
import repositories.ReceptionistRepo;
import services.*;
import utilities.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static employees.utils.Specialization.*;

public class Main {

    public static void main(String[] args) {

        final String GREEN_BACKGROUND = "\u001B[42m";
        final String BLACK = "\u001B[30m";
        final String BLUE = "\u001B[34m";
        final String RESET = "\u001B[0m";

        // Read data from .csv
        readData();

        // database creation
        DatabaseOperations.initializeDatabase();

        // adding the patients from csv to database
        ReceptionistRepo receptionistRepo = ReceptionistRepo.getInstance();
        for (Patient patient : Clinic.getInstance().getPatients()) {
            receptionistRepo.insertPatient(patient);
        }

        AdministratorRepo administratorRepo = AdministratorRepo.getInstance();
        for (Employee employee : Clinic.getInstance().getEmployees()) {
            administratorRepo.insertEmployee(employee);
        }

        for (Medicine medicine : Clinic.getInstance().getMedicines()) {
            administratorRepo.insertMedicine(medicine);
        }

        for (Equipment equipment : Clinic.getInstance().getEquipment()) {
            administratorRepo.insertEquipment(equipment);
        }

        System.out.println(GREEN_BACKGROUND + BLACK + "========== CLINIC INFORMATION ========================" + RESET);

        // Clinic methods
        Clinic clinic = Clinic.getInstance();
        clinic.setName("My First Clinic");

        // Administrator setter methods
        Administrator administrator = Administrator.getInstance();
        administrator.setFirstName("Hellen");
        administrator.setLastName("Johnson");
        administrator.setBirthday(new Date(29, 12, 1980));
        administrator.setSex('F');
        System.out.println(administrator);
        System.out.println(clinic);

        // Administrator & Clinic getter methods
        System.out.println("\tAdministrator b-day: " + administrator.getBirthday());
        System.out.println("ADMIN: " + clinic.getAdministrator());
        System.out.println("Did someone ask what's the clinic's name? It's: " + clinic.getName());
        System.out.println();

        administratorRepo.insertEmployee(administrator);

        System.out.println(GREEN_BACKGROUND + BLACK + "========== MAKING CHANGES TO CLINIC INFO ========================" + RESET);

        // Administrator & Clinic setter methods
        System.out.println("Let's change the admin...");
        administrator.setFirstName("Diana");
        administrator.setLastName("Gunter");
        administrator.setSex('F');
        administrator.setBirthday(new Date(6, 9, 2001));
        clinic.setName("Gunter's Clinic");
        System.out.println("Admin changed. The new admin is:\t" + administrator);
        System.out.println(clinic);
        System.out.println();

        // Administrator getter methods
        System.out.println("What's the birth year of the admin?? " + administrator.getBirthday().getYear());
        System.out.println("What about the month? " + administrator.getBirthday().getMonth());
        System.out.println("And the day? " + administrator.getBirthday().getDay());

        System.out.println();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== EMPLOYEES INFORMATION ========================" + RESET);

        System.out.println("The employee with ID = 1:");
        Employee emp = administratorRepo.selectEmployeeById(1);
        System.out.println(emp);
        System.out.println();

        // Doctor Specialization methods
        Doctor doctor = Clinic.getInstance().getDoctors().get(4);
        Doctor doctor1 = Clinic.getInstance().getDoctors().get(5);

        System.out.println("Doctor " + doctor.getFirstName() + " " + doctor.getLastName() +
                " is specialized in " + doctor.getSpecialization());
        doctor.setSpecialization(GYNECOLOGY);
        System.out.println((doctor.getSex() == 'M' ? "He" : "She") +
                " changed specialization... The new one is " + doctor.getSpecialization());

        System.out.println();

        // AdministratorService methods
        AdministratorService administratorService = AdministratorService.getInstance();

        System.out.println("How many employees does the clinic have? ... " + Employee.getNoOfEmployees());

        System.out.println("Creating an array with all employees\n");

        System.out.println("Showing all doctors");
        administratorService.showDoctors();

        System.out.println("Showing all nurses");
        administratorService.showNurses();

        System.out.println("Showing all receptionists");
        administratorService.showReceptionists();

        System.out.println();
        System.out.println("Who's the most experienced employee and who is the youngest?");
        int maxiExp = 0;
        int miniAge = 100;
        Employee experienced = doctor;
        Employee youngest = doctor;
        ArrayList<Employee> employees = administratorService.getAllEmployees();
        for (Employee employee : employees) {
            if (employee.getExperience() > maxiExp) {
                maxiExp = employee.getExperience();
                experienced = employee;
            }
            if (employee.getAge() < miniAge) {
                miniAge = employee.getAge();
                youngest = employee;
            }
        }

        System.out.println("Most experienced: " + experienced.getFirstName() + " " + experienced.getLastName() + ", EXP: " + maxiExp);
        System.out.println("Youngest: " + youngest.getFirstName() + " " + youngest.getLastName() + ", AGE: " + miniAge);

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== SORTING EMPLOYEES ========================" + RESET);

        // Sorting doctors
        System.out.println("Before sorting:");
        administratorService.showDoctors();

        System.out.println("Sorting doctors alphabetically...");
        ArrayList<Doctor> doctors = clinic.getDoctors();
        doctors.sort(new cmpDoctorsByNameSpec());

        System.out.println("After sorting:");
        administratorService.showDoctors();

        System.out.println("Sorting doctors by specialization:");
        doctors.sort(new cmpDoctorsBySpecName());
        administratorService.showDoctors();

        System.out.println();

        // Sorting nurses
        System.out.println("Before sorting:");
        administratorService.showNurses();

        System.out.println("Sorting nurses...");
        ArrayList<Nurse> nurses = clinic.getNurses();
        nurses.sort(new cmpName());

        System.out.println("After sorting:");
        administratorService.showNurses();

        System.out.println();

        // Sorting receptionists
        System.out.println("Before sorting:");
        administratorService.showReceptionists();

        System.out.println("Sorting receptionists...");
        ArrayList<Receptionist> receptionists = clinic.getReceptionists();
        receptionists.sort(new cmpName());

        System.out.println("After sorting:");
        administratorService.showReceptionists();

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== SORTING MEDICINE ========================" + RESET);

        System.out.println("Before sorting medicine: ");
        administratorService.showMedicines();

        System.out.println("Sorting medicine...");
        ArrayList<Medicine> medicines = clinic.getMedicines();
        medicines.sort(new cmpMedicineSubstName());

        System.out.println("After sorting medicine: ");
        administratorService.showMedicines();

        System.out.println();

        // Medicine setter methods
        administratorService.showMedicines();

        System.out.println();

        System.out.println("Now sorting differently...");
        medicines.sort(new cmpMedicineNameSubst());

        System.out.println("After sorting:");
        administratorService.showMedicines();

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== EQUIPMENT INFORMATION ========================" + RESET);

        administratorService.showEquipment();

        System.out.println("\n");

        // Appointments
        Appointment appointment = new Appointment(new Date(9, 10, 2020), "12:00", doctor);
        Appointment appointment1 = new Appointment(new Date(7, 8, 2020), "16:00", doctor1);

        System.out.println(GREEN_BACKGROUND + BLACK + "========== RECEPTIONIST STUFF ========================" + RESET);

        // ReceptionistService methods
        ReceptionistService receptionistService = ReceptionistService.getInstance();

        Patient patient = Clinic.getInstance().getPatients().get(5);

        receptionistService.addAppointment(patient, appointment);
        receptionistService.addAppointment(patient, appointment1);

//        receptionistService.cancelAppointment(patient, appointment);

        receptionistService.showDoctorsBySpecialization(ANESTHESIOLOGY);
        receptionistService.showDoctorsBySpecialization(PEDIATRICS);
        receptionistService.showDoctorsBySpecialization(CARDIOLOGY);
        System.out.println();
        receptionistService.showEquipment();

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== DOCTOR STUFF ========================" + RESET);

        // DoctorService methods
        DoctorService doctorService = DoctorService.getInstance();

        // Prescription
        Prescription prescription = new Prescription(medicines, doctor, new Date(9, 10, 2020));
        Prescription prescription1 = new Prescription(medicines, doctor1, new Date(3, 1, 2020));
        System.out.println(prescription);

        doctorService.addPrescription(patient, prescription);
        System.out.println("TOTAL prescription: " + doctorService.getTotalPrescriptionPrice(prescription) + "\n\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== COMMON STUFF ========================" + RESET);

        // CommonService methods
        CommonService commonService = new CommonService();
        commonService.showPrescriptionHistory(patient);

        doctorService.addPrescription(patient, prescription1);
        System.out.println(BLUE + "========= PATIENT HISTORY =================================================================================" + RESET);
        commonService.showPrescriptionHistory(patient);
        System.out.println(BLUE + "========= END PATIENT HISTORY =============================================================================" + RESET);

        System.out.println();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== ADMINISTRATOR COSTS ========================" + RESET);

        for (int i = 1; i <= 12; i++) {
            System.out.println(Months.getMonth(i));
            System.out.print(administratorService.getTotalSpentOnMonth(Months.getMonth(i), 2020));
            System.out.print(" / ");
            System.out.println(administratorService.getTotalSpentOnMonth(i, 2020));
            System.out.println();
        }

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== CLINIC PATIENTS ========================" + RESET);
        System.out.println(BLUE + "=========== ALL =================" + RESET);
        for (Patient p : Clinic.getInstance().getPatients()) {
            System.out.println(p);
        }

        System.out.println();

        System.out.println(BLUE + "=========== ON MONTH ON EACH SPECIALIZATION =================" + RESET);
        for (int i = 1; i <= 12; i++) {
            System.out.println(Months.getMonth(i));
            System.out.print(administratorService.getNumberOfPatientsOnMonthOnSpec(Months.getMonth(i), 2020));
            System.out.print(" / ");
            System.out.println(administratorService.getNumberOfPatientsOnMonthOnSpec(i, 2020));
            System.out.println();
        }

        System.out.println(BLUE + "=========== ON MONTH OVERALL =================" + RESET);
        for (int i = 1; i <= 12; i++) {
            System.out.println(Months.getMonth(i));
            System.out.print(administratorService.getNumberOfPatientsOnMonth(Months.getMonth(i), 2020));
            System.out.print(" / ");
            System.out.println(administratorService.getNumberOfPatientsOnMonth(i, 2020));
        }

        System.out.println(BLUE + "=========== EARNINGS IN OCTOBER =================" + RESET);
        System.out.print(administratorService.getTotalEarningsOnMonth(10, 2020) + " / ");
        System.out.println(administratorService.getTotalEarningsOnMonth("october", 2020));

        System.out.println();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== MANAGING EMPLOYEE SALARY ========================" + RESET);

        for (Employee e : doctors)
            e.setDaysWorked(30);

        for (Employee e : nurses)
            e.setDaysWorked(30);

        for (Employee e : receptionists)
            e.setDaysWorked(30);

        administratorService.paySalaries();
        administratorService.raiseSalary(10);
        administratorService.paySalaries();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== NOTIFICATIONS ========================" + RESET);

        System.out.println("DOCTORS:");
        for (Employee e : doctors)
            System.out.println(e.getNotifications());

        System.out.println();

        System.out.println("NURSES:");
        for (Employee e : nurses)
            System.out.println(e.getNotifications());

        System.out.println();

        System.out.println("RECEPTIONISTS:");
        for (Employee e : receptionists)
            System.out.println(e.getNotifications());

        System.out.println();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== DIAGNOSTIC ========================" + RESET);

        // PatientService methods
        PatientService patientService = PatientService.getInstance();
        patientService.showDoctorsBySpecialization(ENDOCRINOLOGY);

        Medicine medicine = Clinic.getInstance().getMedicines().get(0);
        Medicine medicine2 = Clinic.getInstance().getMedicines().get(1);
        Medicine medicine3 = Clinic.getInstance().getMedicines().get(2);

        ArrayList<Medicine> treatment1 = new ArrayList<>();
        treatment1.add(medicine2);
        treatment1.add(medicine);
        Diagnostic diagnostic = new Diagnostic("COVID-19", treatment1,
                new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        doctorService.addDiagnostic(patient, diagnostic);
        diagnostic.setDescription("Bad form of SARS-COV-2");
        patientService.showDiagnosticHistory(patient);

        Prescription prescription2 = new Prescription(diagnostic.getTreatment(), doctor, diagnostic.getDate());
        doctorService.addMedicineOnPrescription(medicine3, prescription2);
        System.out.println(prescription2);

        System.out.println();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== PAYING DEBT ========================" + RESET);

        receptionistService.addAppointment(patient, new Appointment(new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()), "12:00", doctor));

        System.out.println(patient.getDebt());
        patientService.pay(patient, patient.getDebt());
        System.out.println(patient.getDebt());

        System.out.println(GREEN_BACKGROUND + BLACK + "========== CALCULATING PROFIT ========================" + RESET);
        System.out.println("Overall profit in October 2020");
        System.out.println(administratorService.getProfitOnMonth("OCTOBER", 2020));
        System.out.println("Average profit on patient in October 2020");
        System.out.println(administratorService.getAverageProfitOnPatientOnMonth("OCTOBER", 2020));

        Patient patient1 = receptionistRepo.selectPatientById(3);
        System.out.println(patient1);

        var patients = receptionistRepo.selectPatientsByCriteria("last_name", "Blue");
        System.out.println(patients);

        patients = receptionistRepo.selectPatientsByCriteria("sex", "F");
        System.out.println(patients);

        System.out.println();

        var selectedEmployees = receptionistRepo.selectEmployeesByCriteria("age", "> 30");
        System.out.println("Number of employees with age > 30 : " + selectedEmployees.size());

        selectedEmployees = receptionistRepo.selectEmployeesByCriteria("experience", "<= 3");
        System.out.println("Number of employees with experience <= 3 : " + selectedEmployees.size());

        for (Appointment appointment2 : patient.getAppointments()) {
            receptionistRepo.insertAppointment(appointment2);
        }

        receptionistRepo.deleteAppointment("date_time LIKE '2021%'");
        receptionistRepo.deleteAppointment("doctor_id = 7");

        administratorRepo.deleteEmployeeById(7);
        receptionistRepo.deletePatientById(3);

        administratorRepo.deleteMedicine("producer LIKE 'Ba%'");

        saveData();
    }

    private static void readData() {
        EmployeeCSVManipulation.getInstance().readData();
        PatientCSVManipulation.getInstance().readData();
        MedicineCSVManipulation.getInstance().readData();
        EquipmentCSVManipulation.getInstance().readData();
    }

    private static void saveData() {
        EmployeeCSVManipulation.getInstance().writeData();
        PatientCSVManipulation.getInstance().writeData();
        MedicineCSVManipulation.getInstance().writeData();
        EquipmentCSVManipulation.getInstance().writeData();
    }
}
