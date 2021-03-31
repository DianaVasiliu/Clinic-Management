package application;

import clinic.Administrator;
import clinic.Clinic;
import employees.Doctor;
import employees.Employee;
import employees.Nurse;
import employees.Receptionist;
import employees.utils.cmpDoctorsByNameSpec;
import employees.utils.cmpDoctorsBySpecName;
import employees.utils.cmpName;
import equipment.Electornic;
import equipment.Equipment;
import equipment.NonConsumable;
import patients.*;
import patients.utils.cmpMedicineNameSubst;
import patients.utils.cmpMedicineSubstName;
import services.*;
import utilities.Date;
import utilities.Months;

import java.time.LocalDate;
import java.util.ArrayList;

import static employees.utils.Specialization.*;

public class Main {

    public static void main(String[] args) {

        final String GREEN_BACKGROUND = "\u001B[42m";
        final String BLACK = "\u001B[30m";
        final String BLUE = "\u001B[34m";
        final String RESET = "\u001B[0m";

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


        System.out.println(GREEN_BACKGROUND + BLACK + "========== EMPLOYEES INFORMATION ========================" + RESET);

        // Doctors, Nurses, Receptionists
        Doctor doctor = new Doctor("John", "Smith", new Date(12, 9, 1987),'M', CARDIOLOGY, 5);
        Doctor doctor1 = new Doctor("Emily", "Blue", new Date(3, 12, 1985), 'F', PEDIATRICS, 10);
        Doctor doctor2 = new Doctor("Mark", "Blue", new Date(9, 1, 1990), 'm', PEDIATRICS, 5);
        Doctor doctor3 = new Doctor("Jennifer", "Lawrence", new Date(4, 10, 1988), 'F', ENDOCRINOLOGY, 3);

        Nurse nurse = new Nurse("Emily", "Jordan", new Date(16, 4, 1993), 'F', 2);
        Nurse nurse1 = new Nurse("Monica", "Geller", new Date(6, 8, 1988), 'F', 3);
        Nurse nurse2 = new Nurse("Amanda", "Grey", new Date(9, 10, 1973), 'F', 9);
        Nurse nurse3 = new Nurse("Lily", "London", new Date(19, 4, 1992), 'f', 1);

        Receptionist receptionist = new Receptionist("Amanda", "Grey", new Date(31, 5, 1986), 'F', 9);
        Receptionist receptionist1 = new Receptionist("Vanessa", "Black", new Date(3, 4, 1972), 'F', 2);
        Receptionist receptionist2 = new Receptionist("Anna", "Sky", new Date(6, 2, 1991), 'f', 4);
        Receptionist receptionist3 = new Receptionist("Mimi", "Hopkins", new Date(7, 7, 1996), 'F', 2);

        System.out.println(doctor);
        System.out.println(nurse);
        System.out.println(receptionist);

        System.out.println();

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

        // Doctor Specialization methods
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
        administratorService.addEmployee(doctor);
        administratorService.addEmployee(doctor1);
        administratorService.addEmployee(doctor2);
        administratorService.addEmployee(doctor3);
        administratorService.showDoctors();

        System.out.println("Showing all nurses");
        administratorService.addEmployee(nurse);
        administratorService.addEmployee(nurse1);
        administratorService.addEmployee(nurse2);
        administratorService.addEmployee(nurse3);
        administratorService.showNurses();

        System.out.println("Showing all receptionists");
        administratorService.addEmployee(receptionist);
        administratorService.addEmployee(receptionist1);
        administratorService.addEmployee(receptionist2);
        administratorService.addEmployee(receptionist3);
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

        // Medicine methods
        Medicine medicine = new Medicine("Nurofen", "Reckitt Benckiser", 10.0, "Ibuprofen");
        Medicine medicine1 = new Medicine("ACC", "Salut Pharma GmbH", 3.50, "Cefixima");
        Medicine medicine2 = new Medicine("Aspirina", "Bayer", 1.99, "Acetilsalicilic acid");
        Medicine medicine3 = new Medicine("Aspirina", "Bayer", 0.99, "Acetilsalicilic acid");
        administratorService.addMedicine(medicine);
        administratorService.addMedicine(medicine1);
        administratorService.addMedicine(medicine2);
        administratorService.addMedicine(medicine3);

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
        System.out.println("Making some changes...");

        medicine1.setActiveSubstance("Cefixime");
        medicine1.setProducer("Salutas Pharma GmbH");
        medicine2.setName("Aspirin");
        medicine3.setName("Aspirin");
        medicine3.setPrice(1.04);
        administratorService.showMedicines();

        System.out.println();

        System.out.println("Now sorting differently...");
        medicines.sort(new cmpMedicineNameSubst());

        System.out.println("After sorting:");
        administratorService.showMedicines();

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== EQUIPMENT INFORMATION ========================" + RESET);

        // Equipment
        Equipment equipment = new NonConsumable("Stethoscope", 40.99, new Date(3, 3, 2021), 800);
        Equipment electornic = new Electornic("EKG", 200.0, 20, new Date(4, 10, 2020));

        administratorService.addEquipment(equipment);
        administratorService.addEquipment(electornic);
        administratorService.showEquipment();

        System.out.println("\n");

        // Patients
        Patient patient = new Patient("Elizabeth", "Harmon", "784-91-241", 20, 'f', 172, 60.0);
        Patient patient1 = new Patient("Anne", "Matches", "891-13-920", 43, 'f', 168, 58);

        // Appointments
        Appointment appointment = new Appointment(new Date(9, 10, 2020), "12:00", doctor);
        Appointment appointment1 = new Appointment(new Date(7, 8, 2020), "16:00", doctor1);

        System.out.println(GREEN_BACKGROUND + BLACK + "========== RECEPTIONIST STUFF ========================" + RESET);

        // ReceptionistService methods
        ReceptionistService receptionistService = new ReceptionistService();

        receptionistService.addPatient(patient);
        receptionistService.addPatient(patient1);

        receptionistService.addAppointment(patient, appointment);
        receptionistService.addAppointment(patient, appointment1);

        receptionistService.cancelAppointment(patient, appointment);

        receptionistService.showDoctorsBySpecialization(ANESTHESIOLOGY);
        receptionistService.showDoctorsBySpecialization(PEDIATRICS);
        receptionistService.showDoctorsBySpecialization(CARDIOLOGY);
        System.out.println();
        receptionistService.showEquipment();

        System.out.println("\n");

        System.out.println(GREEN_BACKGROUND + BLACK + "========== DOCTOR STUFF ========================" + RESET);

        // DoctorService methods
        DoctorService doctorService = new DoctorService();

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

        administratorService.addMedicine(new Medicine("Triferment", "Biofarm", 10.0, "pancreatine"));
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
        PatientService patientService = new PatientService();
        patientService.showDoctorsBySpecialization(ENDOCRINOLOGY);

        ArrayList<Medicine> treatment1 = new ArrayList<>();
        treatment1.add(medicine2);
        treatment1.add(medicine);
        Diagnostic diagnostic = new Diagnostic("COVID-19", treatment1,
                new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        doctorService.addDiagnostic(patient, diagnostic);
        diagnostic.setDescription("Bad form of SARS-COV-2");
        patientService.showDiagnosticHistory(patient);

        Prescription prescription2 = new Prescription(diagnostic.getTreatment(), doctor, diagnostic.getDate());
        doctorService.addMedicineOnPrescription(new Medicine("Ibuprofen", "Bayer", 5.90, "ibuprofen"), prescription2);
        System.out.println(prescription2);

        System.out.println();

        System.out.println(GREEN_BACKGROUND + BLACK + "========== PAYING DEBT ========================" + RESET);

        receptionistService.addAppointment(patient, new Appointment(new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()), "12:00", doctor));

        System.out.println(patient.getDebt());
        patientService.pay(patient, patient.getDebt());
        System.out.println(patient.getDebt());

    }
}
