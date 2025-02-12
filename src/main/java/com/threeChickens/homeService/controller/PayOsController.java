package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.payment.PayOsDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Address", description = "APIs for integrating PayOS")
@RequestMapping("api/payOs")
public class PayOsController {

}
