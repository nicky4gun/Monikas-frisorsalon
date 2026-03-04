package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.BookingView;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.repositories.BookingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    void setUp() throws SQLException, IOException {
        config = new DbConfig();
        Connection conn = config.getConnection();

        InputStream data = BookingServiceTest.class.getClassLoader().getResourceAsStream("test-data.sql");

        assertNotNull(data);
        String sql = new String(data.readAllBytes());
        conn.createStatement().execute(sql);

        BookingRepository bookingRepository = new BookingRepository(config);
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    void addBooking_shouldStoreInDB() {
        LocalDate date = LocalDate.of(2026, 3, 3);
        LocalTime time = LocalTime.of(13, 0);
        int employeeId = 1;
        int customerId = 1;
        int hairTreatmentId = 1;
        Status status = Status.BOOKED;
        String note = "Bob: jeg vil gerne have krøller siger Hanne";

        bookingService.addBooking(date, time, employeeId, customerId, hairTreatmentId, status, note);

        Booking result = bookingService.findBookingById(1);
        assertNotNull(result);
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals(LocalDate.of(2026, 3, 3), result.getDate());
        assertEquals(LocalTime.of(13, 0), result.getTime());
    }

    @Test
    void findBookingById() {
        int bookingID = 1;

        Booking result = bookingService.findBookingById(bookingID);

        assertNotNull(result);
        assertEquals(bookingID, result.getId());
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals(LocalDate.of(2026, 3, 3), result.getDate());
        assertEquals(LocalTime.of(13, 0), result.getTime());
        assertEquals(1, result.getEmployeeId());
        assertEquals(1, result.getCustomerId());
        assertEquals(1, result.getHairTreatmentId());
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals("Bob skal vaske hår", result.getNote());
    }

    @Test
    void findBookingByDateAndEmployeeId() {
        LocalDate date = LocalDate.of(2026, 3, 3);
        int employeeId = 1;

        List<BookingView> results = bookingService.getBookingsByDateAndEmployee(date, employeeId);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        BookingView result = results.get(0);

        assertEquals(employeeId, result.getId());
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals(LocalTime.of(13, 0), result.getTime());
        assertEquals(1, result.getEmployeeId());
        assertEquals(1, result.getCustomerId());
        assertEquals(1, result.getTreatmentId());
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals("Bob skal vaske hår", result.getNote());
    }

    @Test
    void updateBooking() {
        Booking booking = new Booking(2, LocalDate.of(2026, 2, 4), LocalTime.of(13,0), 1, 1, 1, Status.BOOKED,"bob skal vaske hår" );

        booking.setDate(LocalDate.of(2026, 3, 4));
        booking.setTime(LocalTime.of(14, 0));
        booking.setEmployeeId(2);
        booking.setCustomerId(2);
        booking.setHairTreatmentId(2);
        booking.setStatus(Status.BOOKED);
        booking.setNote("Bob skal ikke vaske hår");

        Booking result = booking;

        assertNotNull(result);
        assertEquals(LocalDate.of(2026, 3, 4), result.getDate());
        assertEquals(LocalTime.of(14, 0), result.getTime());
        assertEquals(2, result.getEmployeeId());
        assertEquals(2, result.getCustomerId());
        assertEquals(2, result.getHairTreatmentId());
        assertEquals(Status.BOOKED, result.getStatus());
        assertEquals("Bob skal ikke vaske hår", result.getNote());
    }

    @Test
    void cancelBooking() {
        int bookingID = 1;

        Booking booking = bookingService.findBookingById(bookingID);
        assertNotNull(booking);

        booking.setStatus(Status.CANCELLED);
        booking.setNote("Customer cancelled");
        bookingService.cancelBooking(bookingID);

        assertEquals(bookingID, booking.getId());
        assertEquals(Status.CANCELLED, booking.getStatus());

    }

    @Test
    void completeBooking() {
        int bookingID = 1;

        Booking booking = bookingService.findBookingById(bookingID);
        assertNotNull(booking);

        booking.setStatus(Status.COMPLETED);
        booking.setNote("Customer Completed");
        bookingService.completeBooking(bookingID);

        assertEquals(bookingID, booking.getId());
        assertEquals(Status.COMPLETED, booking.getStatus());


    }

    @AfterEach
    void tearDown() {
        config = null;
        bookingService = null;
    }
}