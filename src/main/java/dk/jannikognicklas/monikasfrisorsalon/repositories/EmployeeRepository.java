package dk.jannikognicklas.monikasfrisorsalon.repositories;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private final DbConfig config;

    public EmployeeRepository(DbConfig config) {
        this.config = config;
    }

    public Employee addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, username, password) VALUES (?, ?, ?)";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getUsername());
            stmt.setString(3, employee.getPassword());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    employee.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to add new user", e);
        }

        return employee;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM employees WHERE username = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find username", e);
        }
    }

    public Employee checkLogin(String username, String password) {
        String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Employee> findAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ";

        try (Connection conn  = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("cant find employees"+ e.getMessage());
        }

        return  employees;
    }
    public Employee findEmployeesById(int employeeId) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return mapEmployee(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("cant find Employee Id")  ;
        }

        return null;
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("username"),
                rs.getString("password")
        );
    }
}
