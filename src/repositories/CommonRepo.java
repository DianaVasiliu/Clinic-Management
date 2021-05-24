package repositories;

import clinic.Administrator;
import database.DBConfig;
import employees.Doctor;
import employees.Employee;
import employees.Nurse;
import employees.Receptionist;
import employees.utils.Specialization;
import equipment.Consumable;
import equipment.Electronic;
import equipment.Equipment;
import equipment.NonConsumable;
import patients.Appointment;
import patients.Medicine;
import patients.Patient;
import utilities.Date;

import java.sql.*;
import java.util.TreeSet;

public abstract class CommonRepo {

    private static CommonRepo instance;

    public Employee selectEmployeeById(long id) {
        String query = "SELECT * FROM employees WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        Employee employee = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                employee = getDatabaseEmployee(result);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (employee != null) {
            employee.setID(id);
        }
        return employee;
    }

    public Medicine selectMedicineById(long id) {
        String query = "SELECT * FROM medicine WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        Medicine medicine = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                String producer = result.getString("producer");
                double price = result.getDouble("price");
                String active_substance = result.getString("active_substance");

                medicine = new Medicine(name, producer, price, active_substance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicine;
    }

    public Equipment selectEquipmentById(long id) {
        String query = "SELECT * FROM equipment WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        Equipment equipment = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                double price = result.getDouble("price");
                Date buy_date = new Date(result.getDate("buy_date").toLocalDate().getDayOfMonth(),
                        result.getDate("buy_date").toLocalDate().getMonthValue(),
                        result.getDate("buy_date").toLocalDate().getYear());
                String eq_type = result.getString("eq_type");

                switch (eq_type) {
                    case "electronic" -> {
                        String query2 = "SELECT watts_per_hour FROM electronic WHERE id = ?";
                        PreparedStatement statement1 = connection.prepareStatement(query2);
                        statement1.setLong(1, id);
                        ResultSet result1 = statement1.executeQuery();
                        double watts_per_hour = 0.0;
                        if (result1.next()) {
                            watts_per_hour = result1.getDouble("watts_per_hour");
                        }

                        equipment = new Electronic(name, price, watts_per_hour, buy_date);
                    }
                    case "consumable" -> {
                        String query2 = "SELECT * FROM consumable WHERE id = ?";
                        PreparedStatement statement1 = connection.prepareStatement(query2);
                        statement1.setLong(1, id);
                        ResultSet result1 = statement1.executeQuery();
                        int items_in_package = 0;
                        double avg_last_time = 0.0;
                        if (result1.next()) {
                            items_in_package = result1.getInt("items_in_package");
                            avg_last_time = result1.getDouble("avg_last_time");
                        }

                        equipment = new Consumable(name, price, items_in_package, buy_date, avg_last_time);
                    }
                    case "nonconsumable" -> {
                        String query2 = "SELECT days_after_change FROM nonconsumable WHERE id = ?";
                        PreparedStatement statement1 = connection.prepareStatement(query2);
                        statement1.setLong(1, id);
                        ResultSet result1 = statement1.executeQuery();
                        double days_after_change = 0.0;
                        if (result1.next()) {
                            days_after_change = result1.getDouble("days_after_change");
                        }

                        equipment = new NonConsumable(name, price, buy_date, days_after_change);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return equipment;
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

    public Appointment selectAppointmentById(long id) {
        String query = "SELECT * FROM appointment WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        Appointment appointment = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);;
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String date = resultSet.getString("date_time");
                long doctor_id = resultSet.getLong("doctor_id");
                long patient_id = resultSet.getLong("patient_id");
                Date appointmentDate = new Date(
                        Integer.parseInt(date.split("-")[2].split(" ")[0]),
                        Integer.parseInt(date.split("-")[1]),
                        Integer.parseInt(date.split("-")[0]));
                String appointmentTime = date.split(" ")[1];

                Doctor appointmentDoctor = (Doctor) selectEmployeeById(doctor_id);
                Patient appointmentPatient = selectPatientById(patient_id);

                appointment = new Appointment(appointmentDate, appointmentTime, appointmentDoctor);
                appointmentPatient.getAppointments().add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointment;
    }

    public TreeSet<Patient> selectPatientsByCriteria(String criteria, String value) {
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

    public TreeSet<Employee> selectEmployeesByCriteria(String criteria, String value) {
        String query = "SELECT * FROM employees WHERE " + criteria + " " + value;
        Connection connection = DBConfig.getDatabaseConnection();
        TreeSet<Employee> employees = new TreeSet<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Employee employee = getDatabaseEmployee(result);
                employees.add(employee);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employees;
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

    private Employee getDatabaseEmployee(ResultSet result) throws SQLException {
        if (result != null) {
            long id = result.getInt("id");
            String first_name = result.getString("first_name");
            String last_name = result.getString("last_name");
            Date birthday = new Date(result.getDate("birthday").toLocalDate().getDayOfMonth(),
                                     result.getDate("birthday").toLocalDate().getMonthValue(),
                                     result.getDate("birthday").toLocalDate().getYear());
            char sex = result.getString("sex").charAt(0);
            int experience = result.getInt("experience");

            String emp_type = result.getString("emp_type");

            Employee employee = null;
            switch (emp_type) {
                case "doctor" -> {
                    String query = "SELECT specialization FROM doctor WHERE id = " + id;
                    Connection connection = DBConfig.getDatabaseConnection();
                    Statement statement = connection.createStatement();
                    ResultSet set = statement.executeQuery(query);
                    String spec = "";
                    if (set.next()) {
                        spec = set.getString("specialization");
                    }
                    Specialization specialization = Specialization.getSpecialization(spec);
                    employee = new Doctor(first_name, last_name, birthday, sex, specialization, experience);
                }
                case "nurse" -> employee = new Nurse(first_name, last_name, birthday, sex, experience);
                case "receptionist" -> employee = new Receptionist(first_name, last_name, birthday, sex, experience);
                case "administrator" -> {
                    Administrator administrator = Administrator.getInstance();
                    administrator.setFirstName(first_name);
                    administrator.setLastName(last_name);
                    administrator.setBirthday(birthday);
                    administrator.setSex(sex);
                    administrator.calculateSalary();
                    administrator.setExperience(experience);
                    employee = administrator;
                }
            }

            if (employee != null) {
                employee.setAge();
                employee.setID(id);
            }
            return employee;
        }
        return null;
    }
}
