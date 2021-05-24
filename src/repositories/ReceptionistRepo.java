package repositories;

import clinic.Clinic;
import database.DBConfig;
import employees.Receptionist;
import patients.Appointment;
import patients.Patient;
import utilities.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            System.out.println("ok");
            if (index >= 0) {
                try {
                    System.out.println("ok");
                    String appointmentDate = appointment.getDate().getFullDate() + " " + appointment.getTime();
                    appointmentDate = appointmentDate.replace("/", "-");

                    System.out.println("appointment date:" + appointmentDate);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    java.util.Date parsedDate = dateFormat.parse(appointmentDate);
                    String formattedDate = dateFormat.format(parsedDate);

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setLong(1, appointment.getID());
                    statement.setString(2, formattedDate);
                    statement.setLong(3, appointment.getDoctor().getID());
                    statement.setLong(4, patient.getID());
                    statement.execute();

                } catch (SQLException | ParseException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void deleteAppointment() {
        // TODO
    }
}