package com.example.apelsinapp.dto.TaskDTOs;

import java.sql.Date;

public interface OrdersWithoutInvoices {
    Integer getId();
    Date getDate();
    Integer getQuantity();
    Integer getPrice();

}
