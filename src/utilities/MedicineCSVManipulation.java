package utilities;

import clinic.Clinic;
import patients.Medicine;
import services.AdministratorService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MedicineCSVManipulation implements CSVManipulation {

    private static MedicineCSVManipulation instance;

    private MedicineCSVManipulation() {}

    public static MedicineCSVManipulation getInstance() {
        if (instance == null) {
            instance = new MedicineCSVManipulation();
        }
        return instance;
    }

    @Override
    public void readData() {
        try {
            String filePath = "clinic_data/medicine.csv";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();

            line = reader.readLine();
            while (line != null) {
                String[] info = line.split(",");
                ArrayList<String> lineInfo = new ArrayList<>(Arrays.asList(info));

                for (int i = 0; i < lineInfo.size(); i++) {
                    lineInfo.set(i, lineInfo.get(i).trim());
                }

                Medicine medicine = new Medicine(lineInfo.get(0),
                        lineInfo.get(1),
                        Double.parseDouble(lineInfo.get(2)),
                        lineInfo.get(3));

                AdministratorService.getInstance().addMedicine(medicine);

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
            String filePath = "clinic_data/medicine.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            ArrayList<Medicine> medicine = Clinic.getInstance().getMedicines();
            writer.write("NAME, PRODUCER, PRICE, ACTIVE SUBSTANCE\n");
            for (Medicine m : medicine) {
                String output = m.getName() + ", " +
                        m.getProducer() + ", " +
                        m.getPrice() + ", " +
                        m.getActiveSubstance() + "\n";
                writer.write(output);
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
