package com.example.apelsinapp.controller;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.PaymentDTO;
import com.example.apelsinapp.entity.Payment;
import com.example.apelsinapp.repository.PaymentRepository;
import com.example.apelsinapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    final PaymentRepository paymentRepository;
    final PaymentService paymentService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Payment> all = paymentRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Payment()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody PaymentDTO dto) {
        ApiResponse response = paymentService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("Not Found");

        Payment payment = byId.get();
        payment.setActive(false);
        paymentRepository.save(payment);
        return ResponseEntity.ok().body("DELETED!");
    }

    @GetMapping("/detail/{id}")
    public HttpEntity<?> getDetailByPaymentId(@PathVariable Integer id) {
        ApiResponse response = paymentService.getDetailByPaymentId(id);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


}
