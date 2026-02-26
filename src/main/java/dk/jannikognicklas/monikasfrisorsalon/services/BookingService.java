package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.repositories.BookingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void addBooking(LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status,String note) {
        bookingRepository.addBooking(new Booking( date, time,  employeeId,  customerId,  hairTreatmentId, status,note));
    }

    public List<Booking> findAllBookings() {
        return bookingRepository.findAllBookings();
    }

    public Booking findBookingById(int bookingId) {
        return bookingRepository.findBookingById(bookingId);
    }

    public List<Booking> getBookingsByDateAndEmployee(LocalDate date, int employeeId) {
        return bookingRepository.findBookingsByDateAndEmployee(date, employeeId);
    }

    public  void updateBooking(LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status,String note) {
        bookingRepository.updateBooking(new Booking(date, time,  employeeId,  customerId,  hairTreatmentId, status,note));
    }

    public void cancelBooking(int bookingId) {
        boolean deleted = bookingRepository.cancelBooking(bookingId);
    }

    public void completeBooking(int bookingId) {
        boolean deleted = bookingRepository.completeBooking(bookingId);
    }
}

