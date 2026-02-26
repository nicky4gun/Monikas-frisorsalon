module dk.jannikognicklas.monikasfrsiorsalon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens dk.jannikognicklas.monikasfrisorsalon to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon;

    opens dk.jannikognicklas.monikasfrisorsalon.controllers to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon.controllers;

    opens dk.jannikognicklas.monikasfrisorsalon.services to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon.services;

    opens dk.jannikognicklas.monikasfrisorsalon.repositories to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon.repositories;

    opens dk.jannikognicklas.monikasfrisorsalon.models to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon.models;
}