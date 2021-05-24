package repositories;

import clinic.Administrator;
import database.DBConfig;
import employees.Doctor;
import employees.Employee;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import employees.Nurse;
import employees.Receptionist;
import employees.utils.Specialization;
import equipment.Consumable;
import equipment.Electronic;
import equipment.Equipment;
import equipment.NonConsumable;
import patients.Medicine;
import utilities.Date;

public class AdministratorRepo {

    public void insertEmployee(Employee employee) {
        if (employee != null) {
            Employee dbEmployee = selectEmployeeById(employee.getID());

            if (dbEmployee != null) {
                return;
            }

            String query = "INSERT INTO employees VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String emp_type = employee.getClass().getSimpleName().toLowerCase();
            Connection connection = DBConfig.getDatabaseConnection();

            try {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = format.parse(employee.getBirthday().toString());

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, employee.getID());
                statement.setString(2, employee.getFirstName());
                statement.setString(3, employee.getLastName());
                statement.setDate(4, new java.sql.Date(date.getTime()));
                statement.setString(5, String.valueOf(employee.getSex()));
                statement.setInt(6, employee.getAge());
                statement.setDouble(7, employee.getSalary());
                statement.setInt(8, employee.getExperience());
                statement.setInt(9, employee.getDaysWorked());
                statement.setDouble(10, Employee.getSalaryPerHour());
                statement.setInt(11, Employee.getHoursPerDay());
                statement.setString(12, emp_type);
                statement.execute();

                if (employee instanceof Doctor) {
                    long id = employee.getID();
                    String spec = ((Doctor) employee).getSpecialization().toString();

                    String query2 = "INSERT INTO doctor VALUES(?, ?)";
                    PreparedStatement statement1 = connection.prepareStatement(query2);
                    statement1.setLong(1, id);
                    statement1.setString(2, spec);
                    statement1.execute();
                }

            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public Employee selectEmployeeById(long id) {
        String query = "SELECT * FROM employees WHERE id = ?";
        Connection connection = DBConfig.getDatabaseConnection();
        Employee employee = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                Date birthday = new Date(result.getDate("birthday").toLocalDate().getDayOfMonth(),
                                         result.getDate("birthday").toLocalDate().getMonthValue(),
                                         result.getDate("birthday").toLocalDate().getYear());
                char sex = result.getString("sex").charAt(0);
                int experience = result.getInt("experience");
                String emp_type = result.getString("emp_type");

                switch (emp_type) {
                    case "doctor" -> {
                        String query2 = "SELECT specialization FROM doctor WHERE id = ?";
                        PreparedStatement statement1 = connection.prepareStatement(query2);
                        statement1.setLong(1, id);
                        ResultSet res1 = statement1.executeQuery();
                        String specialization = "";
                        if (res1.next()) {
                            specialization = res1.getString("specialization");
                        }
                        employee = new Doctor(first_name, last_name, birthday, sex, Specialization.getSpecialization(specialization), experience);
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

    public void insertMedicine(Medicine medicine) {
        if (medicine != null) {
            Medicine dbMedicine = selectMedicineById(medicine.getID());

            if (dbMedicine != null) {
                return;
            }

            String query = "INSERT INTO medicine VALUES (?, ?, ?, ?, ?)";
            Connection connection = DBConfig.getDatabaseConnection();

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, medicine.getID());
                statement.setString(2, medicine.getName());
                statement.setString(3, medicine.getProducer());
                statement.setDouble(4, medicine.getPrice());
                statement.setString(5, medicine.getActiveSubstance());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
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
                            days_after_change = result.getDouble("days_after_change");
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

    public void insertEquipment(Equipment equipment) {
        if (equipment != null) {
            Equipment dbEquipment = selectEquipmentById(equipment.getID());

            if (dbEquipment != null) {
                return;
            }

            String query = "INSERT INTO equipment VALUES (?, ?, ?, ?, ?)";
            String eq_type = equipment.getClass().getSimpleName().toLowerCase();
            Connection connection = DBConfig.getDatabaseConnection();

            try {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = format.parse(equipment.getBuyDate().toString());

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, equipment.getID());
                statement.setString(2, equipment.getName());
                statement.setDouble(3, equipment.getPrice());
                statement.setDate(4, new java.sql.Date(date.getTime()));
                statement.setString(5, eq_type);
                statement.execute();

                if (equipment instanceof Electronic) {
                    query = "INSERT INTO electronic VALUES (?, ?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setLong(1, equipment.getID());
                    statement.setDouble(2, ((Electronic) equipment).getWattsPerHour());
                    statement.setDouble(3, ((Electronic) equipment).getHoursUsed());
                    statement.execute();
                }
                else if (equipment instanceof Consumable) {
                    query = "INSERT INTO consumable VALUES (?, ?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setLong(1, equipment.getID());
                    statement.setInt(2, ((Consumable) equipment).getNumberOfItemsInPackage());
                    statement.setDouble(3, ((Consumable) equipment).getAverageDaysItLasts());
                    statement.execute();
                }
                else if (equipment instanceof NonConsumable) {
                    query = "INSERT INTO nonconsumable VALUES (?, ?)";
                    statement = connection.prepareStatement(query);
                    statement.setLong(1, equipment.getID());
                    statement.setDouble(2, ((NonConsumable) equipment).getDaysAfterChange());
                    statement.execute();
                }

            } catch (ParseException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
