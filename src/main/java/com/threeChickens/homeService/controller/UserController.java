package com.threeChickens.homeService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.address.CreateAddressDto;
import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.notification.GetNotificationDto;
import com.threeChickens.homeService.dto.notification.RedisNotificationDto;
import com.threeChickens.homeService.dto.payment.PayOsDto;
import com.threeChickens.homeService.dto.post.GetPostDto;
import com.threeChickens.homeService.dto.user.GetUserDetailDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.user.PaymentHistoryDto;
import com.threeChickens.homeService.dto.user.UpdateUserDto;
import com.threeChickens.homeService.dto.work.GetFreelancerWorkDto;
import com.threeChickens.homeService.service.UserService;
import com.threeChickens.homeService.utils.FirebaseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "User", description = "APIs for User")
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private FirebaseUtil firebaseUtil;

    @GetMapping
    @Operation(summary = "Get all User for Admin")
    public ResponseEntity<ApiResponse<List<GetUserDto>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size,
                                                                @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                @RequestParam(defaultValue = "desc") String sortDirection,
                                                                @RequestParam(required = false) String role,
                                                                @RequestParam(required = false) String postId) {
        List<GetUserDto> getUserDtoList = userService.getUsers(page, size, sortBy, sortDirection, role, postId);
        ApiResponse<List<GetUserDto>> res = ApiResponse.<List<GetUserDto>>builder().items(getUserDtoList).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping(value="/{id}/uploadAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload avatar for user")
    public ResponseEntity<ApiResponse<String>> uploadAvatars(@PathVariable("id") String id, @RequestParam("avatar") MultipartFile avatar) {
        String fileName = userService.updateAvatar(id, avatar);
        ApiResponse<String> res =  ApiResponse.<String>builder().items(fileName).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id")
    public ResponseEntity<ApiResponse<GetUserDetailDto>> getById(@PathVariable("id") String id, @RequestParam(required = false) String role) {
        GetUserDetailDto getUserDto = userService.getUserByIdAndRole(id, role);
        ApiResponse<GetUserDetailDto> res = ApiResponse.<GetUserDetailDto>builder().items(getUserDto).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/works")
    @Operation(summary = "Get works by freelancer id")
    public ResponseEntity<ApiResponse<List<GetFreelancerWorkDto>>> getWorksById(@PathVariable("id") String id) {
        List<GetFreelancerWorkDto> getFreelancerWorkDtos = userService.getWorksByFreelancerId(id);
        ApiResponse<List<GetFreelancerWorkDto>> res = ApiResponse.<List<GetFreelancerWorkDto>>builder().items(getFreelancerWorkDtos).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/notifications")
    @Operation(summary = "Get notifications for user")
    public ResponseEntity<ApiResponse<List<RedisNotificationDto>>> getNotifications(@PathVariable("id") String id) {
        List<RedisNotificationDto> notifications = userService.getNotificationsByUserId(id);
        ApiResponse<List<RedisNotificationDto>> res = ApiResponse.<List<RedisNotificationDto>>builder().items(notifications).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/paymentHistories")
    @Operation(summary = "Get payment histories for user")
    public ResponseEntity<ApiResponse<List<PaymentHistoryDto>>> getPaymentHistories(@PathVariable("id") String id) {
        List<PaymentHistoryDto> paymentHistories = userService.getPaymentHistoriesByUserId(id);
        ApiResponse<List<PaymentHistoryDto>> res = ApiResponse.<List<PaymentHistoryDto>>builder().items(paymentHistories).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{userId}/notifications/{id}")
    @Operation(summary = "View a notification")
    public ResponseEntity<ApiResponse<?>> viewNotification(@PathVariable("userId") String userId, @PathVariable int id) {
        userService.viewNotification(userId, id);
        ApiResponse<?> res = ApiResponse.builder().build();
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

//    @PostMapping("/sendNotification")
//    public void sendNotification(@RequestParam String to, @RequestParam String subject, @RequestParam String body) throws JsonProcessingException {
//        firebaseUtil.sendNotification(to, subject, body);
//    }

    @PutMapping("/{id}/recharge")
    public ResponseEntity<ApiResponse<String>> recharge (@PathVariable("id") String id,@RequestBody PayOsDto payOsDto) throws Exception {
        String checkoutUrl = userService.recharge(id ,payOsDto);
        ApiResponse<String> res = ApiResponse.<String>builder().items(checkoutUrl).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<ApiResponse<GetUserDetailDto>> withdraw (@PathVariable("id") String id, @RequestBody PayOsDto payOsDto) throws Exception {
        GetUserDetailDto getUserDetailDto = userService.withdraw(id ,payOsDto);
        ApiResponse<GetUserDetailDto> res = ApiResponse.<GetUserDetailDto>builder().items(getUserDetailDto).build();
        return ResponseEntity.ok(res);
    }
}
