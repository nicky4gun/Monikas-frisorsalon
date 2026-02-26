package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.HairDresserApplication;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import dk.jannikognicklas.monikasfrisorsalon.services.CustomersService;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import dk.jannikognicklas.monikasfrisorsalon.services.TreatmentService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewSwitcher {
    private final Stage stage;
    private final BookingService bookingService;
    private final EmployeeService employeeService;
    private final TreatmentService treatmentService;
    private final CustomersService customersService;

    public ViewSwitcher(Stage stage, BookingService bookingService, EmployeeService employeeService, TreatmentService treatmentService, CustomersService customersService) {
        this.stage = stage;
        this.bookingService = bookingService;
        this.employeeService = employeeService;
        this.treatmentService = treatmentService;
        this.customersService = customersService;
    }

    public void goToLogin() {
        switchTo("login-view.fxml", employeeService);
    }

    public void goToBooking(Employee loggedInEmployee) {
        try {
            FXMLLoader loader = new FXMLLoader(HairDresserApplication.class.getResource("views/calendar-view.fxml"));
            Parent view = loader.load();

            BookingController controller = loader.getController();
            controller.setService(bookingService);
            controller.setEmployeeService(employeeService);
            controller.setTreatmentService(treatmentService);
            controller.setCustomersService(customersService);
            controller.setLoggedInEmployee(loggedInEmployee);
            controller.setViewSwitcher(this);

            stage.setScene(new Scene(view));
            stage.sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToDashboard(Employee loggedInEmployee) {
        try {
            FXMLLoader loader = new FXMLLoader(HairDresserApplication.class.getResource("views/dashboard-view.fxml"));
            Parent view = loader.load();

            DashboardController controller = loader.getController();
            controller.setService(bookingService);
            controller.setLoggedInEmployee(loggedInEmployee);
            controller.setViewSwitcher(this);

            stage.setScene(new Scene(view));
            stage.sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void switchTo(String fxml, T service) {
        try {
            FXMLLoader loader = new FXMLLoader(HairDresserApplication.class.getResource("views/" + fxml));
            Parent view = loader.load();

            ViewController<T> controller = loader.getController();
            controller.setService(service);
            controller.setViewSwitcher(this);

            stage.setScene(new Scene(view));
            stage.sizeToScene();

        } catch (IOException e) {
            System.out.println("Couldn't switch to: " + fxml);
            e.printStackTrace();
        }
    }
}
