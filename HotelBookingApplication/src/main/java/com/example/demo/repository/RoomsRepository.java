package com.example.demo.repository;

import com.example.demo.model.RoomsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomsRepository {

    @Autowired
    private JdbcTemplate template;

    // Get all rooms
    public List<RoomsModel> getAllRooms() {
        String sql = "SELECT * FROM Rooms";
        return template.query(sql, new RowMapper<RoomsModel>() {
            @Override
            public RoomsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoomsModel room = new RoomsModel();
                room.setRoom_id(rs.getInt("room_id"));
                room.setHotel_id(rs.getInt("hotel_id"));
                room.setType_id(rs.getInt("type_id"));
                room.setPrice(rs.getBigDecimal("price"));
                room.setAvailability_status(rs.getString("availability_status"));
                room.setImage_url(rs.getString("image_url"));
                room.setCreated_at(rs.getTimestamp("created_at"));
                return room;
            }
        });
    }

    // Add a new room
    public Boolean addRoom(RoomsModel room) {
        // Validate hotel_id and type_id
        if (!isValidHotel(room.getHotel_id()) || !isValidRoomType(room.getType_id())) {
            return false;
        }

        String sql = "INSERT INTO Rooms (hotel_id, type_id, price, availability_status, image_url, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        int rowsAffected = template.update(sql,
                room.getHotel_id(),
                room.getType_id(),
                room.getPrice(),
                room.getAvailability_status(),
                room.getImage_url(),
                room.getCreated_at());
        return rowsAffected > 0;
    }

    // Update room details
    public Boolean updateRoom(RoomsModel room) {
        if (!isValidHotel(room.getHotel_id()) || !isValidRoomType(room.getType_id())) {
            return false;
        }

        String sql = "UPDATE Rooms SET price = ?, availability_status = ?, image_url = ?, hotel_id = ?, type_id = ? " +
                     "WHERE room_id = ?";
        int rowsAffected = template.update(sql,
                room.getPrice(),
                room.getAvailability_status(),
                room.getImage_url(),
                room.getHotel_id(),
                room.getType_id(),
                room.getRoom_id());
        return rowsAffected > 0;
    }

    public Boolean updateRoomAvailability(int roomId, String status) {
        String sql = "UPDATE Rooms SET availability_status = ? WHERE room_id = ?";
        int rowsAffected = template.update(sql, status, roomId);
        return rowsAffected > 0;
    }

    public Boolean deleteRoomById(int roomId) {
        String sql = "DELETE FROM Rooms WHERE room_id = ?";
        int rowsAffected = template.update(sql, roomId);
        return rowsAffected > 0;
    }

    // Get room by room_id
    public RoomsModel getRoomById(int roomId) {
        String sql = "SELECT * FROM Rooms WHERE room_id = ?";
        try {
            return template.queryForObject(sql, new Object[]{roomId}, new RowMapper<RoomsModel>() {
                @Override
                public RoomsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                    RoomsModel room = new RoomsModel();
                    room.setRoom_id(rs.getInt("room_id"));
                    room.setHotel_id(rs.getInt("hotel_id"));
                    room.setType_id(rs.getInt("type_id"));
                    room.setPrice(rs.getBigDecimal("price"));
                    room.setAvailability_status(rs.getString("availability_status"));
                    room.setImage_url(rs.getString("image_url"));
                    room.setCreated_at(rs.getTimestamp("created_at"));
                    return room;
                }
            });
        } catch (Exception e) {
            return null; // Return null if not found
        }
    }

    // Check if hotel exists
    private boolean isValidHotel(int hotelId) {
        String sql = "SELECT COUNT(*) FROM Hotels WHERE hotel_id = ?";
        Integer count = template.queryForObject(sql, new Object[]{hotelId}, Integer.class);
        return count != null && count > 0;
    }

    // Check if room type exists
    private boolean isValidRoomType(int typeId) {
        String sql = "SELECT COUNT(*) FROM Room_Types WHERE type_id = ?";
        Integer count = template.queryForObject(sql, new Object[]{typeId}, Integer.class);
        return count != null && count > 0;
    }
}
