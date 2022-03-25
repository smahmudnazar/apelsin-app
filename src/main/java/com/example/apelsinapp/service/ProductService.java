package com.example.apelsinapp.service;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.ProductDTO;
import com.example.apelsinapp.entity.Category;
import com.example.apelsinapp.entity.Detail;
import com.example.apelsinapp.entity.Product;
import com.example.apelsinapp.repository.CategoryRepository;
import com.example.apelsinapp.repository.DetailRepository;
import com.example.apelsinapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;
    final DetailRepository detailRepository;

    public ApiResponse add(ProductDTO dto) {
        Optional<Category> category = categoryRepository.findById(dto.getCategory_id());
        if (category.isEmpty()) return new ApiResponse("Category Not founded",false);

        Product product=new Product();
        product.setCategory(category.get());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
        product.setPhoto(dto.getPhoto());

        productRepository.save(product);
        return new ApiResponse("Success",true);
    }

    public ApiResponse edit(Integer id, ProductDTO dto) {
        Optional<Category> category = categoryRepository.findById(dto.getCategory_id());
        if (category.isEmpty()) return new ApiResponse("Category Not founded",false);

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) return new ApiResponse("Product Not founded",false);

        Product product = optionalProduct.get();
        product.setCategory(category.get());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
        product.setPhoto(dto.getPhoto());

        productRepository.save(product);
        return new ApiResponse("Success",true);
    }

    public ApiResponse getByDetailId(Integer id) {
        List<Detail> details = detailRepository.findAllByProductId(id);

        return new ApiResponse("Success",true,details);
    }
}
