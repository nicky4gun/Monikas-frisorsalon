package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewSwitcher {
    private final Stage stage;
    private final BookingService bookingService;
    private final EmployeeService employeeService;

    public ViewSwitcher(Stage stage, BookingService bookingService, EmployeeService employeeService) {
        this.stage = stage;
        this.bookingService = bookingService;
        this.employeeService = employeeService;
    }

    public void goToLogin() {
        switchTo("login-view.fxml", employeeService);
    }

    public void goToBooking() {
        switchTo("calendar-view.fxml", bookingService);
    }

    public void goToDashboard() {
        switchTo("dashboard-view.fxml", null);
    }

    private <T> void switchTo(String fxml, T service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/jannikognicklas/monikasfrisorsalon/" + fxml));
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
