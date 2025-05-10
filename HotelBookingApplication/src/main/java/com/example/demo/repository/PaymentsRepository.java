package com.example.demo.repository;

import com.example.demo.model.PaymentsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentsRepository {

    @Autowired
    private JdbcTemplate template;

    // Get all payments
    public List<PaymentsModel> getAllPayments() {
        String sql = "SELECT * FROM Payments";
        return template.query(sql, new BeanPropertyRowMapper<>(PaymentsModel.class));
    }

    // Get payments by customer ID
    public List<PaymentsModel> getPaymentsByCustomerId(int customerId) {
        String sql = "SELECT * FROM Payments WHERE customer_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(PaymentsModel.class), customerId);
    }

    // Get payments by booking ID
    public List<PaymentsModel> getPaymentsByBookingId(int bookingId) {
        String sql = "SELECT * FROM Payments WHERE booking_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(PaymentsModel.class), bookingId);
    }

    // Get payment by transaction ID
    public PaymentsModel getPaymentByTransactionId(String transactionId) {
        String sql = "SELECT * FROM Payments WHERE transaction_id = ?";
        try {
            return template.queryForObject(sql, new Object[]{transactionId}, new BeanPropertyRowMapper<>(PaymentsModel.class));
        } catch (Exception e) {
            return null;
        }
    }

    // Add new payment
    public Boolean addPayment(PaymentsModel payment) {
        String sql = "INSERT INTO Payments (booking_id, customer_id, amount, payment_method, payment_status, transaction_id,payment_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?,?)";
        int rows = template.update(sql,
                payment.getBookingId(),
                payment.getCustomerId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                Timestamp.valueOf(LocalDateTime.now())
        );
        return rows > 0;
    }

    // Update payment status by transaction ID
    public Boolean updatePaymentStatus(String transactionId, String newStatus) {
        String sql = "UPDATE Payments SET payment_status = ? WHERE transaction_id = ?";
        int rows = template.update(sql, newStatus, transactionId);
        return rows > 0;
    }

    // Delete a payment by ID (if needed)
    public Boolean deletePaymentById(int paymentId) {
        String sql = "DELETE FROM Payments WHERE payment_id = ?";
        int rows = template.update(sql, paymentId);
        return rows > 0;
    }
}
