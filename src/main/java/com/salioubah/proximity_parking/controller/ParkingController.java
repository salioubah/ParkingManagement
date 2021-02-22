package com.salioubah.proximity_parking.controller;

import com.salioubah.proximity_parking.model.Parking;
import com.salioubah.proximity_parking.service.ParkingService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main controller of our parking
 */
@Controller
@RequestMapping("/api/v1")
public class ParkingController {

    private List<Parking> parking;
    private ParkingService parkingService;

    public ParkingController(){
        parkingService = new ParkingService();
    }

    /**
     * Get all the parking around a User
     * @param latUser Latitude of user's position
     * @param lonUser Longitude of user's position
     * @param distance distance between user and parking
     * @return a list of parking around a user
     */
    @GetMapping("/parking")
    public ResponseEntity<Object> getParking(@RequestParam double latUser, @RequestParam double lonUser, @RequestParam(defaultValue = "3") double distance){
        if(parking == null || parking.size() == 0) {
            parking = parkingService.loadData();
        }
        Stream<Parking> filteredStream = parking.stream().filter(v->ParkingService.isInRange(v,latUser,lonUser,distance));
        List<Parking> filteredParking = filteredStream.collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray(filteredParking);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonArray.toString());
    }
}
