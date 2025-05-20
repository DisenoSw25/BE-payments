package edu.uclm.esi.payments.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.payments.model.Payment;

public interface PaymentDAO extends JpaRepository<Payment, Long> {
    
}