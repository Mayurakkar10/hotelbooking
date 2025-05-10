package com.example.demo.services;

import com.example.demo.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RoomAvailabilityScheduler {

    @Autowired
    private RoomsRepository roomsRepository;

    // Runs every day at 2 AM
    @Scheduled(cron = "0 * * * * *") // Every minute (for testing)

    public void updateRoomAvailabilityBasedOnCheckout() {
        roomsRepository.setRoomsAvailableAfterCheckout();
    }
}

