package com.example.demo.controller;

import com.example.demo.model.BookingsModel;
import com.example.demo.model.UsersModel;
import com.example.demo.repository.BookingsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.services.BookingsService;
import com.example.demo.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BookingsController {

    @Autowired
    private BookingsService bookingsService;
    
    @Autowired 
    private BookingsRepository bookingsRepostiory;
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired 
    private EmailService emailService;
    
    @PostMapping("/addBooking")
    public ResponseEntity<?> addBooking(@RequestBody BookingsModel booking) {
        Integer bookingId = bookingsService.addBooking(booking);

        if (bookingId == null) {
            return ResponseEntity.badRequest().body("Booking validation failed or could not insert.");
        }

//        Map<String, Object> response = Map.of("bookingId", bookingId);
//        return ResponseEntity.ok(response);
        
        Map<String, Object> bookingInfo = bookingsRepostiory.getBookingById(bookingId);
        UsersModel user = usersRepository.getUserById(booking.getCustomer_id());

        if (user != null && bookingInfo != null) {
            String email = user.getEmail();
            String name = user.getName();
            String hotelName = (String) bookingInfo.get("hotel_name");
            String roomType = (String) bookingInfo.get("room_type");
            int guests = booking.getNumber_of_guests();
            String checkIn = booking.getCheck_in_date().toString();
            String checkOut = booking.getCheck_out_date().toString();
            double price = booking.getTotal_price().doubleValue();
       
            emailService.sendBookingConfirmation(email, name, hotelName, roomType, guests, checkIn, checkOut, price);
        }

        return ResponseEntity.ok(Map.of("bookingId", bookingId));
    }



    @GetMapping("/getAllBookings")
    public List<BookingsModel> getAllBookings() {
        return bookingsService.getAllBookings();
    }
    
    @GetMapping("/hotels")
    public List<Map<String, Object>> getHotelDetails() {
        return bookingsService.getHotelDetails();
    }

    @GetMapping("/searchBookingById/{bookingId}")
    public Map<String, Object> getBookingById(@PathVariable int bookingId) {
        return bookingsService.getBookingById(bookingId);
    }

    @GetMapping("bookings/owner/{ownerId}")
    public List<Map<String, Object>> getBookingsByOwner(@PathVariable int ownerId) {
        return bookingsService.getBookingsByOwner(ownerId);
    }

    @GetMapping("bookings/customer/{customerId}")
    public List<Map<String, Object>> getBookingsByCustomer(@PathVariable int customerId) {
        return bookingsService.getBookingsByCustomer(customerId);
    }

    @PostMapping("/{bookingId}/review")
    public ResponseEntity<Map<String, Object>> addReview(
        @PathVariable int bookingId,
        @RequestBody Map<String, Object> payload
    ) {
        int rating = (int) payload.get("review_rating");
        String review = (String) payload.get("review_text");

        Map<String, Object> updated = bookingsService.addReview(bookingId, rating, review);
        return ResponseEntity.ok(updated);
    }
}

