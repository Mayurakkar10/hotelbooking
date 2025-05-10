package com.example.demo.controller;

import com.example.demo.model.PaymentsModel;
import com.example.demo.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    // Get all payments
    @GetMapping("/getAllPayments")
    public List<PaymentsModel> getAllPayments() {
        return paymentsService.getAllPayments();
    }

    // Get payments by customer ID
    @GetMapping("/getPaymentsByCustomerId/{customerId}")
    public List<PaymentsModel> getPaymentsByCustomerId(@PathVariable int customerId) {
        return paymentsService.getPaymentsByCustomerId(customerId);
    }

    // Get payments by booking ID
    @GetMapping("/getPaymentsByBookingId/{bookingId}")
    public List<PaymentsModel> getPaymentsByBookingId(@PathVariable int bookingId) {
        return paymentsService.getPaymentsByBookingId(bookingId);
    }

    // Get payment by transaction ID
    @GetMapping("/getPaymentByTransactionId/{transactionId}")
    public ResponseEntity<?> getPaymentByTransactionId(@PathVariable String transactionId) {
        PaymentsModel payment = paymentsService.getPaymentByTransactionId(transactionId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.status(404).body("Payment not found");
        }
    }

    // Add a new payment
    @PostMapping("bookings/addPayment")
    public String addPayment(@RequestBody PaymentsModel payment) {
        return paymentsService.addPayment(payment);
    }

    // Update payment status
    @PutMapping("/updatePaymentStatus/{transactionId}")
    public String updatePaymentStatus(@PathVariable String transactionId, @RequestParam String newStatus) {
        return paymentsService.updatePaymentStatus(transactionId, newStatus);
    }

    // Delete a payment by ID
    @DeleteMapping("/deletePaymentById/{paymentId}")
    public String deletePayment(@PathVariable int paymentId) {
        return paymentsService.deletePayment(paymentId);
    }
}
