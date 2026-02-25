package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import javafx.fxml.FXML;

public class DashboardController implements ViewController<BookingService> {
    private ViewSwitcher viewSwitcher;
    private BookingService bookingService;

    @Override
    public void setService(BookingService bookingservice) {
        this.bookingService = bookingService;
    }

    @Override
    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    @FXML
    protected void onLogoutButtonPress() {
        viewSwitcher.goToLogin();
    }

    @FXML
    protected void onCreateBookingButtonPress() {
        viewSwitcher.goToBooking();
    }
}
