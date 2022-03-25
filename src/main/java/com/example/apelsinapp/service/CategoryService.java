package com.example.apelsinapp.service;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.entity.Category;
import com.example.apelsinapp.entity.Product;
import com.example.apelsinapp.repository.CategoryRepository;
import com.example.apelsinapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;

    public ApiResponse add(Category category) {
      categoryRepository.save(category);
    return new ApiResponse("Success",true);
    }

    public ApiResponse edit(Integer id, Category category) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) return new ApiResponse("Not found!", false);

        Category category1 = optionalCategory.get();
        category1.setName(category.getName());
        categoryRepository.save(category1);

        return new ApiResponse("Succes",true);
    }

    public ApiResponse getByProductId(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) return new ApiResponse("Not Found",true);

        Category category = product.get().getCategory();

        return new ApiResponse("Success",true,category);

    }
}
