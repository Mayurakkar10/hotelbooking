package com.example.demo.services;

import com.example.demo.model.RoomsModel;
import com.example.demo.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {

    @Autowired
    private RoomsRepository roomsRepository;

    // Get all rooms
    public List<RoomsModel> getAllRooms() {
        return roomsRepository.getAllRooms();
    }

    // Get room by ID
    public RoomsModel getRoomById(int roomId) {
        return roomsRepository.getRoomById(roomId);
    }

    // Add new room
    public String addRoom(RoomsModel room) {
        boolean added = roomsRepository.addRoom(room);
        return added ? "Room added successfully" : "Failed to add room: Invalid hotel_id or type_id";
    }

    // Update entire room by ID
    public String updateRoom(int roomId, RoomsModel room) {
        room.setRoom_id(roomId); // Make sure roomId is set
        boolean updated = roomsRepository.updateRoom(room);
        return updated ? "Room updated successfully" : "Failed to update room: Invalid hotel_id/type_id or room not found";
    }

    // Update availability status
    public boolean updateRoomAvailability(int roomId, String newStatus) {
        return roomsRepository.updateRoomAvailability(roomId, newStatus);
    }

    // Delete room
    public String deleteRoom(int roomId) {
        boolean deleted = roomsRepository.deleteRoomById(roomId);
        return deleted ? "Room deleted successfully" : "Room not found or deletion failed";
    }
}
