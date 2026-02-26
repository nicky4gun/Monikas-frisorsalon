package dk.jannikognicklas.monikasfrisorsalon.repositories;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.Customer;

import java.sql.*;

public class CustomerRepository {
    private final DbConfig config;

    public CustomerRepository(DbConfig config) {
        this.config = config;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (name,email,phonenumber) VALUES (?, ?, ?,)";
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
            } catch (SQLException e) {
                throw new RuntimeException("An error occurred trying to add new booking", e);
            }


        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to add new customer", e);
        }

    }
    public void UpdateCustomer(Customer customer) {
        String sql = "UPDATE customers set name = ?,  email = ?,phone = ? where id = ? ";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setInt(3, customer.getPhoneNumber());

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getInt(1));
                }
            }catch (SQLException e) {
                throw new RuntimeException("An error occurred trying to update customer ", e);
            }

        }catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to update customer", e);
        }


    }
}


