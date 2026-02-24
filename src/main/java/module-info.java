module dk.jannikognicklas.monikasfrsiorsalon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens dk.jannikognicklas.monikasfrisorsalon to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon;
    exports dk.jannikognicklas.monikasfrisorsalon.controllers;
    opens dk.jannikognicklas.monikasfrisorsalon.controllers to javafx.fxml;
}