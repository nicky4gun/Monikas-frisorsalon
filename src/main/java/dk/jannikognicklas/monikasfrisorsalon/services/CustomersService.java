package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.Customer;
import dk.jannikognicklas.monikasfrisorsalon.repositories.CustomerRepository;

public class CustomersService {
    private final CustomerRepository customerRepository;
    public CustomersService( CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    public void addCustomer(String name,String email,int phone){
        customerRepository.addCustomer(new Customer(name,email,phone));
    }

    public void UpdateCustomer(String name,String email,int phone){
        customerRepository.UpdateCustomer(new Customer(name,email,phone));
    }
}
