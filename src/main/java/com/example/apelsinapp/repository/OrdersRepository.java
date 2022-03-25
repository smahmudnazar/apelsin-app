package com.example.apelsinapp.repository;

import com.example.apelsinapp.dto.TaskDTOs.OrdersWithoutInvoices;
import com.example.apelsinapp.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findAllByActiveTrue();

    @Query(value = "select * from apelsin.public.orders where not exists (select from apelsin.public.detail where orders\n" +
            ".date > '2016-09-06' or detail.order_id = orders.id)", nativeQuery = true)
    List<Orders> orders_without_details();

    @Query(value = "select distinct * from orders o join customer c on c.id = o.customer_id where " +
            "o.date in (select max(date) from orders o where o.customer_id = c.id group by o.customer_id )", nativeQuery = true)
    List<Orders> getLastCustomerOrders();

    @Query(value = "select o.id,o.date, sum(d.quantity) as quantity, sum(p.price) as price from apelsin.public.orders as o\n" +
            "    join detail d on o.id = d.order_id join product p on p.id = d.product_id where\n" +
            "  o.id not in (select o2.id from apelsin.public.orders as o2 join invoice i on o2.id = i.order_id) group by o.id, o.date",nativeQuery = true)
    List<OrdersWithoutInvoices> ordersWithoutInvoices();
}
