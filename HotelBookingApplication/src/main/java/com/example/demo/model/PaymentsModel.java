package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class PaymentsModel {
	    private int paymentId;
	    private int bookingId;
	    private int customerId;
	    private BigDecimal amount;
	    private String paymentMethod;
	    private String paymentStatus;
	    private String transactionId;
	    private Timestamp paymentDate;
}
