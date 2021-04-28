package utilities;

import employees.Doctor;
import employees.Employee;
import employees.Nurse;
import employees.Receptionist;
import employees.utils.Specialization;
import services.AdministratorService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeCSVManipulation implements CSVManipulation {

    private static EmployeeCSVManipulation reader;

    private EmployeeCSVManipulation() {}

    public static EmployeeCSVManipulation getInstance() {
        if (reader == null) {
            reader = new EmployeeCSVManipulation();
        }
        return reader;
    }

    @Override
    public void readData() {
        try {
            String filePath = "clinic_data/employees.csv";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();
            while (line != null) {
                String[] info = line.split(",");
                ArrayList<String> lineInfo = new ArrayList<>(Arrays.asList(info));

                for (int i = 0; i < lineInfo.size(); i++) {
                    lineInfo.set(i, lineInfo.get(i).trim());
                }

                ArrayList<String> date = new ArrayList<>(Arrays.asList(lineInfo.get(3).split("/")));
                int day = Integer.parseInt(date.get(0));
                int month = Integer.parseInt(date.get(1));
                int year = Integer.parseInt(date.get(2));
                Date birthday = new Date(day, month, year);

                if (lineInfo.get(0).toLowerCase().compareTo("doctor") == 0) {
                    Specialization spec = Specialization.getSpecialization(lineInfo.get(6));
                    Doctor doctor = new Doctor(
                            lineInfo.get(1),
                            lineInfo.get(2),
                            birthday,
                            lineInfo.get(4).charAt(0),
                            spec,
                            Integer.parseInt(lineInfo.get(5))
                    );
                    AdministratorService.getInstance().addEmployee(doctor);
                }
                else if (lineInfo.get(0).toLowerCase().compareTo("nurse") == 0) {
                    Nurse nurse = new Nurse(
                            lineInfo.get(1),
                            lineInfo.get(2),
                            birthday,
                            lineInfo.get(4).charAt(0),
                            Integer.parseInt(lineInfo.get(5))
                    );
                    AdministratorService.getInstance().addEmployee(nurse);
                }
                else if (lineInfo.get(0).toLowerCase().compareTo("receptionist") == 0) {
                    Receptionist receptionist = new Receptionist(
                            lineInfo.get(1),
                            lineInfo.get(2),
                            birthday,
                            lineInfo.get(4).charAt(0),
                            Integer.parseInt(lineInfo.get(5))
                    );
                    AdministratorService.getInstance().addEmployee(receptionist);
                }
                else {
                    System.err.println("Wrong type of employee");
                    return;
                }

                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println("Input file error");
            System.out.println(e.getMessage());
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Too many values given");
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Other exeption");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeData() {
        try {
            String filePath = "clinic_data/employees.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            ArrayList<Employee> employees = AdministratorService.getInstance().getAllEmployees();
            for (Employee e : employees) {
                StringBuilder output = new StringBuilder();
                String date = e.getBirthday().getDay() + "/" + e.getBirthday().getMonth() + "/" + e.getBirthday().getYear();

                output.append(e.getFirstName()).append(", ")
                        .append(e.getLastName()).append(", ")
                        .append(date).append(", ")
                        .append(e.getSex()).append(", ")
                        .append(e.getExperience());

                if (e instanceof Doctor) {
                    output.insert(0, "Doctor, ");
                    output.append(", ").append(((Doctor) e).getSpecialization().name().toLowerCase());
                }
                else if (e instanceof Nurse) {
                    output.insert(0, "Nurse, ");
                }
                else if (e instanceof Receptionist) {
                    output.insert(0, "Receptionist, ");
                }
                writer.write(output + "\n");
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
