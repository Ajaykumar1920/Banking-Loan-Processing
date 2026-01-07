package com.bank.customer.service;

import org.springframework.stereotype.Service;

import com.bank.customer.dto.CustomerDto;
import com.bank.customer.entity.CustomerModel;
import com.bank.customer.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    

    public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<CustomerDto> getAllCustomers() {
        List<CustomerModel> customers = customerRepository.findAll();
        return customers.stream()
                        .map(this::convertToDto)  // Convert to DTO with calculated age
                        .toList();
    }

    // Get customer by ID
    public CustomerDto getCustomerById(Long id) {
        CustomerModel cust = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Call calculateAge() method here
        int age = cust.calculateAge();  // Calculate age dynamically

        // Pass the calculated age to the DTO constructor
        return new CustomerDto(cust.getCustomerName(), cust.getDateOfBirth(), age, cust.getGender(),
                cust.getMobileNumber(), cust.getEmail(), cust.getAddress(), cust.getMaritalStatus(),
                cust.getNationality(), cust.getBankAccountNumber(), cust.getIfscCode());
    }

    // Create new customer
    public CustomerDto createCustomer(CustomerModel customer) {
        CustomerModel savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);  // Convert to DTO with calculated age
    }

    // Update existing customer
    public CustomerDto updateCustomer(Long id, CustomerModel customer) {
        CustomerModel existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer Not Found."));
        
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setMobileNumber(customer.getMobileNumber());
        existingCustomer.setGender(customer.getGender());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setMaritalStatus(customer.getMaritalStatus());
        existingCustomer.setNationality(customer.getNationality());
        existingCustomer.setBankAccountNumber(customer.getBankAccountNumber());
        existingCustomer.setIfscCode(customer.getIfscCode());
        
        CustomerModel updatedCustomer = customerRepository.save(existingCustomer);

        // Calculate age dynamically here as well
        int age = updatedCustomer.calculateAge();  // Get age from the entity

        // Return the DTO with the updated age
        return new CustomerDto(updatedCustomer.getCustomerName(), updatedCustomer.getDateOfBirth(),
                age, updatedCustomer.getGender(), updatedCustomer.getMobileNumber(), updatedCustomer.getEmail(),
                updatedCustomer.getAddress(), updatedCustomer.getMaritalStatus(), updatedCustomer.getNationality(),
                updatedCustomer.getBankAccountNumber(), updatedCustomer.getIfscCode());
    }

    // Convert entity to DTO
    private CustomerDto convertToDto(CustomerModel customer) {
        // Calculate age based on the dateOfBirth
        int age = customer.calculateAge();  // Get age dynamically from the entity

        // Return the DTO with the calculated age
        return new CustomerDto(
                customer.getCustomerName(),
                customer.getDateOfBirth(),
                age,  // Use the dynamically calculated age
                customer.getGender(),
                customer.getMobileNumber(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getMaritalStatus(),
                customer.getNationality(),
                customer.getBankAccountNumber(),
                customer.getIfscCode()
        );
    }

    // Convert DTO to entity
    private CustomerModel convertToEntity(CustomerDto customerDTO) {
        CustomerModel customer = new CustomerModel();
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setGender(customerDTO.getGender());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setMaritalStatus(customerDTO.getMaritalStatus());
        customer.setNationality(customerDTO.getNationality());
        customer.setBankAccountNumber(customerDTO.getBankAccountNumber());
        customer.setIfscCode(customerDTO.getIfscCode());
        return customer;
    }
}

