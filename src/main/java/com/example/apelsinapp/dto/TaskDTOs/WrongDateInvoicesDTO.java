package com.example.apelsinapp.dto.TaskDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WrongDateInvoicesDTO {
    private Integer invoice_id,order_id;
    private Date issued,order_date;
}
