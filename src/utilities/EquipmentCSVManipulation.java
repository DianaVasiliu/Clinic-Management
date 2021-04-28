package utilities;

import clinic.Clinic;
import equipment.Consumable;
import equipment.Electronic;
import equipment.Equipment;
import equipment.NonConsumable;
import services.AdministratorService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EquipmentCSVManipulation implements CSVManipulation {

    private static EquipmentCSVManipulation reader;

    private EquipmentCSVManipulation() {}

    public static EquipmentCSVManipulation getInstance() {
        if (reader == null) {
            reader = new EquipmentCSVManipulation();
        }
        return reader;
    }

    @Override
    public void readData() {
        try {
            String filePath = "clinic_data/equipment.csv";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();
            while (line != null) {
                String[] info = line.split(",");
                ArrayList<String> lineInfo = new ArrayList<>(Arrays.asList(info));

                for (int i = 0; i < lineInfo.size(); i++) {
                    lineInfo.set(i, lineInfo.get(i).trim());
                }

                ArrayList<String> date = new ArrayList<>(Arrays.asList(lineInfo.get(4).split("/")));
                int day = Integer.parseInt(date.get(0));
                int month = Integer.parseInt(date.get(1));
                int year = Integer.parseInt(date.get(2));
                Date buyDate = new Date(day, month, year);

                if (lineInfo.get(0).toLowerCase().compareTo("consumable") == 0) {
                    Consumable consumable = new Consumable(
                            lineInfo.get(1),
                            Double.parseDouble(lineInfo.get(2)),
                            Integer.parseInt(lineInfo.get(3)),
                            buyDate,
                            Double.parseDouble(lineInfo.get(5))
                    );
                    AdministratorService.getInstance().addEquipment(consumable);
                }
                else if (lineInfo.get(0).toLowerCase().compareTo("nonconsumable") == 0) {
                    NonConsumable nonConsumable = new NonConsumable(
                            lineInfo.get(1),
                            Double.parseDouble(lineInfo.get(2)),
                            buyDate,
                            Double.parseDouble(lineInfo.get(3))
                    );
                    AdministratorService.getInstance().addEquipment(nonConsumable);
                }
                else if (lineInfo.get(0).toLowerCase().compareTo("electronic") == 0) {
                    Electronic electronic = new Electronic(
                            lineInfo.get(1),
                            Double.parseDouble(lineInfo.get(2)),
                            Double.parseDouble(lineInfo.get(3)),
                            buyDate
                    );
                    AdministratorService.getInstance().addEquipment(electronic);
                }
                else {
                    System.err.println("Wrong type of equipment");
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
            String filePath = "clinic_data/equipment.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            ArrayList<Equipment> equipment = Clinic.getInstance().getEquipment();
            for (Equipment e : equipment) {
                StringBuilder output = new StringBuilder();

                output.append(e.getClass().getSimpleName()).append(", ")
                        .append(e.getName()).append(", ")
                        .append(e.getPrice()).append(", ");

                String date = e.getBuyDate().getDay() + "/" + e.getBuyDate().getMonth() + "/" + e.getBuyDate().getYear();

                if (e instanceof Electronic) {
                    output.append(((Electronic) e).getWattsPerHour()).append(", ")
                            .append(date);
                }
                else if (e instanceof NonConsumable) {
                    output.append(((NonConsumable) e).getDaysAfterChange()).append(", ")
                            .append(date);
                }
                else if (e instanceof Consumable) {
                    output.append(((Consumable) e).getNumberOfItemsInPackage()).append(", ")
                            .append(date).append(", ")
                            .append(((Consumable) e).getAverageDaysItLasts());
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
