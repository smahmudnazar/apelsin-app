package com.example.apelsinapp.controller;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.entity.Customer;
import com.example.apelsinapp.repository.CustomerRepository;
import com.example.apelsinapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    final CustomerRepository customerRepository;
    final CustomerService customerService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Customer> all = customerRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Customer()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Customer customer) {
        ApiResponse response = customerService.add(customer);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Customer customer) {
        ApiResponse response = customerService.edit(id, customer);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("Not Found");

        Customer customer = byId.get();
        customer.setActive(false);
        customerRepository.save(customer);
        return ResponseEntity.ok().body("DELETED!");
    }


}
