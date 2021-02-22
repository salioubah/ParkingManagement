package com.salioubah.proximity_parking.service;

import com.salioubah.proximity_parking.model.Parking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingServiceTest {

    Parking p;

    @BeforeEach
    void setUp() {
        p = new Parking("P1",44.8755207, -0.5443771);
    }

    @Test
    void isInRange() {
        assertTrue(ParkingService.isInRange(p, 44.8755207, -0.5443771, 1));
    }

    @Test
    void isNotInRange() {
        assertFalse(ParkingService.isInRange(p, 44, -0.5443771, 1));
    }
}