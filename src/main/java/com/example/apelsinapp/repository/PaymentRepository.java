package com.example.apelsinapp.repository;

import com.example.apelsinapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    List<Payment> findAllByActiveTrue();

    List<Payment> findAllByInvoiceId(Integer id);

}
