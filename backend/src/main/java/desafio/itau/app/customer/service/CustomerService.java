package desafio.itau.app.customer.service;

import desafio.itau.app.customer.dto.CustomerCreateDTO;
import desafio.itau.app.customer.dto.CustomerDTO;
import desafio.itau.app.customer.dto.CustomerUpdateDTO;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.app.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        return optionalCustomer.flatMap(customer -> Optional.of(convertToDTO(customer)));
    }

    public CustomerDTO createCustomer(CustomerCreateDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    public CustomerDTO updateCustomer(UUID customerId, CustomerUpdateDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(customerDTO.getName());
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return convertToDTO(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setName(customer.getName());
        return customerDTO;
    }

    private Customer convertToEntity(CustomerCreateDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        return customer;
    }
}