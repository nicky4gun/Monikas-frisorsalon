package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.repositorys.ViewController;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import javafx.fxml.FXML;

public class BookingController implements ViewController {
    private BookingService bookingService;

    @Override
    public void setService(Object bookingService) {
        this.bookingService = (BookingService) bookingService;
    }

}
