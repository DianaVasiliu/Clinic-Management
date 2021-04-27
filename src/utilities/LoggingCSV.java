package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingCSV {

    private LoggingCSV() {}

    public static void log(String text) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("logs.csv", true));

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            writer.write(text + ", " + date.format(formatter) + "\n");
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
