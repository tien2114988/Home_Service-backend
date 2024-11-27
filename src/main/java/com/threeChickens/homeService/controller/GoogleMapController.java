//package com.threeChickens.homeService.controller;
//
//import com.threeChickens.homeService.utils.GoogleMapUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RestController("api/googleMap")
//public class GoogleMapController {
//    @Autowired
//    private GoogleMapUtil googleMapUtil;
//
////    @GetMapping("/place")
////    public Map<String, Object> place(@RequestParam String input) throws IOException {
////        return googleMapUtil.placeAutocomplete(input);
////    }
//
////    @GetMapping("/geocode")
////    public Map<String, Object> geocode(@RequestParam String placeId, @RequestParam String latitude, @RequestParam String longitude) throws IOException {
////        return googleMapUtil.geocode(placeId, latitude, longitude);
////    }
//
////    @GetMapping("/distanceMatrix")
////    public Map<String, Object> distanceMatrix(@RequestParam String originPlaceId, @RequestParam String destinationPlaceId) throws IOException {
////        return googleMapUtil.distanceMatrix(originPlaceId, destinationPlaceId);
////    }
//}
