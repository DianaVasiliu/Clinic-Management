package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseOperations {

    public static void createAllTables() {
        Connection connection = DBConfig.getDatabaseConnection();

        String query = "{CALL create_tables()}";

        try {
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        createAllTables();
    }
}