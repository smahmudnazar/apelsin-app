package com.example.apelsinapp.controller;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.ProductDTO;
import com.example.apelsinapp.entity.Product;
import com.example.apelsinapp.entity.Customer;
import com.example.apelsinapp.entity.Product;
import com.example.apelsinapp.repository.ProductRepository;
import com.example.apelsinapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    final ProductRepository productRepository;
    final ProductService productService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Product> all = productRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Product> byId = productRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Product()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody ProductDTO dto) {
        ApiResponse response = productService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDTO dto) {
        ApiResponse response = productService.edit(id, dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("Not Found");

        Product product = byId.get();
        product.setActive(false);
        productRepository.save(product);
        return ResponseEntity.ok().body("DELETED!");
    }

    @GetMapping("/details/{id}")
    public HttpEntity<?> getByProductId(@PathVariable Integer id) {
        ApiResponse response=productService.getByDetailId(id);

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
