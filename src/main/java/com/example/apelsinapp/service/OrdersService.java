package com.example.apelsinapp.service;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.DetailAndProductName;
import com.example.apelsinapp.dto.DetailDTO;
import com.example.apelsinapp.dto.OrderDTO;
import com.example.apelsinapp.entity.*;
import com.example.apelsinapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {
    final OrdersRepository ordersRepository;
    final InvoiceRepository invoiceRepository;
    final CustomerRepository customerRepository;
    final ProductRepository productRepository;
    final DetailRepository detailRepository;

    public ApiResponse add(OrderDTO dto) {
        Orders orders=new Orders();
        orders.setDate(Date.valueOf(LocalDate.now()));

        Optional<Customer> customer = customerRepository.findById(dto.getCustomer_id());
        if (customer.isEmpty()) return new ApiResponse("Customer Not Founded!",false);
        orders.setCustomer(customer.get());

        List<Detail> detailList=new ArrayList<>();
        Double forAmount = 0d;
        for (DetailDTO detailDTO : dto.getDetails()) {
            Optional<Product> optionalProduct = productRepository.findById(detailDTO.getProduct_id());
            if (optionalProduct.isEmpty()) return new ApiResponse("Product Not Founded!",false);

            Detail detail=new Detail();
            detail.setOrder(orders);
            detail.setProduct(optionalProduct.get());
            detail.setQuantity(detailDTO.getQuantity());
            forAmount+=detail.getProduct().getPrice()* Double.valueOf(detail.getQuantity());
            detailList.add(detail);
        }
        detailRepository.saveAll(detailList);


        Invoice invoice=new Invoice();
        invoice.setIssued(Date.valueOf(LocalDate.now()));
        invoice.setOrder(orders);
        invoice.setAmount(forAmount);
        invoice.setDue(Date.valueOf(LocalDate.now().plusDays(3)));
        invoiceRepository.save(invoice);

        return new ApiResponse("Success",true);
    }

    public ApiResponse getDetailByOrderId(Integer id) {
        List<Detail> allByOrderId = detailRepository.findAllByOrderId(id);
        List<DetailAndProductName> detailAndProductNames=new ArrayList<>();

        for (Detail detail : allByOrderId) {
            DetailAndProductName detailAndProductName=new DetailAndProductName();
            detailAndProductName.setOrder(detail.getOrder());
            detailAndProductName.setQuantity(detail.getQuantity());
            detailAndProductName.setId(detail.getId());
            detailAndProductName.setProduct_name(detail.getProduct().getName());

            detailAndProductNames.add(detailAndProductName);
        }

        return new ApiResponse("Success",true,detailAndProductNames);
    }
}
