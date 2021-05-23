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
}
