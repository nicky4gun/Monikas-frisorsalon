package dk.jannikognicklas.monikasfrisorsalon;

import dk.jannikognicklas.monikasfrisorsalon.controllers.LoginController;
import dk.jannikognicklas.monikasfrisorsalon.controllers.ViewSwitcher;
import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.repositories.BookingRepository;
import dk.jannikognicklas.monikasfrisorsalon.repositories.EmployeeRepository;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HairDresserApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // DbConfig setup
        DbConfig config = new DbConfig();

        // Repository setup
        BookingRepository bookingRepository = new BookingRepository(config);
        EmployeeRepository employeeRepository = new EmployeeRepository(config);

        // Service setup
        BookingService bookingService = new BookingService(bookingRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        // ViewSwitcher setup
        ViewSwitcher viewSwitcher = new ViewSwitcher(stage, bookingService, employeeService);

        FXMLLoader fxmlLoader = new FXMLLoader(HairDresserApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 297, 240);
        stage.setTitle("Hårmoni'ka Bookingsystem");

        // Dependency injection
        LoginController lc = fxmlLoader.getController();
        lc.setViewSwitcher(viewSwitcher);
        lc.setService(employeeService);

        stage.setScene(scene);
        stage.show();
    }
}
