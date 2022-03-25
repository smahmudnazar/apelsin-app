package com.example.apelsinapp.service;

import com.example.apelsinapp.dto.ApiResponse;
import com.example.apelsinapp.dto.TaskDTOs.*;
import com.example.apelsinapp.entity.*;
import com.example.apelsinapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    final InvoiceRepository invoiceRepository;
    final OrdersRepository ordersRepository;
    final CustomerRepository customerRepository;
    final PaymentRepository paymentRepository;
    final ProductRepository productRepository;
    final DetailRepository detailRepository;

    public ApiResponse findAllExpiredInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice : invoiceRepository.findAll()) {
            if (invoice.getIssued().after(invoice.getDue())) invoices.add(invoice);
        }
        return new ApiResponse("Success", true, invoices);
    }

    public ApiResponse findAllWrongDateInvoices() {
        List<WrongDateInvoicesDTO> list = new ArrayList<>();

        for (Invoice invoice : invoiceRepository.findAll()) {
            for (Orders orders : ordersRepository.findAll()) {
                if (invoice.getOrder().equals(orders)) {
                    if (invoice.getIssued().before(orders.getDate())) {
                        WrongDateInvoicesDTO wrongDate = new WrongDateInvoicesDTO();
                        wrongDate.setOrder_date(orders.getDate());
                        wrongDate.setInvoice_id(invoice.getId());
                        wrongDate.setIssued(invoice.getIssued());
                        wrongDate.setOrder_id(orders.getId());

                        list.add(wrongDate);
                    }
                }
            }
        }
        return new ApiResponse("Success", true, list);
    }

    public ApiResponse findAllCustomersWithoutOrders() {
        List<Customer> allCustomersWithoutOrders = customerRepository.findAllCustomersWithoutOrders();
        return new ApiResponse("Succes", true, allCustomersWithoutOrders);
    }

    public ApiResponse getLastOrders() {
            List<Orders> lastCustomerOrders = ordersRepository.getLastCustomerOrders();


            List<Object> collect = lastCustomerOrders.stream().map(this::lastOrders).collect(Collectors.toList());
            return new ApiResponse("Success", true, collect);
        }

    public CustomersLastOrders lastOrders(Orders order) {
        return new CustomersLastOrders(
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getDate()
        );
    }

    public ApiResponse highDemandProducts(){

        List<HighDemandProducts> highDemandProducts=new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            Integer total = quantityProduct(product);
            if (total>=10){
                    HighDemandProducts highDemandProducts1=new HighDemandProducts();
                    highDemandProducts1.setCode(product.getId());
                    highDemandProducts1.setTotal(total);

                    highDemandProducts.add(highDemandProducts1);
            }
        }
        return new ApiResponse("Success",true,highDemandProducts);
    }

    public Integer quantityProduct(Product product){
        List<Detail> allByProductId = detailRepository.findAllByProductId(product.getId());
        int counter = 0;
        for (Detail detail : allByProductId) {
            counter+=detail.getQuantity();
        }
        return counter;
    }

    public ApiResponse ordersWithoutDetails(){
        List<Orders> ordersList = ordersRepository.orders_without_details();
        return new ApiResponse("Success",true,ordersList);
    }

    public Integer avgProductQuantity(Product product){
        List<Detail> allByProductId = detailRepository.findAllByProductId(product.getId());
        int counter = 0;
        int sum=0;
        for (Detail detail : allByProductId) {
            sum+=detail.getQuantity();
            counter++;
        }
        return sum/counter;
    }

    public ApiResponse bulkProducts(){

        List<BulkProducts> bulkProducts=new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            Integer average = avgProductQuantity(product);
            if (average>=8){
               BulkProducts bulkProducts1=new BulkProducts();
               bulkProducts1.setCode(product.getId());
               bulkProducts1.setPrice(product.getPrice());

               bulkProducts.add(bulkProducts1);
            }
        }

        return new ApiResponse("Success",true,bulkProducts);
    }

    public ApiResponse numberOfProductsInYear(){
        List<NumberOfProductsInYear> numberOfProductsInYears = customerRepository.NumberOfProductsInYear();
        return new ApiResponse("Success",true, numberOfProductsInYears);
    }

    public ApiResponse findAllOverpaidInvoices() {
        List<OverpaidInvoices> overpaidInvoices=new ArrayList<>();
        for (Invoice invoice : invoiceRepository.findAll()) {
            double amount = getAmount(invoice);
            if (amount>=invoice.getAmount()){
                OverpaidInvoices invoices=new OverpaidInvoices();
                invoices.setInvoice_id(invoice.getId());
                invoices.setShould_amount(invoice.getAmount());
                invoices.setMore_than_necessary(amount-invoice.getAmount());

                overpaidInvoices.add(invoices);
            }
        }
        return new ApiResponse("Success",true,overpaidInvoices);
    }

    public double getAmount(Invoice invoice){
        double amount=0;
        for (Payment payment : paymentRepository.findAllByInvoiceId(invoice.getId())) {
            amount+=payment.getAmount();
        }
        return amount;
    }

    public ApiResponse ordersWithoutInvoices(){
        List<OrdersWithoutInvoices> ordersWithoutInvoices = ordersRepository.ordersWithoutInvoices();

        return new ApiResponse("Success",true,ordersWithoutInvoices);
    }

}
