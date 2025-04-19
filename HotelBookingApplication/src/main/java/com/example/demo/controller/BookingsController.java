package com.example.demo.controller;

import com.example.demo.model.BookingsModel;
import com.example.demo.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingsController {

    @Autowired
    private BookingsService bookingsService;
    

    @PostMapping("/addBooking")
    public String addBooking(@RequestBody BookingsModel booking) {
        return bookingsService.addBooking(booking);
    }
  
    @GetMapping("/getAllBookings")
    public List<BookingsModel> getAllBookings() {
        return bookingsService.getAllBookings();
    }

   
    @GetMapping("/searchBookingById{bookingId}")
    public BookingsModel getBookingById(@PathVariable int bookingId) {
        return bookingsService.getBookingById(bookingId);
    }


}
