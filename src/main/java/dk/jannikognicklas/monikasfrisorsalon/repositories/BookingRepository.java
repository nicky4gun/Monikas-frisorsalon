package dk.jannikognicklas.monikasfrisorsalon.repositories;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.BookingView;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private final DbConfig config;

    public BookingRepository(DbConfig config) {
        this.config = config;
    }

    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (date, time, employee_id, customer_id, hair_treatment_id, status, note) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(booking.getDate()));
            stmt.setTime(2, Time.valueOf(booking.getTime()));
            stmt.setInt(3, booking.getEmployeeId());
            stmt.setInt(4, booking.getCustomerId());
            stmt.setInt(5, booking.getHairTreatmentId());
            stmt.setString(6, String.valueOf(booking.getStatus()));
            stmt.setString(7,booking.getNote());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    booking.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to insert a booking", e);
        }
    }

    public List<Booking> findAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY date DESC";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(mapBooking(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find Booking by employee id " + e);
        }

        return bookings;

    }

    public Booking findBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapBooking(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find booking by id" + e);
        }

        return null;
    }

    public List<BookingView> findBookingsByDateAndEmployee(LocalDate date, int employeeId) {
        List<BookingView> bookings = new ArrayList<>();
        String sql = """ 
                       SELECT b.id, b.date, b.time, b.note, b.status,
                              c.name AS customer_name,
                              c.id AS customer_id,
                              e.id AS employee_id,
                              ht.id AS hair_treatment_id,
                              e.name AS employee_name,
                              ht.hair_treatment AS treatment_name
                       FROM bookings b 
                       JOIN customers c on b.customer_id = c.id 
                       JOIN hair_treatments ht on b.hair_treatment_id = ht.id
                       JOIN employees e on b.employee_id = e.id
                       WHERE date = ? AND employee_id = ? AND status = 'BOOKED' ORDER BY time DESC
                       """;

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            stmt.setInt(2, employeeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapBookingView(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to find Booking by employee id " + e);
        }

        return bookings;
    }

    public void updateBooking(Booking booking) {
        String sql = "UPDATE bookings set date = ?, time = ?, employee_id = ?, customer_id = ?, hair_treatment_id = ?, status = ?, note = ? WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(booking.getDate()));
            stmt.setTime(2, Time.valueOf(booking.getTime()));
            stmt.setInt(3, booking.getEmployeeId());
            stmt.setInt(4, booking.getCustomerId());
            stmt.setInt(5, booking.getHairTreatmentId());
            stmt.setString(6, String.valueOf(booking.getStatus()));
            stmt.setString(7, booking.getNote());
            stmt.setInt(8, booking.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to insert a booking", e);
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

    private Booking mapBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getInt("employee_id"),
                rs.getInt("customer_id"),
                rs.getInt("hair_treatment_id"),
                Status.valueOf(rs.getString("status")),
                rs.getString("note")
        );
    }

    private BookingView mapBookingView(ResultSet rs) throws SQLException {
        return new BookingView(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("hair_treatment_id"),
                rs.getInt("employee_id"),
                rs.getTime("time").toLocalTime(),
                rs.getString("customer_name"),
                rs.getString("treatment_name"),
                rs.getString("employee_name"),
                rs.getString("note"),
                Status.valueOf(rs.getString("status"))
        );
    }
}
