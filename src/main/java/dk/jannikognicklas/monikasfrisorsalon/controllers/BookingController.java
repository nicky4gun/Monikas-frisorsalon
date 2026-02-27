package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.models.Booking;
import dk.jannikognicklas.monikasfrisorsalon.models.Customer;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.models.HairTreatment;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Status;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import dk.jannikognicklas.monikasfrisorsalon.services.CustomersService;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import dk.jannikognicklas.monikasfrisorsalon.services.TreatmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BookingController implements ViewController<BookingService> {
    private ViewSwitcher viewSwitcher;
    private BookingService bookingService;
    private EmployeeService employeeService;
    private TreatmentService treatmentService;
    private CustomersService customersService;

    private Employee loggedInEmployee;

    @FXML DatePicker datePicker;

    @FXML TextField timeField;
    @FXML TextField nameField;
    @FXML TextField emailField;
    @FXML TextField phoneField;
    @FXML TextArea noteArea;

    @FXML ComboBox<Employee> employeeBox;
    @FXML ComboBox<HairTreatment> treatmentBox;
    @FXML ComboBox<Customer> customerBox;

    @FXML TableView<Booking> bookingView;
    @FXML TableColumn<Booking, Integer> timeCol;
    @FXML TableColumn<Booking, String> nameCol;
    @FXML TableColumn<Booking, String> noteCol;
    @FXML TableColumn<Booking, String> employeeCol;
    @FXML TableColumn<Booking, String> hairTreatmentCol;

    ObservableList<Booking> bookings;
    ObservableList<Employee> employees;
    ObservableList<HairTreatment> treatments;

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
        refreshSelectedDate();
    }

    @Override
    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    private void initBookingTable() {
        bookings = FXCollections.observableArrayList(bookingService.findAllBookings());

        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        hairTreatmentCol.setCellValueFactory(new PropertyValueFactory<>("hairTreatmentId"));
        employeeCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

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
        ObservableList<Customer> customers = FXCollections.observableArrayList(customersService.findAllCustomers());


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

    public void initialize() {
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    protected void onAddBooking() {
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.parse(timeField.getText().trim());

        Employee selectededEmployee = employeeBox.getSelectionModel().getSelectedItem();
        Customer selectedCustomer = customerBox.getSelectionModel().getSelectedItem();
        HairTreatment selectedTreatment = treatmentBox.getSelectionModel().getSelectedItem();
        String note = noteArea.getText().trim();

        bookingService.addBooking(date, time, selectededEmployee.getId(), selectedCustomer.getId(), selectedTreatment.getId(), Status.BOOKED, note);

        refreshSelectedDate();
    }

    @FXML
    protected void onAddCustomer() {
    }

    @FXML
    private void onDateChanged() {
        refreshSelectedDate();
    }

    @FXML
    protected void onCancelSelected() {
        Booking selected = bookingView.getSelectionModel().getSelectedItem();

        try {
            if (selected != null) {
                bookings.remove(selected);
                bookingService.cancelBooking(selected.getId());
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        refreshSelectedDate();
    }

    @FXML
    protected void onCompleteSelected() {
        Booking selected = bookingView.getSelectionModel().getSelectedItem();

        try {
            if (selected != null) {
                bookings.remove(selected);
                bookingService.completeBooking(selected.getId());
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onGoBack() {
        viewSwitcher.goToDashboard(loggedInEmployee);
    }

    @FXML
    protected void onUpdateBooking() {}

    private void refreshSelectedDate() {
        LocalDate date = datePicker.getValue();
        bookings.clear();

        if (date == null) return;

        bookings.addAll(bookingService.getBookingsByDateAndEmployee(date, loggedInEmployee.getId()));
    }
}
