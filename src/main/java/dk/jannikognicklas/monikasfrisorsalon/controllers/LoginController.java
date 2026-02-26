package dk.jannikognicklas.monikasfrisorsalon.controllers;

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

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;

    @FXML Label loginMessageLabel;

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
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Employee loggedInEmployee = employeeService.checkLogin(username, password);

            if (loggedInEmployee != null) {
                viewSwitcher.goToDashboard(loggedInEmployee);

            } else {
                showLoginMessage("Wrong username or password", Color.RED);
            }

        } catch (IllegalArgumentException e) {
            showLoginMessage("Invalid: " + e.getMessage(), Color.BLACK);
        }
    }

    private void showLoginMessage(String message, Color color) {
        loginMessageLabel.setText(message);
        loginMessageLabel.setTextFill(color);
    }
}
