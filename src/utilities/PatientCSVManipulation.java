package utilities;

import clinic.Clinic;
import patients.Patient;
import services.ReceptionistService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PatientCSVManipulation implements CSVManipulation {

    private static PatientCSVManipulation reader;

    private PatientCSVManipulation() {}

    public static PatientCSVManipulation getInstance() {
        if (reader == null) {
            reader = new PatientCSVManipulation();
        }
        return reader;
    }

    @Override
    public void readData() {
        try {
            String filePath = "clinic_data/patients.csv";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();
            while (line != null) {
                String[] info = line.split(",");
                ArrayList<String> lineInfo = new ArrayList<>(Arrays.asList(info));

                for (int i = 0; i < lineInfo.size(); i++) {
                    lineInfo.set(i, lineInfo.get(i).trim());
                }

                Patient patient = new Patient(lineInfo.get(0),
                        lineInfo.get(1),
                        lineInfo.get(2),
                        Integer.parseInt(lineInfo.get(3)),
                        lineInfo.get(4).charAt(0),
                        Integer.parseInt(lineInfo.get(5)),
                        Double.parseDouble(lineInfo.get(6)));

                ReceptionistService.getInstance().addPatient(patient);

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
            String filePath = "clinic_data/patients.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            ArrayList<Patient> patients = Clinic.getInstance().getPatients();
            for (Patient p : patients) {
                String output = p.getFirstName() + ", " +
                        p.getLastName() + ", " +
                        p.getSSN() + ", " +
                        p.getAge() + ", " +
                        p.getSex() + ", " +
                        p.getHeight() + ", " +
                        p.getWeight() + "\n";
                writer.write(output);
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
