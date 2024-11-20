package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.utils.GoogleMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/googleMap")
public class GoogleMapController {
    @Autowired
    private GoogleMapUtil googleMapUtil;

    @GetMapping("/reverse-geocode")
    public String reverseGeocode(@RequestParam double lat, @RequestParam double lng) {
        return googleMapUtil.reverseGeocode(lat, lng);
    }

    @GetMapping("/distance")
    public String getDistance(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) {
        return googleMapUtil.getDistance(originLat, originLng, destLat, destLng);
    }

    @GetMapping("/directions")
    public String getDirections(
            @RequestParam double originLat,
            @RequestParam double originLng,
            @RequestParam double destLat,
            @RequestParam double destLng) {
        return googleMapUtil.getDirections(originLat, originLng, destLat, destLng);
    }
}
