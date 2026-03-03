package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.controllers.navigation.ViewController;
import dk.jannikognicklas.monikasfrisorsalon.controllers.navigation.ViewSwitcher;
import dk.jannikognicklas.monikasfrisorsalon.models.*;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import dk.jannikognicklas.monikasfrisorsalon.services.CustomersService;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import dk.jannikognicklas.monikasfrisorsalon.services.TreatmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class BookingController implements ViewController<BookingService> {
    private ViewSwitcher viewSwitcher;
    private BookingService bookingService;
    private EmployeeService employeeService;
    private TreatmentService treatmentService;
    private CustomersService customersService;

    private Employee loggedInEmployee;

    @FXML private DatePicker datePicker;

    @FXML private TextField timeField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField searchField;
    @FXML private TextArea noteArea;

    @FXML private ComboBox<Employee> employeeBox;
    @FXML private ComboBox<HairTreatment> treatmentBox;
    @FXML private ComboBox<Customer> customerBox;

    @FXML private TableView<BookingView> bookingView;
    @FXML private TableColumn<BookingView, String> timeCol;
    @FXML private TableColumn<BookingView, String> nameCol;
    @FXML private TableColumn<BookingView, String> emailCol;
    @FXML private TableColumn<BookingView, String> phoneCol;
    @FXML private TableColumn<BookingView, String> employeeCol;
    @FXML private TableColumn<BookingView, String> hairTreatmentCol;
    @FXML private TableColumn<BookingView, String> noteCol;
    @FXML private TableColumn<BookingView, String> statusCol;

    private ObservableList<BookingView> bookings = FXCollections.observableArrayList();
    private ObservableList<Employee> employees;
    private ObservableList<HairTreatment> treatments;
    private ObservableList<Customer> customers;

    @Override
    public void setService(BookingService bookingService) {
        this.bookingService = bookingService;

        initBookingTable();
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
        initEmployeeList();
    }

    public void setTreatmentService(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
        initTreatmentList();
    }

    public void setCustomersService(CustomersService customersService) {
        this.customersService = customersService;
        initCustomerList();
    }

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
        employeeBox.getSelectionModel().select(loggedInEmployee);
        refreshSelectedDate();
    }

    @Override
    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    public void initialize() {
        datePicker.setValue(LocalDate.now());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
             bookingView.setItems( bookingService.searchBookings(newValue));});
    }

    // ------ Init helpers ------
    private void initBookingTable() {
        timeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTime().toString()));
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCustomerName()));
        emailCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCustomerEmail()));
        phoneCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cell.getValue().getPhoneNumber())));
        hairTreatmentCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTreatment().toString()));
        employeeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getEmployeeName()));
        noteCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNote()));
        statusCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getStatus().toString()));

        bookingView.setItems(bookings);
    }

    private void initEmployeeList() {
        employees = FXCollections.observableArrayList(employeeService.findAllEmployees());

        if (employeeBox != null) {
            employeeBox.setItems(employees);
        } else {
            System.out.println("No employees found");
        }
    }

    private void initCustomerList() {
        customers = FXCollections.observableArrayList(customersService.findAllCustomers());


        if (customerBox != null) {
            customerBox.setItems(customers);
        } else {
            System.out.println("No customers found");
        }
    }

    private void initTreatmentList() {
        treatments = FXCollections.observableArrayList(treatmentService.findAllTreatments());

        if (treatmentBox != null) {
            treatmentBox.setItems(treatments);
        } else {
            System.out.println("No treatments found");
        }
    }

    // ------ Event Handlers ------
    @FXML
    protected void onAddBooking() {
        LocalDate date = datePicker.getValue();
        LocalTime time;

        try {
            time = LocalTime.parse(timeField.getText().trim());
        } catch (DateTimeParseException e) {
            showAlert("Fejl", "Indtast tid i formatet HH:mm, f.eks. 16:00", Alert.AlertType.ERROR);
            return;
        }

        Employee selectedEmployee = employeeBox.getSelectionModel().getSelectedItem();
        Customer selectedCustomer = customerBox.getSelectionModel().getSelectedItem();
        HairTreatment selectedTreatment = treatmentBox.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null || selectedTreatment == null || selectedEmployee == null) {
            showAlert("Fejl", "Vælg venligst frisør, kunde og behandling", Alert.AlertType.ERROR);
        }

        String note = noteArea.getText().trim();

        try {
            bookingService.addBooking(date, time, selectedEmployee.getId(), selectedCustomer.getId(), selectedTreatment.getId(), Status.BOOKED, note);
            showAlert("Success", "Booking blev tilføjet", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Fejl", e.getMessage(), Alert.AlertType.ERROR);
        }

        refreshSelectedDate();
    }

    @FXML
    private void onDateChanged() {
        refreshSelectedDate();
    }

    @FXML
    private void onEmployeeChanged() {
        refreshSelectedDate();
    }

    @FXML
    protected void onCancelSelected() {
        BookingView selected = bookingView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            bookings.remove(selected);
            bookingService.cancelBooking(selected.getId());
            showAlert("Success", "Booking blev afmeldt", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Fejl", e.getMessage(), Alert.AlertType.ERROR);
        }

        refreshSelectedDate();
    }

    @FXML
    protected void onGoBack() {
        viewSwitcher.goToDashboard(loggedInEmployee);
    }

    @FXML
    protected void onUpdateBooking() {
        BookingView selected = bookingView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.parse(timeField.getText().trim());
        Employee employee = employeeBox.getSelectionModel().getSelectedItem();
        HairTreatment treatment = treatmentBox.getSelectionModel().getSelectedItem();
        Customer customer = customerBox.getSelectionModel().getSelectedItem();
        Status status = selected.getStatus();
        String note = noteArea.getText();

        try {
            bookingService.updateBooking(selected.getId(), date, time, employee.getId(), customer.getId(), treatment.getId(), status, note);
            showAlert("Success", "Booking blev opdateret", Alert.AlertType.INFORMATION);
            refreshSelectedDate();
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void onEditBooking() {
        BookingView selected = bookingView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            timeField.setText(selected.getTime().toString());
            employeeBox.setValue(employeeService.findEmployeeById(selected.getEmployeeId()));
            treatmentBox.setValue(treatmentService.findHairTreatmentById(selected.getTreatmentId()));
            customerBox.setValue(customersService.findCustomerById(selected.getCustomerId()));
            noteArea.setText(selected.getNote());
        }
    }

    @FXML
    protected void onCreateCustomer() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        int phoneNumber = Integer.parseInt(phoneField.getText().trim());

        boolean existingEmail = customersService.emailExists(email);

        if (existingEmail) {
            showAlert("Fejl", "Email allerede brugt", Alert.AlertType.ERROR);
            return;
        }

        try {
            Customer customer = customersService.addCustomer(name, email, phoneNumber);
            customers.add(customer);
            showAlert("Sucess", "Kunde blev oprettet", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

        nameField.clear();
        emailField.clear();
        phoneField.clear();

        refreshSelectedDate();
    }

    // ------ Helpers ------
    private void refreshSelectedDate() {
        LocalDate date = datePicker.getValue();
        bookings.clear();

        if (date == null) return;

        Employee selected = employeeBox.getSelectionModel().getSelectedItem();
        Employee employee;

        if (selected != null) {
            employee = selected;
        } else {
            employee = loggedInEmployee;
        }

        bookings.addAll(bookingService.getBookingsByDateAndEmployee(date, employee.getId()));
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert  = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
