package com.example.apelsinapp.service;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.PaymentDTO;
import com.example.apelsinapp.dto.TaskDTOs.DetailByPaymentId;
import com.example.apelsinapp.entity.Invoice;
import com.example.apelsinapp.entity.Payment;
import com.example.apelsinapp.repository.DetailRepository;
import com.example.apelsinapp.repository.InvoiceRepository;
import com.example.apelsinapp.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    final PaymentRepository paymentRepository;
    final InvoiceRepository invoiceRepository;
    final DetailRepository detailRepository;

    public ApiResponse add(PaymentDTO dto) {
        Optional<Invoice> invoice = invoiceRepository.findById(dto.getInvoice_id());
        if (invoice.isEmpty()) return new ApiResponse("Invoive Not Founded",false);

        Payment payment=new Payment();
        payment.setAmount(dto.getAmount());
        payment.setInvoice(invoice.get());

        paymentRepository.save(payment);
        return new ApiResponse("Success",true);
    }

    public ApiResponse getDetailByPaymentId(Integer id){
        List<DetailByPaymentId> byPaymentId = detailRepository.findByPaymentId(id);

        return new ApiResponse("Succes",true,byPaymentId);
    }
}
