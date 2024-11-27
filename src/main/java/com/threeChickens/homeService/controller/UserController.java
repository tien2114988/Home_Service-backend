package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.address.CreateAddressDto;
import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.user.UpdateUserDto;
import com.threeChickens.homeService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User", description = "APIs for User")
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all User for Admin")
    public ResponseEntity<ApiResponse<List<GetUserDto>>> getAll() {
        List<GetUserDto> getUserDtoList = userService.getUsers();
        ApiResponse<List<GetUserDto>> res = ApiResponse.<List<GetUserDto>>builder().items(getUserDtoList).build();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a user", description = "Pass only updated fields, dont pass not-changed fields")
    public ResponseEntity<ApiResponse<GetUserDto>> updateUser(@PathVariable("id") String id, @RequestBody @Valid UpdateUserDto updateUserDto) throws Exception {
        GetUserDto updatedUser = userService.updateUser(id, updateUserDto);
        ApiResponse<GetUserDto> res = ApiResponse.<GetUserDto>builder().items(updatedUser).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/addresses")
    @Operation(summary = "Create an address for a user")
    public ResponseEntity<ApiResponse<GetAddressDto>> addAddress(@PathVariable("id") String id, @RequestBody CreateAddressDto createAddressDto) throws Exception {
        GetAddressDto getAddressDto = userService.addAddress(id, createAddressDto);
        ApiResponse<GetAddressDto> res = ApiResponse.<GetAddressDto>builder().items(getAddressDto).build();
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/addresses/{id}")
    @Operation(summary = "Delete an address for a user")
    public ResponseEntity<ApiResponse<?>> deleteAddress(@PathVariable("id") String id) throws Exception {
        userService.deleteAddress(id);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PatchMapping("/addresses/{id}")
    @Operation(summary = "Update an address for a user", description = "Pass only changed fields")
    public ResponseEntity<ApiResponse<GetAddressDto>> updateAddress(@PathVariable("id") String addressId, @RequestBody CreateAddressDto updateAddressDto) throws Exception {
        GetAddressDto getAddressDto = userService.updateAddress(addressId, updateAddressDto);
        ApiResponse<GetAddressDto> res = ApiResponse.<GetAddressDto>builder().items(getAddressDto).build();
        return ResponseEntity.ok(res);
    }
}
