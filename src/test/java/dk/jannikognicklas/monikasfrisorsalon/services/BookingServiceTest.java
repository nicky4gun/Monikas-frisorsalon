package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {
    private DbConfig config;
    private BookingService bookingService;

    @BeforeAll
    static void initSchema() throws Exception {
        DbConfig config = new DbConfig();
        Connection conn = config.getConnection();

        InputStream schema = BookingServiceTest.class.getClassLoader().getResourceAsStream("schema.sql");

        assertNotNull(schema);
        String sql = new String(schema.readAllBytes());
        conn.createStatement().execute(sql);
    }

    @BeforeEach
    void setUp() throws SQLException {
        config = new DbConfig();
        Connection conn = config.getConnection();

        conn.createStatement().execute("DELETE FROM bookings");
        conn.createStatement().execute("DELETE FROM customers");
        conn.createStatement().execute("DELETE FROM employees");
        conn.createStatement().execute("DELETE FROM hair_treatments");

        conn.createStatement().execute("INSERT INTO employees (name, username, password) VALUES ('Test Employee', 'testuser', 'password')");
        conn.createStatement().execute("INSERT INTO customers (name) VALUES ('Test Customer')");
        conn.createStatement().execute("INSERT INTO hair_treatments (hair_treatment, duration, price) VALUES ('MALE', 30, 99.99)");

        BookingRepository bookingRepository = new BookingRepository(config);
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    void addBooking_shouldStoreInDB() {
        LocalDate date = LocalDate.of(2026,3,1);
        LocalTime time = LocalTime.of( 14,30);
        int employeeId = 1;
        int customerId = 1;
        int hairTreatmentId = 1;
        Status status = Status.BOOKED;
        String note = "bob: jeg vil gerne have krøller siger hanne";

        bookingService.addBooking(date, time, employeeId, customerId, hairTreatmentId, status, note);

        Booking result = bookingService.findBookingById(1);
        assertNotNull(result);
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals(LocalDate.of(2026, 3,1), result.getDate());
        assertEquals(LocalTime.of(14,30), result.getTime());
    }

    @Test
    void findBookingById() {
        fail("Not yet implemented");
    }

    @Test
    void findBookingByEmployeeId() {
        fail("Not yet implemented");
    }

    @Test
    void updateBooking() {
        fail("Not yet implemented");
    }

    @Test
    void cancelBooking() {
        fail("Not yet implemented");
    }

    @Test
    void completeBooking() {
        fail("Not yet implemented");
    }
}