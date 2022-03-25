package com.example.apelsinapp.controller;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.entity.Category;
import com.example.apelsinapp.repository.CategoryRepository;
import com.example.apelsinapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    final CategoryRepository categoryRepository;
    final CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> getAll() {
        List<Category> all = categoryRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);

        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Category()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Category category) {
        ApiResponse response = categoryService.add(category);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody Category category) {
        ApiResponse response = categoryService.edit(id, category);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("Not Found");

        Category category = byId.get();
        category.setActive(false);
        categoryRepository.save(category);
        return ResponseEntity.ok().body("DELETED!");
    }

    @GetMapping("/product_id/{id}")
    public HttpEntity<?> getByProductId(@PathVariable Integer id) {
        ApiResponse response=categoryService.getByProductId(id);

        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
