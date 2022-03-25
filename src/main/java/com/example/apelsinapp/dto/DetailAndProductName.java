package com.example.apelsinapp.dto;

import com.example.apelsinapp.entity.Orders;
import com.example.apelsinapp.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailAndProductName {
    private Integer id;
    private Orders order;
    private String product_name;
    private Integer quantity;

}
