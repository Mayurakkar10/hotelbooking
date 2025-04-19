package com.example.demo.services;

import com.example.demo.model.BookingsModel;
import com.example.demo.repository.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingsService {

    @Autowired
    private BookingsRepository bookingsRepository;

    // Get all bookings
    public List<BookingsModel> getAllBookings() {
        return bookingsRepository.getAllBookings();
    }

    // Add a new booking
    public String addBooking(BookingsModel booking) {
        boolean added = bookingsRepository.addBooking(booking);
        return added ? "Booking added successfully" : "Failed to add booking. Check customer, hotel, or room details.";
    }

    // Get booking by booking_id
    public BookingsModel getBookingById(int bookingId) {
        return bookingsRepository.getBookingById(bookingId);
    }
}
