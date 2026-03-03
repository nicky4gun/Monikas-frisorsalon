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

public class LoginController implements ViewController<EmployeeService> {
    private ViewSwitcher viewSwitcher;
    private EmployeeService employeeService;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private Label loginMessageLabel;

    @Override
    public void setService(EmployeeService service) {
        this.employeeService = service;
    }

    @Override
    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    @FXML
    protected void onLoginButtonPress() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showLoginMessage("Angiv venligst både brugernavn & adgangskode", Color.RED);
            return;
        }

        try {
            Employee loggedInEmployee = employeeService.checkLogin(username, password);

            if (loggedInEmployee == null) {
                showLoginMessage("Forkert brugernavn eller adgangskode", Color.RED);
            } else {
                viewSwitcher.goToDashboard(loggedInEmployee);
            }

        } catch (IllegalArgumentException e) {
            showLoginMessage("Ugyldigt: " + e.getMessage(), Color.BLACK);
        }
    }

    @FXML
    protected void onSignUpButtonPress() {
        viewSwitcher.goToSignUp();
    }

    private void showLoginMessage(String message, Color color) {
        loginMessageLabel.setText(message);
        loginMessageLabel.setTextFill(color);
    }
}
