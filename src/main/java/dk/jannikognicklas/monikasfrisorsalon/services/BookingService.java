package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.BookingView;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.repositories.BookingRepository;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void addBooking(LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status, String note) {
        if (date == null) throw new IllegalArgumentException("Vælg venligst en dato for booking");
        if (time == null) throw new IllegalArgumentException("Angiv venligst et tidspunkt for booking");
        if (employeeId <= 0) throw new IllegalArgumentException("Vælg venligst en frisør");
        if (customerId <= 0) throw new IllegalArgumentException("Vælg venligst en kunde");
        if (hairTreatmentId <= 0) throw new IllegalArgumentException("Vælg venligst en behandling");

        Booking booking = new Booking(date, time, employeeId, customerId, hairTreatmentId, status, note);
        bookingRepository.addBooking(booking);
    }

    public List<Booking> findAllBookings() {
        return bookingRepository.findAllBookings();
    }

    public Booking findBookingById(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Ugyldigt booking id");
        }

        Booking booking = bookingRepository.findBookingById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Booking blev ikke fundet");
        }

        return booking;
    }

    public List<BookingView> getBookingsByDateAndEmployee(LocalDate date, int employeeId) {
        return bookingRepository.findBookingsByDateAndEmployee(date, employeeId);
    }

    public  void updateBooking(int id, LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status, String note) {
        if (id <= 0) throw new IllegalArgumentException("Ugyldigt booking id");
        if (date == null) throw new IllegalArgumentException("Vælg venligst en dato for booking");
        if (time == null) throw new IllegalArgumentException("Angiv venligst et tidspunkt for booking");
        if (employeeId <= 0) throw new IllegalArgumentException("Vælg venligst en frisør");
        if (customerId <= 0) throw new IllegalArgumentException("Vælg venligst en kunde");
        if (hairTreatmentId <= 0) throw new IllegalArgumentException("Vælg venligst en behandling");

        Booking bookingToUpdate = new Booking(id, date, time, employeeId, customerId, hairTreatmentId, status, note);
        bookingRepository.updateBooking(bookingToUpdate);
    }

    public ObservableList<BookingView> searchBookings(String keywords){
        return bookingRepository.customerSearch(keywords);
    }

    public void cancelBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Ugyldigt booking id");
        }

        boolean deleted = bookingRepository.cancelBooking(bookingId);

        if (!deleted) {
            throw new IllegalArgumentException("Booking kunne ikke afmeldes");
        }
    }

    public void completeBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Ugyldigt booking id");
        }

        boolean completed = bookingRepository.completeBooking(bookingId);

        if (!completed) {
            throw new IllegalArgumentException("Booking kunne ikke markeres som gennemført");
        }
    }
}

