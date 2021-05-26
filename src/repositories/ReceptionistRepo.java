package repositories;

import clinic.Clinic;
import database.DBConfig;
import patients.Appointment;
import patients.Patient;
import utilities.Date;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReceptionistRepo extends CommonRepo {

    private static ReceptionistRepo instance;

    private ReceptionistRepo() {}

    public static ReceptionistRepo getInstance() {
        if (instance == null) {
            instance = new ReceptionistRepo();
        }
        return instance;
    }

    /* database operations */

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

    public void insertAppointment(Appointment appointment) {
        if (appointment != null) {
            Appointment dbAppointment = selectAppointmentById(appointment.getID());

            if (dbAppointment != null) {
                return;
            }

            String query = "INSERT INTO appointment VALUES(?, ?, ?, ?)";
            Connection connection = DBConfig.getDatabaseConnection();

            var patients = Clinic.getInstance().getPatients();
            int index = -1;
            Patient patient = null;
            for (Patient p : patients) {
                index = p.getAppointments().indexOf(appointment);
                if (index >= 0) {
                    patient = p;
                    break;
                }
            }

            if (index >= 0) {
                try {
                    String appointmentDate = appointment.getDate().getFullDate() + " " + appointment.getTime();
                    appointmentDate = appointmentDate.replace("/", "-");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    java.util.Date parsedDate = dateFormat.parse(appointmentDate);
                    String formattedDate = dateFormat.format(parsedDate);

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setLong(1, appointment.getID());
                    statement.setString(2, formattedDate);
                    statement.setLong(3, appointment.getDoctor().getID());
                    statement.setLong(4, patient.getID());
                    statement.execute();

                    System.out.println("Appointment inserted");

                } catch (SQLException | ParseException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void updatePatient(String criteria, String columnToUpdate, String newValue) {
        if (!criteria.equals("") && !columnToUpdate.equals("") && !newValue.equals("")) {
            String query = "UPDATE patient SET " + columnToUpdate + " = ? WHERE " + criteria;
            Connection connection = DBConfig.getDatabaseConnection();

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                if (criteria.equalsIgnoreCase("age") || criteria.equalsIgnoreCase("height")) {
                    statement.setInt(1, Integer.parseInt(newValue));
                }
                else if (criteria.equalsIgnoreCase("weight") || criteria.equalsIgnoreCase("debt")) {
                    statement.setDouble(1, Double.parseDouble(newValue));
                }
                else if (criteria.equalsIgnoreCase("id")) {
                    System.err.println("Cannot update patient id");
                    return;
                }
                else {
                    statement.setString(1, newValue);
                }
                statement.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void updateAppointment(String criteria, String columnToUpdate, String newValue) {
        String query = "UPDATE appointment SET " + columnToUpdate + " = ? WHERE " + criteria;
        Connection connection = DBConfig.getDatabaseConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            if (columnToUpdate.equalsIgnoreCase("doctor_id") || columnToUpdate.equalsIgnoreCase("patient_id")) {
                statement.setLong(1, Long.parseLong(newValue));
            }
            else if (columnToUpdate.equalsIgnoreCase("id")) {
                return;
            }
            else {
                int day = Integer.parseInt(newValue.split("[/-]")[0]);
                int month = Integer.parseInt(newValue.split("[/-]")[1]);
                int year = Integer.parseInt(newValue.split("[/-]")[2].split(" ")[0]);
                String time = newValue.split("[/-]")[2].split(" ")[1];

                statement.setString(1, (new Date(day, month, year)).toSQLDate().toString() + " " + time);
            }
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void deletePatientById(long id) {
        // TODO : either ON DELETE CASCADE or DELETE FROM appointment ...

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

    public void deleteAppointment(String criteria) {
        String query = "DELETE FROM appointment WHERE " + criteria;
        Connection connection = DBConfig.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute(query);

            System.out.println("Appointment deleted");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}