package dk.jannikognicklas.monikasfrisorsalon.controllers;

import dk.jannikognicklas.monikasfrisorsalon.controllers.navigation.ViewController;
import dk.jannikognicklas.monikasfrisorsalon.controllers.navigation.ViewSwitcher;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SignUpController implements ViewController<EmployeeService> {
    private ViewSwitcher viewSwitcher;
    private EmployeeService employeeService;

    @FXML private TextField nameSignUpField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private Label loginMessageLabel;

    @Override
    public void setService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    @FXML
    protected void onSignUpButtonPress() {
        String name = nameSignUpField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showSignUpMessage("Angiv venligst både navn, brugernavn & adgangskode", Color.RED);
            return;
        }

        try {
            boolean existingUser = employeeService.usernameExists(username);

            if (existingUser) {
                showSignUpMessage("Brugernavn er taget, prøv et andet", Color.RED);
                return;
            }

            Employee newEmployee = employeeService.addEmployee(name, username, password);
            viewSwitcher.goToDashboard(newEmployee);

        } catch (IllegalArgumentException e) {
            showSignUpMessage("Invalid: " + e.getMessage(), Color.RED);
        }
    }

    private void showSignUpMessage(String message, Color color) {
        loginMessageLabel.setText(message);
        loginMessageLabel.setTextFill(color);
    }
}
