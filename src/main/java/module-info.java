module dk.jannikognicklas.monikasfrsiorsalon {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.jannikognicklas.monikasfrisorsalon to javafx.fxml;
    exports dk.jannikognicklas.monikasfrisorsalon;
}