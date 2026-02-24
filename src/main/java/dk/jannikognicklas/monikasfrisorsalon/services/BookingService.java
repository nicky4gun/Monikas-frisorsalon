package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.model.Booking;
import dk.jannikognicklas.monikasfrisorsalon.model.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.repositorys.BookingRepository;

import java.time.LocalDateTime;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void addBooking(LocalDateTime startTime, LocalDateTime endTime, int employeeId, int customerId, int hairTreatmentId, Status status) {
        bookingRepository.addBooking(new Booking( startTime, endTime,  employeeId,  customerId,  hairTreatmentId, status));
    }

    public void findBookingById(int bookingId) {
        bookingRepository.findBookingById(bookingId);
    }

    public void findBookingByEmployeeId(int employeeId) {bookingRepository.findBookingByEmployeeId(employeeId);}

    public  void updateBooking(LocalDateTime startTime, LocalDateTime endTime, int employeeId, int customerId, int hairTreatmentId, Status status) {
        bookingRepository.updateBooking(new Booking(startTime, endTime,  employeeId,  customerId,  hairTreatmentId, status));
    }

    public void cancelBooking(int bookingId) {
        boolean deleted = bookingRepository.cancelBooking(bookingId);
    }

    public void completeBooking(int bookingId) {
        boolean deleted = bookingRepository.completeBooking(bookingId);
    }
}

