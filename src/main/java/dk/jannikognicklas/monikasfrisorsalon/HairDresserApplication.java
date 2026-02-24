package dk.jannikognicklas.monikasfrisorsalon;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.repositorys.BookingRepository;
import dk.jannikognicklas.monikasfrisorsalon.repositorys.ViewController;
import dk.jannikognicklas.monikasfrisorsalon.services.BookingService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HairDresserApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DbConfig config = new DbConfig();
        BookingRepository bookingRepository = new BookingRepository(config);

        BookingService bookingService = new BookingService(bookingRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(HairDresserApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");

        // Dependency injection goes here
        ViewController controller = fxmlLoader.getController();
        controller.setService(bookingService);

        stage.setScene(scene);
        stage.show();
    }
}
