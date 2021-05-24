package repositories;

import clinic.Administrator;
import database.DBConfig;
import employees.Doctor;
import employees.Employee;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import equipment.Consumable;
import equipment.Electronic;
import equipment.Equipment;
import equipment.NonConsumable;
import patients.Medicine;

public class AdministratorRepo extends CommonRepo {

    private static AdministratorRepo instance;

    private AdministratorRepo() {}

    public static AdministratorRepo getInstance() {
        if (instance == null) {
            instance = new AdministratorRepo();
        }
        return instance;
    }

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

    public void updateEmployeeSalary(String criteria, double newSalary) {
        String query = "UPDATE employees SET salary = ? WHERE " + criteria;
        Connection connection = DBConfig.getDatabaseConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, newSalary);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteEmployeeById(long id) {
        if (id == Administrator.getInstance().getID()) {
            return;
        }

        Connection connection = DBConfig.getDatabaseConnection();

        try {
            Employee dbEmployee = selectEmployeeById(id);
            if (dbEmployee instanceof Doctor) {
                String query = "DELETE FROM doctor WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, id);
                statement.execute();
            }

            String query = "DELETE FROM employees WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteMedicine(String criteria) {
        String query = "DELETE FROM medicine WHERE " + criteria;
        Connection connection = DBConfig.getDatabaseConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
