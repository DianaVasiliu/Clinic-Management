package database;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseOperations {

    public static void createAllTables() {
        Connection connection = DBConfig.getDatabaseConnection();
        try {
            String file = "table_creation.sql";
            Scanner sc = new Scanner(new File(file));
            Statement statement = connection.createStatement();
            sc.useDelimiter(";");

            while (sc.hasNext()) {
                String query = sc.next().trim();
                statement.execute(query);
            }
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        createAllTables();

    }
}