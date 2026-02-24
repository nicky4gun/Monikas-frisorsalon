package dk.jannikognicklas.monikasfrisorsalon.repositorys;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.model.Booking;
import dk.jannikognicklas.monikasfrisorsalon.model.enums.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final DbConfig config;

    public BookingRepository(DbConfig config) {
        this.config = config;
    }

    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (start_time, end_time, customer_id, hair_treatment_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(booking.getStartTime()));
            stmt.setTimestamp(2, Timestamp.valueOf(booking.getEndTime()));
            stmt.setInt(3, booking.getCustomerId());
            stmt.setInt(4, booking.getHairTreatmentId());
            stmt.setString(5, String.valueOf(booking.getStatus()));

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    booking.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException("An error occurred trying to add new booking", e);
            }


        } catch (SQLException e) {
            throw new RuntimeException("an error occurred while trying to insert a booking", e);
        }
    }

    public List<Booking> findBookingById(int bookingId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE id = ? AND status = 'BOOKED'";
        
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                    LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                    int employeeId = rs.getInt("employee_id");
                    int customerId = rs.getInt("customer_id");
                    int hairTreatmentId = rs.getInt("hair_treatment_id");
                    Status status = Status.valueOf(rs.getString("status"));

                    bookings.add(new Booking(id, startTime, endTime, employeeId, customerId, hairTreatmentId, status));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find booking by id" + e);
        }

        return bookings;
    }

    public List<Booking> findBookingByEmployeeId(int employeeId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE employee_id = ? AND status = 'BOOKED'";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                    LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                    int customerId = rs.getInt("customer_id");
                    int hairTreatmentId = rs.getInt("hair_treatment_id");
                    Status status = Status.valueOf(rs.getString("status"));

                    bookings.add(new Booking(id, startTime, endTime, employeeId, customerId, hairTreatmentId, status));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find Booking by employee id " + e);
        }

        return bookings;
    }

    public void updateBooking(Booking booking) {
        String sql = "UPDATE bookings (start_time, end_time, customer_id, hair_treatment_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(booking.getStartTime()));
            stmt.setTimestamp(2, Timestamp.valueOf(booking.getEndTime()));
            stmt.setInt(3, booking.getCustomerId());
            stmt.setInt(4, booking.getHairTreatmentId());
            stmt.setString(5, String.valueOf(booking.getStatus()));
            stmt.executeUpdate();



            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    booking.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            throw new RuntimeException("an error occurred while trying to insert a booking", e);
        }
    }
    

    // Sletning af bestilling ift. skats lovgiving
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'CANCELLED' WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to cancel booking" + e);
        }
    }

    public boolean completeBooking(int id) {
        String sql = "UPDATE bookings SET status = 'COMPLETED' WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to complete Booking" + e);
        }
    }
}
