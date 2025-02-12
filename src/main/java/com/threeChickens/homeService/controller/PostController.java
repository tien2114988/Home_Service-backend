package com.threeChickens.homeService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.bankHub.CancelPostDto;
import com.threeChickens.homeService.dto.post.CreatePostDto;
import com.threeChickens.homeService.dto.post.GetPostDetailDto;
import com.threeChickens.homeService.dto.rate.CreateRateDto;
import com.threeChickens.homeService.dto.post.GetPostDto;
import com.threeChickens.homeService.dto.takePost.CreateTakePostDto;
import com.threeChickens.homeService.dto.takePost.FreelancerTakeDto;
import com.threeChickens.homeService.dto.takePost.TakePostDto;
import com.threeChickens.homeService.dto.workSchedule.GetWorkScheduleDto;
import com.threeChickens.homeService.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<TakePostDto>> takePost(@PathVariable("id") String id, @RequestBody CreateTakePostDto createTakePostDto) throws JsonProcessingException {
        TakePostDto takePostDto = postService.takePost(id, createTakePostDto);
        ApiResponse<TakePostDto> res =  ApiResponse.<TakePostDto>builder().items(takePostDto).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/rate")
    @Operation(summary = "Customer rate Freelancers after completed job")
    public ResponseEntity<ApiResponse<GetPostDto>> rate(@PathVariable("id") String id, @RequestBody List<CreateRateDto> createRateDtos) {
        GetPostDto getPostDto = postService.rate(id, createRateDtos);
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

    @PutMapping(value="/{id}/uploadImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload images for starting or ending work")
    public ResponseEntity<ApiResponse<GetPostDto>> uploadImages(@PathVariable("id") String id, @RequestParam("type") String type, @RequestParam("images") MultipartFile[] images) {
        GetPostDto getPostDto = postService.uploadImages(id,  type, images);
        ApiResponse<GetPostDto> res =  ApiResponse.<GetPostDto>builder().items(getPostDto).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<List<GetPostDto>>> getAllByCustomerId(@PathVariable("id") String id,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size,
                                                                        @RequestParam(defaultValue = "updatedAt") String sortBy,
                                                                        @RequestParam(defaultValue = "desc") String sortDirection,
                                                                        @RequestParam(required = false) String workId,
                                                                        @RequestParam(required = false) String packageName){
        List<GetPostDto> getPostDtoList = postService.getAllPostsByCustomerId(id, page, size, sortBy, sortDirection, workId, packageName);
        ApiResponse<List<GetPostDto>> res =  ApiResponse.<List<GetPostDto>>builder().items(getPostDtoList).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetPostDetailDto>> getById(@PathVariable("id") String id){
        GetPostDetailDto getPostDetail = postService.getById(id);
        ApiResponse<GetPostDetailDto> res =  ApiResponse.<GetPostDetailDto>builder().items(getPostDetail).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/freelancers/{id}")
    public ResponseEntity<ApiResponse<List<TakePostDto>>> getAllByFreelancerId(@PathVariable("id") String id,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size,
                                                                        @RequestParam(defaultValue = "updatedAt") String sortBy,
                                                                        @RequestParam(defaultValue = "desc") String sortDirection,
                                                                        @RequestParam(required = false) String workId,
                                                                        @RequestParam(required = false) String packageName,
                                                                        @RequestParam(required = false) String workStatus) {
        List<TakePostDto> getPostDtoList = postService.getAllPostsByFreelancerId(id, page, size, sortBy, sortDirection, workId, packageName, workStatus);
        ApiResponse<List<TakePostDto>> res =  ApiResponse.<List<TakePostDto>>builder().items(getPostDtoList).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}/freelancers")
    public ResponseEntity<ApiResponse<List<FreelancerTakeDto>>> getFreelancersByPostId(@PathVariable("id") String id,
                                                                                       @RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "5") int size,
                                                                                       @RequestParam(defaultValue = "updatedAt") String sortBy,
                                                                                       @RequestParam(defaultValue = "desc") String sortDirection,
                                                                                       @RequestParam(required = false) String takePostStatus) {
        List<FreelancerTakeDto> freelancerTakeDtos = postService.getFreelancerByPostsId(id, page, size, sortBy, sortDirection, takePostStatus);
        ApiResponse<List<FreelancerTakeDto>> res =  ApiResponse.<List<FreelancerTakeDto>>builder().items(freelancerTakeDtos).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetPostDto>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size,
                                                                @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                @RequestParam(defaultValue = "desc") String sortDirection,
                                                                @RequestParam(required = false) String freelancerId) {
        List<GetPostDto> getPostDtoList = postService.getAllPosts(page, size, sortBy, sortDirection, freelancerId);
        ApiResponse<List<GetPostDto>> res =  ApiResponse.<List<GetPostDto>>builder().items(getPostDtoList).build();
        return ResponseEntity.ok(res);
    }
}
