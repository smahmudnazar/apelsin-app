package com.example.apelsinapp.dto.TaskDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverpaidInvoices {
    private Integer invoice_id;
    private Double should_amount;
    private Double more_than_necessary;
}
