package repositories;

import database.DBConfig;
import patients.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public class ReceptionistRepo {

    public void insertPatient(Patient patient) {
        if (patient != null) {
            Patient dbPatient = selectPatientById(patient.getID());

            if (dbPatient != null) {
                return;
            }

            String query = "INSERT INTO patient VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection connection = DBConfig.getDatabaseConnection();

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, patient.getID());
                statement.setString(2, patient.getFirstName());
                statement.setString(3, patient.getLastName());
                statement.setString(4, patient.getSSN());
                statement.setInt(5, patient.getAge());
                statement.setString(6, String.valueOf(patient.getSex()));
                statement.setInt(7, patient.getHeight());
                statement.setDouble(8, patient.getWeight());
                statement.setDouble(9, patient.getDebt());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Patient selectPatientById(long id) {
        String query = "SELECT * FROM patient WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        Patient patient = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                patient = getDatabasePatient(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return patient;
    }

    public TreeSet<Patient> selectPatientsWithCriteria(String criteria, String value) {
        String query = "SELECT * FROM patient WHERE " + criteria + " = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        TreeSet<Patient> patients = new TreeSet<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, value);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Patient patient = getDatabasePatient(result);
                patients.add(patient);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return patients;
    }

    public void deletePatientById(long id) {
        String query = "DELETE FROM patient WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Patient getDatabasePatient(ResultSet result) throws SQLException{
        if (result != null) {
            long id = result.getInt("id");
            String first_name = result.getString("first_name");
            String last_name = result.getString("last_name");
            String SSN = result.getString("ssn");
            int age = result.getInt("age");
            char sex = result.getString("sex").charAt(0);
            int height = result.getInt("height");
            double weight = result.getDouble("weight");
            double debt = result.getDouble("debt");

            Patient patient = new Patient(first_name, last_name, SSN, age, sex, height, weight);
            patient.setID(id);
            patient.setDebt(debt);
            return patient;
        }
        return null;
    }
}