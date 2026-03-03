package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.BookingView;
import dk.jannikognicklas.monikasfrisorsalon.models.Customer;
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
        Booking booking = new Booking(date, time, employeeId, customerId, hairTreatmentId, status, note);
        bookingRepository.addBooking(booking);
    }

   // public List<Booking> findAllBookings() {
     //   return bookingRepository.findAllBookings();;
    //}

    public Booking findBookingById(int bookingId) {
        return bookingRepository.findBookingById(bookingId);
    }

    public List<BookingView> getBookingsByDateAndEmployee(LocalDate date, int employeeId) {
        return bookingRepository.findBookingsByDateAndEmployee(date, employeeId);
    }

    public  void updateBooking(int id, LocalDate date, LocalTime time, int employeeId, int customerId, int hairTreatmentId, Status status, String note) {
        Booking bookingToUpdate = new Booking(id, date, time, employeeId, customerId, hairTreatmentId, status, note);
        bookingRepository.updateBooking(bookingToUpdate);
    }

    public ObservableList<BookingView> searchBookings(String keywords){
        return bookingRepository.customerSearch(keywords);
    }


    public void cancelBooking(int bookingId) {
        boolean deleted = bookingRepository.cancelBooking(bookingId);
    }

    public void completeBooking(int bookingId) {
        boolean deleted = bookingRepository.completeBooking(bookingId);
    }
}

