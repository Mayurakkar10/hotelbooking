package com.example.demo.controller;

import com.example.demo.model.RoomsModel;
import com.example.demo.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/rooms")
public class RoomsController {

    @Autowired
    private RoomsService roomsService;

    // Get all rooms
    @GetMapping("/getAllRooms")
    public List<RoomsModel> getAllRooms() {
        return roomsService.getAllRooms();
    }

    // Get room by ID
    @GetMapping("/{roomId}")
    public RoomsModel getRoomById(@PathVariable int roomId) {
        return roomsService.getRoomById(roomId);
    }

    // Add a new room
    @PostMapping("/addRooms")
    public String addRoom(@RequestBody RoomsModel room) {
        return roomsService.addRoom(room);
    }
    @PutMapping("/update-status/{roomId}")
    public String updateAvailability( @PathVariable int roomId,@RequestBody Map<String, String> requestBody) {

        String newStatus = requestBody.get("availability_status");
        boolean result = roomsService.updateRoomAvailability(roomId, newStatus);

        if (result) {
            return "Room status updated successfully";
        } else {
            return "Room not found or update failed";
        }
    }

    
    @PutMapping("/UpdateRoomById{roomId}")
    public String updateRoom(@PathVariable int roomId, @RequestBody RoomsModel room) {
        return roomsService.updateRoom(roomId, room);
    }

    // Delete room by ID
    @DeleteMapping("/{roomId}")
    public String deleteRoom(@PathVariable int roomId) {
        return roomsService.deleteRoom(roomId);
    }
}
