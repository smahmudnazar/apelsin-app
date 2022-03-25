package com.example.apelsinapp.controller;

import com.example.apelsinapp.repository.CustomerRepository;
import com.example.apelsinapp.repository.InvoiceRepository;
import com.example.apelsinapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    final InvoiceRepository invoiceRepository;
    final TaskService taskService;
    final CustomerRepository customerRepository;

    //    1.Invoices issued after their due date. Return all attributes.
    @GetMapping("/expired_invoices")
    public HttpEntity<?> expired_invoices() {
        return ResponseEntity.ok().body(taskService.findAllExpiredInvoices());
    }

    //    2.Invoices that were issued before the date in which the order they refer to was placed.
//    Return the ID of the invoice, the date it was issued, the ID of the order associated with it
//    and the date the order was placed.
    @GetMapping("/wrong_date_invoices")
    public HttpEntity<?> wrong_date_invoices() {
        return ResponseEntity.ok(taskService.findAllWrongDateInvoices());
    }

    //    3. Orders that do not have a detail and were placed before 6 September 2016. Return all
//    attributes.
    @GetMapping("/orders_without_details")
    public HttpEntity<?> orders_without_details() {
        return ResponseEntity.ok(taskService.ordersWithoutDetails());
    }


    //  4.Customers who have not placed any orders in 2016. Return all attributes.
    @GetMapping("/customers_without_orders")
    public HttpEntity<?> customers_without_orders() {
        return ResponseEntity.ok(taskService.findAllCustomersWithoutOrders());
    }

    //    5.ID and name of customers and the date of their last order. For customers who did not
//    place any orders, no rows must be returned. For each customer who placed more than
//    one order on the date of their most recent order, only one row must be returned.
    @GetMapping("/customers_last_order")
    public HttpEntity<?> customers_last_order() {
        return ResponseEntity.ok(taskService.getLastOrders());
    }

    //    6.Invoices that have been overpaid. Observe that there may be more than one payment
//    referring to the same invoice. Return the invoice number and the amount that should be
//    reimbursed.
    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> overpaid_invoicess() {
        return ResponseEntity.ok(taskService.findAllOverpaidInvoices());
    }


    //    7.Products that were ordered more than 10 times in total, by taking into account the
//    quantities in which they appear in the order details. Return the product code and the
//    total number of times it was ordered.
    @GetMapping("/high_demand_products")
    public HttpEntity<?> high_demand_products() {
        return ResponseEntity.ok(taskService.highDemandProducts());
    }

    //    8.Products that are usually ordered in bulk: whenever one of these products is ordered, it
//    is ordered in a quantity that on average is equal to or greater than 8. For each such
//    product, return product code and price.
    @GetMapping("/bulk_products")
    public HttpEntity<?> bulk_products() {
        return ResponseEntity.ok(taskService.bulkProducts());
    }


    //    9.Total number of orders placed in 2016 by customers of each country. If all customers
//    from a specific country did not place any orders in 2016, the country will not appear in
//    the output.
    @GetMapping("/number_of_products_in_year")
    public HttpEntity<?> number_of_products_in_year() {
        return ResponseEntity.ok(taskService.numberOfProductsInYear());
    }

    //   10.For each order without invoice, list its ID, the date it was placed and the total price of the
//   products in its detail, taking into account the quantity of each ordered product and its unit
//   price. Orders without detail must not be included in the answers.
    @GetMapping("/orders_without_invoices")
    public HttpEntity<?> orders_without_invoices() {
        return ResponseEntity.ok(taskService.ordersWithoutInvoices());
    }


}
