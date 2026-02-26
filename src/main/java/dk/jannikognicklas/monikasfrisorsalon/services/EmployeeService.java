package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
