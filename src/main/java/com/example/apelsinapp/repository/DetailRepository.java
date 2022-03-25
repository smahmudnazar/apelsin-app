package com.example.apelsinapp.repository;

import com.example.apelsinapp.dto.TaskDTOs.DetailByPaymentId;
import com.example.apelsinapp.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRepository extends JpaRepository<Detail,Integer> {
    List<Detail> findAllByProductId(Integer id);
    List<Detail> findAllByOrderId(Integer id);

    @Query(value = "select d.id,d.product_id ,d.quantity as quantity,d.order_id from apelsin.public.payment as p inner join apelsin.public.invoice i on i.id = p.invoice_id\n" +
            "   inner join apelsin.public.orders o on o.id = i.order_id\n" +
            "   inner join apelsin.public.detail d on o.id = d.order_id where p.id = :id ;",nativeQuery = true)
    List<DetailByPaymentId> findByPaymentId(Integer id);
}
