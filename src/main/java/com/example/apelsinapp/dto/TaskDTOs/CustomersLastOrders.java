package com.example.apelsinapp.dto.TaskDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomersLastOrders {
    private Integer id;
    private String name;
    private Date date;
}
