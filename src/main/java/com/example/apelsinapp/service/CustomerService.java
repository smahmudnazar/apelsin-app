package com.example.apelsinapp.service;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.entity.Customer;
import com.example.apelsinapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    final CustomerRepository customerRepository;

    public ApiResponse add(Customer customer) {
        customerRepository.save(customer);
        return new ApiResponse("Success",true);
    }

    public ApiResponse edit(Integer id, Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) return new ApiResponse("Not found!", false);

        Customer customer1 = optionalCustomer.get();
        customer1.setCountry(customer.getCountry());
        customer1.setName(customer.getName());
        customer1.setPhone(customer.getPhone());
        customer1.setText(customer.getText());

        customerRepository.save(customer1);
        return new ApiResponse("Succes", true);
    }
}
