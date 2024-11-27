package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.bankHub.CancelPostDto;
import com.threeChickens.homeService.dto.post.CreatePostDto;
import com.threeChickens.homeService.dto.post.GetPostDto;
import com.threeChickens.homeService.dto.post.CreateTakePostDto;
import com.threeChickens.homeService.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Post", description = "APIs for Post of Service")
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(summary = "Create a post for Customer")
    public ResponseEntity<ApiResponse<GetPostDto>> create(@RequestBody CreatePostDto createPostDto) {
        GetPostDto getPostDto = postService.createPost(createPostDto);
        ApiResponse<GetPostDto> res =  ApiResponse.<GetPostDto>builder().items(getPostDto).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/takePost")
    @Operation(summary = "Take a post for Freelancer",
            description = "status = PENDING (Customer send request to Freelancer), " +
                    "REJECTED (Freelancer reject post request)," +
                    "ACCEPTED (Freelancer take post or accept post request)")
    public ResponseEntity<ApiResponse<GetPostDto>> takePost(@PathVariable("id") String id, @RequestBody CreateTakePostDto createTakePostDto) {
        GetPostDto getPostDto = postService.takePost(id, createTakePostDto);
        ApiResponse<GetPostDto> res =  ApiResponse.<GetPostDto>builder().items(getPostDto).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/cancelPost")
    @Operation(summary = "Cancel a post for User",
            description = "Pass request body if Freelancer cancel post, Don't pass request body if Customer cancel post")
    public ResponseEntity<ApiResponse<GetPostDto>> cancelPost(@PathVariable("id") String id, @RequestBody CancelPostDto cancelPOstDto) {
        GetPostDto getPostDto = postService.cancelPost(id, cancelPOstDto);
        ApiResponse<GetPostDto> res =  ApiResponse.<GetPostDto>builder().items(getPostDto).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<GetPostDto>>> getAllByUserId(@PathVariable("userId") String userId) {
        List<GetPostDto> getPostDtoList = postService.getAllPostsByUserId(userId);
        ApiResponse<List<GetPostDto>> res =  ApiResponse.<List<GetPostDto>>builder().items(getPostDtoList).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetPostDto>>> getAll() {
        List<GetPostDto> getPostDtoList = postService.getAllPosts();
        ApiResponse<List<GetPostDto>> res =  ApiResponse.<List<GetPostDto>>builder().items(getPostDtoList).build();
        return ResponseEntity.ok(res);
    }
}
