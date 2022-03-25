package com.example.apelsinapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {

    private String name;
    private Integer category_id;
    private String description;
    private Double price;
    private String photo;
}
