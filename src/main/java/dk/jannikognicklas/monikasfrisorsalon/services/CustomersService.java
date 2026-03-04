package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Customer;
import dk.jannikognicklas.monikasfrisorsalon.repositories.CustomerRepository;

import java.util.List;

public class CustomersService {

    private final CustomerRepository customerRepository;

    public CustomersService( CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    public Customer addCustomer(String name, String email, int phone){
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Angiv venligst kundens name");
        if (email == null ||  email.isEmpty()) throw new IllegalArgumentException("Angiv venligst kundens email");
        if (phone <= 0) throw new IllegalArgumentException("Ugyldigt telefonnummer");

        Customer customer = new Customer(name, email, phone);
        customerRepository.addCustomer(customer);

        return customer;
    }

    public boolean emailExists(String email) {
        return customerRepository.emailExists(email);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAllCustomers();
    }

    public Customer findCustomerById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ugyldigt kunde id");
        }

        Customer customer = customerRepository.findCustomerById(id);

        if (customer == null) {
            throw new IllegalArgumentException("Ingen kunde fundet");
        }

        return customer;
    }

    public void updateCustomer(int id, String name, String email, int phone){
        if (id <= 0) throw new IllegalArgumentException("Ugyldigt kunde id");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Angiv venligst kundens name");
        if (email == null ||  email.isEmpty()) throw new IllegalArgumentException("Angiv venligst kundens email");
        if (phone <= 0) throw new IllegalArgumentException("Ugyldigt telefonnummer");

        Customer customerToUpdate = new Customer(id, name, email, phone);
        customerRepository.updateCustomer(customerToUpdate);
    }
}
