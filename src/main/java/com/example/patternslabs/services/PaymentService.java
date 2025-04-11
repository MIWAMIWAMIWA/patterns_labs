package com.example.patternslabs.services;

import com.example.patternslabs.models.Payment;
import com.example.patternslabs.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void createPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public Payment getPayment(int id) {
        return paymentRepository.getReferenceById(id);
    }

    public boolean hasPayment(int id) {
        return paymentRepository.existsById(id);
    }

    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }
}
