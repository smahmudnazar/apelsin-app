package com.example.apelsinapp.controller;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.OrderDTO;
import com.example.apelsinapp.entity.Orders;
import com.example.apelsinapp.repository.OrdersRepository;
import com.example.apelsinapp.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    final OrdersRepository ordersRepository;
    final OrdersService ordersService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Orders> all = ordersRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Orders> byId = ordersRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Orders()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody OrderDTO dto) {
        ApiResponse response = ordersService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Orders> byId = ordersRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("Not Found");

        Orders orders = byId.get();
        orders.setActive(false);
        ordersRepository.save(orders);
        return ResponseEntity.ok().body("DELETED!");
    }

    @GetMapping("/details/{id}")
    public HttpEntity<?> getDetail(@PathVariable Integer id) {
       ApiResponse response= ordersService.getDetailByOrderId(id);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);

    }
}
