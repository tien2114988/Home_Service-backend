package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.province.WardDto;
import com.threeChickens.homeService.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Address", description = "APIs for Address of User (Province -> District -> Ward or Google Map")
@RequestMapping("api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    @Operation(summary = "Get all Provinces or Districts or Wards",
            description = "Pass no param to get Province List, Pass provinceCode to get District list, " +
                    "Pass provinceCode and districtCode to get Ward list")
    public ResponseEntity<ApiResponse<List<WardDto>>> getProvinces(@RequestParam(value="provinceCode", required = false) Integer provinceCode, @RequestParam(value = "wardCode", required = false) Integer districtCode) {
        List<WardDto> provinces = addressService.getProvinces(provinceCode, districtCode);
        ApiResponse<List<WardDto>> res = ApiResponse.<List<WardDto>>builder().items(provinces).build();
        return ResponseEntity.ok(res);
    }
}
