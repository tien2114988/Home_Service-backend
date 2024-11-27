package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.googleMap.DistanceMatrixDto;
import com.threeChickens.homeService.dto.googleMap.GeocodeDto;
import com.threeChickens.homeService.dto.googleMap.PlaceDto;
import com.threeChickens.homeService.dto.province.WardDto;
import com.threeChickens.homeService.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Address", description = "APIs for Address of User (Province -> District -> Ward) or Google Map")
@RequestMapping("api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

//    @GetMapping
//    @Operation(summary = "Get all Provinces or Districts or Wards",
//            description = "Pass no param to get Province List, Pass provinceCode to get District list, " +
//                    "Pass provinceCode and districtCode to get Ward list")
//    public ResponseEntity<ApiResponse<List<WardDto>>> getProvinces(@RequestParam(value="provinceCode", required = false) Integer provinceCode, @RequestParam(value = "wardCode", required = false) Integer districtCode) {
//        List<WardDto> provinces = addressService.getProvinces(provinceCode, districtCode);
//        ApiResponse<List<WardDto>> res = ApiResponse.<List<WardDto>>builder().items(provinces).build();
//        return ResponseEntity.ok(res);
//    }

    @GetMapping("/googleMap/place")
    @Operation(summary = "Search predicted places by input",
            description = "Pass search input and get all predicted places")
    public ResponseEntity<ApiResponse<List<PlaceDto>>> place(@RequestParam String input) throws IOException {
        List<PlaceDto> places = addressService.searchPlaceByGoogleMap(input);
        ApiResponse<List<PlaceDto>> res = ApiResponse.<List<PlaceDto>>builder().items(places).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/googleMap/geocode")
    @Operation(summary = "Get a detail place by placeId or latitude and longitude",
            description = "Pass only placeId or both latitude and longitude")
    public ResponseEntity<ApiResponse<GeocodeDto>> geocode(@RequestParam(required = false) String placeId, @RequestParam(required = false) String latitude, @RequestParam(required = false) String longitude) throws IOException {
        GeocodeDto geocode = addressService.geocode(placeId, latitude, longitude);
        ApiResponse<GeocodeDto> res = ApiResponse.<GeocodeDto>builder().items(geocode).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/googleMap/distanceMatrix")
    @Operation(summary = "Calculate distance from source to destination")
    public ResponseEntity<ApiResponse<DistanceMatrixDto>> distanceMatrix(@RequestParam String sourcePlaceId, @RequestParam String destinationPlaceId) throws IOException {
        DistanceMatrixDto distanceMatrixDto = addressService.distanceMatrix(sourcePlaceId, destinationPlaceId);
        ApiResponse<DistanceMatrixDto> res = ApiResponse.<DistanceMatrixDto>builder().items(distanceMatrixDto).build();
        return ResponseEntity.ok(res);
    }

}
