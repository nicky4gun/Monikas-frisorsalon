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

    @FXML TextField nameSignUpField;
    @FXML TextField usernameField;
    @FXML PasswordField passwordField;

    @FXML Label loginMessageLabel;

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
        String name = nameSignUpField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            boolean existingUser = employeeService.usernameExists(username);

            if (existingUser) {
                showSignUpMessage("Username is taken, please try another", Color.RED);
            } else {
                Employee newEmployee = employeeService.addEmployee(name, username, password);
                viewSwitcher.goToDashboard(newEmployee);
            }

        } catch (IllegalArgumentException e) {
            showSignUpMessage("Invalid: " + e.getMessage(), Color.RED);
        }
    }

    private void showSignUpMessage(String message, Color color) {
        loginMessageLabel.setText(message);
        loginMessageLabel.setTextFill(color);
    }
}
