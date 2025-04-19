package com.example.demo.repository;

import com.example.demo.model.BookingsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookingsRepository {

    @Autowired
    private JdbcTemplate template;

    // Get all bookings
    public List<BookingsModel> getAllBookings() {
        String sql = "SELECT * FROM Bookings";
        return template.query(sql, new RowMapper<BookingsModel>() {
            @Override
            public BookingsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookingsModel booking = new BookingsModel();
                booking.setBooking_id(rs.getInt("booking_id"));
                booking.setCustomer_id(rs.getInt("customer_id"));
                booking.setHotel_id(rs.getInt("hotel_id"));
                booking.setRoom_id(rs.getInt("room_id"));
                booking.setCheck_in_date(rs.getDate("check_in_date"));
                booking.setCheck_out_date(rs.getDate("check_out_date"));
                booking.setTotal_price(rs.getBigDecimal("total_price"));
                booking.setStatus(rs.getString("status"));
                booking.setReview_rating(rs.getInt("review_rating"));
                booking.setReview_text(rs.getString("review_text"));
                booking.setCreated_at(rs.getTimestamp("created_at"));
                return booking;
            }
        });
    }

    // Add a new booking
    public Boolean addBooking(BookingsModel booking) {
        // Validate customer_id (must be a customer)
        if (!isValidCustomer(booking.getCustomer_id())) {
            return false; // Invalid customer
        }

        // Validate hotel_id
        if (!isValidHotel(booking.getHotel_id())) {
            return false; // Invalid hotel
        }

        // Validate room_id and check that it belongs to the specified hotel
        if (!isRoomValidForHotel(booking.getRoom_id(), booking.getHotel_id())) {
            return false; // Room does not belong to the hotel
        }

        String sql = "INSERT INTO Bookings (customer_id, hotel_id, room_id, check_in_date, check_out_date, total_price, status, review_rating, review_text, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = template.update(sql,
                booking.getCustomer_id(),
                booking.getHotel_id(),
                booking.getRoom_id(),
                booking.getCheck_in_date(),
                booking.getCheck_out_date(),
                booking.getTotal_price(),
                booking.getStatus(),
                booking.getReview_rating(),
                booking.getReview_text(),
                booking.getCreated_at());
        return rowsAffected > 0;
    }

    // Check if customer_id exists and has the "Customer" role
    private boolean isValidCustomer(int customerId) {
        String sql = "SELECT COUNT(*) FROM Users WHERE user_id = ? AND role_id = (SELECT role_id FROM Roles WHERE role_name = 'Customer')";
        Integer count = template.queryForObject(sql, new Object[]{customerId}, Integer.class);
        return count != null && count > 0;
    }

    // Check if hotel_id exists
    private boolean isValidHotel(int hotelId) {
        String sql = "SELECT COUNT(*) FROM Hotels WHERE hotel_id = ?";
        Integer count = template.queryForObject(sql, new Object[]{hotelId}, Integer.class);
        return count != null && count > 0;
    }

    // Check if room_id exists and is part of the specified hotel
    private boolean isRoomValidForHotel(int roomId, int hotelId) {
        String sql = "SELECT COUNT(*) FROM Rooms WHERE room_id = ? AND hotel_id = ?";
        Integer count = template.queryForObject(sql, new Object[]{roomId, hotelId}, Integer.class);
        return count != null && count > 0;
    }

    // Get booking by booking_id
    public BookingsModel getBookingById(int bookingId) {
        String sql = "SELECT * FROM Bookings WHERE booking_id = ?";
        try {
            return template.queryForObject(sql, new Object[]{bookingId}, new RowMapper<BookingsModel>() {
                @Override
                public BookingsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BookingsModel booking = new BookingsModel();
                    booking.setBooking_id(rs.getInt("booking_id"));
                    booking.setCustomer_id(rs.getInt("customer_id"));
                    booking.setHotel_id(rs.getInt("hotel_id"));
                    booking.setRoom_id(rs.getInt("room_id"));
                    booking.setCheck_in_date(rs.getDate("check_in_date"));
                    booking.setCheck_out_date(rs.getDate("check_out_date"));
                    booking.setTotal_price(rs.getBigDecimal("total_price"));
                    booking.setStatus(rs.getString("status"));
                    booking.setReview_rating(rs.getInt("review_rating"));
                    booking.setReview_text(rs.getString("review_text"));
                    booking.setCreated_at(rs.getTimestamp("created_at"));
                    return booking;
                }
            });
        } catch (Exception e) {
            return null; // Return null if not found
        }
    }
}
