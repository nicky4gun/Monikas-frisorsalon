package dk.jannikognicklas.monikasfrisorsalon;

import dk.jannikognicklas.monikasfrisorsalon.controllers.LoginController;
import dk.jannikognicklas.monikasfrisorsalon.controllers.ViewSwitcher;
import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.repositories.BookingRepository;
import dk.jannikognicklas.monikasfrisorsalon.repositories.CustomerRepository;
import dk.jannikognicklas.monikasfrisorsalon.repositories.EmployeeRepository;
import dk.jannikognicklas.monikasfrisorsalon.repositories.TreatmentRepository;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import dk.jannikognicklas.monikasfrisorsalon.services.CustomersService;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import dk.jannikognicklas.monikasfrisorsalon.services.TreatmentService;
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
        TreatmentRepository treatmentRepository = new TreatmentRepository(config);
        CustomerRepository customerRepository = new CustomerRepository(config);

        // Service setup
        BookingService bookingService = new BookingService(bookingRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        TreatmentService treatmentService = new TreatmentService(treatmentRepository);
        CustomersService customersService = new CustomersService(customerRepository);

        // ViewSwitcher setup
        ViewSwitcher viewSwitcher = new ViewSwitcher(stage, bookingService, employeeService, treatmentService, customersService);

        FXMLLoader fxmlLoader = new FXMLLoader(HairDresserApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 297, 240);
        stage.setTitle("Hårmoni'ka Bookingsystem");

        // Dependency injection on startup
        LoginController lc = fxmlLoader.getController();
        lc.setViewSwitcher(viewSwitcher);
        lc.setService(employeeService);

        stage.setScene(scene);
        stage.show();
    }
}
