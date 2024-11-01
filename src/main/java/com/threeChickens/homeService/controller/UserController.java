package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.auth.SignUpDto;
import com.threeChickens.homeService.dto.user.UserDto;
import com.threeChickens.homeService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAll() {
        List<UserDto> userDtoList = userService.getAllUsers();
        ApiResponse<List<UserDto>> res = ApiResponse.<List<UserDto>>builder().items(userDtoList).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserDto userDto) throws Exception {
        UserDto updatedUser = userService.updateUser(id, userDto);
        ApiResponse<UserDto> res = ApiResponse.<UserDto>builder().items(updatedUser).build();
        return ResponseEntity.ok(res);
    }
}
