package com.example.demo.services;

import com.example.demo.model.PaymentsModel;
import com.example.demo.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    // Method to get all payments
    public List<PaymentsModel> getAllPayments() {
        return paymentsRepository.getAllPayments();
    }

    // Method to get payments by customer ID
    public List<PaymentsModel> getPaymentsByCustomerId(int customerId) {
        return paymentsRepository.getPaymentsByCustomerId(customerId);
    }

    // Method to get payments by booking ID
    public List<PaymentsModel> getPaymentsByBookingId(int bookingId) {
        return paymentsRepository.getPaymentsByBookingId(bookingId);
    }

    // Method to get payment by transaction ID
    public PaymentsModel getPaymentByTransactionId(String transactionId) {
        return paymentsRepository.getPaymentByTransactionId(transactionId);
    }

    // Method to add a new payment
    public String addPayment(PaymentsModel payment) {
        boolean added = paymentsRepository.addPayment(payment);
        return added ? "Payment added successfully" : "Failed to add payment";
    }

    // Method to update the status of a payment by transaction ID
    public String updatePaymentStatus(String transactionId, String newStatus) {
        boolean updated = paymentsRepository.updatePaymentStatus(transactionId, newStatus);
        return updated ? "Payment status updated successfully" : "Failed to update payment status";
    }

    // Method to delete a payment by payment ID
    public String deletePayment(int paymentId) {
        boolean deleted = paymentsRepository.deletePaymentById(paymentId);
        return deleted ? "Payment deleted successfully" : "Failed to delete payment";
    }
}
