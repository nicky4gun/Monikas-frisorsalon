package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(String name, String username, String password) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Please enter your name");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Please enter your username");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Please enter your password");

        return employeeRepository.addEmployee(new Employee(name, username, password));
    }

    public boolean usernameExists(String username) {
        return employeeRepository.usernameExists(username);
    }

    public Employee checkLogin(String username, String password) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Please enter your username");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Please enter your password");

        return employeeRepository.checkLogin(username, password);
    }

    public List<Employee> findAllEmployees() {
       return employeeRepository.findAllEmployees();
    }
}
