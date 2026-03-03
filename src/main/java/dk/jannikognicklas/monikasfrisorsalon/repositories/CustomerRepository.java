package dk.jannikognicklas.monikasfrisorsalon.repositories;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.Customer;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final DbConfig config;

    public CustomerRepository(DbConfig config) {
        this.config = config;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1,customer.getName());
            stmt.setString(2,customer.getEmail());
            stmt.setInt(3,customer.getPhoneNumber());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to add new customer", e);
        }

    }

    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn  = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cant find customers"+ e.getMessage());
        }

        return customers;
    }

    public Customer findCustomerById(int employeeId) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Cannot find customer by id: " + employeeId, e);
        }

        return null;
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers set name = ?,  email = ?, phone = ? WHERE id = ? ";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setInt(3, customer.getPhoneNumber());
            stmt.setInt(4, customer.getId());
            stmt.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to update customer", e);
        }
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("phone")
        );
    }
}


