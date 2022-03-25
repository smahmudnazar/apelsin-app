package com.example.apelsinapp.repository;

import com.example.apelsinapp.dto.TaskDTOs.NumberOfProductsInYear;
import com.example.apelsinapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findAllByActiveTrue();


    @Query(value = "select * from apelsin.public.customer where not exists(select from apelsin.public.orders as o where  o.date >'2016-01-01' and o.date < '2016-12-31'and o.customer_id=customer.id);" ,nativeQuery = true)
    List<Customer> findAllCustomersWithoutOrders();

    @Query(value = "select c.country as country , count(o.customer_id) as total from apelsin.public.customer as\n" +
            "                c inner join orders o on c.id = o.customer_id where o.date between '2016-01-01'\n" +
            "                  and '2016-12-31' group by c.country" , nativeQuery = true)
    List<NumberOfProductsInYear> NumberOfProductsInYear();

}
