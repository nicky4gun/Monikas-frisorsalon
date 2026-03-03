package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Customer;
import dk.jannikognicklas.monikasfrisorsalon.repositories.CustomerRepository;

import java.util.List;

public class CustomersService {

    private final CustomerRepository customerRepository;

    public CustomersService( CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    public void addCustomer(String name,String email, int phone){
        Customer customer = new Customer(name, email, phone);
        customerRepository.addCustomer(customer);
    }

    public Customer findCustomerById(int id) {
        return customerRepository.findCustomerById(id);
    }

    public void updateCustomer(String name, String email, int phone){
        Customer customerToUpdate = new Customer(name, email, phone);
        customerRepository.updateCustomer(customerToUpdate);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAllCustomers();
    }
}
