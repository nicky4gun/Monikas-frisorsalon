package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.models.HairTreatment;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BookingController implements ViewController<BookingService> {
    private ViewSwitcher viewSwitcher;
    private BookingService bookingService;

    @FXML DatePicker datePicker;

    @FXML TextField timeField;
    @FXML TextField nameField;
    @FXML TextField emailField;
    @FXML TextField phoneField;
    @FXML TextField noteArea;

    @FXML ComboBox<Employee> employeeBox;
    @FXML ComboBox<HairTreatment> treatmentBox;

    @FXML TableView<Booking> bookingView;
    @FXML TableColumn<Booking, Integer> timeCol;
    @FXML TableColumn<Booking, String> nameCol;
    @FXML TableColumn<Booking, String> noteCol;


    @Override
    public void setService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    @FXML
    protected void onAddBooking() {}

    @FXML
    protected void onAddCustomer() {}

    @FXML
    protected void onDateChanged() {}

    @FXML
    protected void onDeleteSelected() {}
}
