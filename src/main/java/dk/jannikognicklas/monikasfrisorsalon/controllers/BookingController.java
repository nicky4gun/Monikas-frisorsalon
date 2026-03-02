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
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @FXML TableView<BookingView> bookingView;
    @FXML TableColumn<BookingView, String> timeCol;
    @FXML TableColumn<BookingView, String> nameCol;
    @FXML TableColumn<BookingView, String> employeeCol;
    @FXML TableColumn<BookingView, String> hairTreatmentCol;
    @FXML TableColumn<BookingView, String> noteCol;
    @FXML TableColumn<BookingView, String> statusCol;

    ObservableList<BookingView> bookings;
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
        bookings = FXCollections.observableArrayList();

        timeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTime().toString()));
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCustomerName()));
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
    private void onDateChanged() {
        refreshSelectedDate();
    }

    @FXML
    protected void onCancelSelected() {
        BookingView selected = bookingView.getSelectionModel().getSelectedItem();

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
        BookingView selected = bookingView.getSelectionModel().getSelectedItem();

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
    protected void onUpdateBooking() {
        BookingView selected = bookingView.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        try {
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText().trim());
            Employee employee = employeeBox.getSelectionModel().getSelectedItem();
            HairTreatment treatment = treatmentBox.getSelectionModel().getSelectedItem();
            Customer customer = customerBox.getSelectionModel().getSelectedItem();
            Status status = selected.getStatus();
            String note = noteArea.getText();

            bookingService.updateBooking(selected.getId(), date, time, employee.getId(), customer.getId(), treatment.getId(), status, note);
            refreshSelectedDate();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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
    protected void onCreateCustomer() {}

    private void refreshSelectedDate() {
        LocalDate date = datePicker.getValue();
        bookings.clear();

        if (date == null) return;

        bookings.addAll(bookingService.getBookingsByDateAndEmployee(date, loggedInEmployee.getId()));
    }
}
