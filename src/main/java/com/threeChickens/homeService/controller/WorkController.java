package com.threeChickens.homeService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.post.GetPostDto;
import com.threeChickens.homeService.dto.work.CreateFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.CreateWorkDto;
import com.threeChickens.homeService.dto.work.GetDetailFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.GetWorkDto;
import com.threeChickens.homeService.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Work", description = "APIs for Freelancer register to provide Service and do Test")
@RequestMapping("api/works")
public class WorkController {
    @Autowired
    private WorkService workService;

    @PatchMapping("/{id}/freelancers/{freelancerId}")
    @Operation(summary = "Freelancer register to provide Service")
    public ResponseEntity<ApiResponse<GetDetailFreelancerWorkDto>> provideService(@PathVariable("id") String id, @PathVariable("freelancerId") String freelancerId, @RequestBody CreateFreelancerWorkDto createFreelancerWorkDto) {
        GetDetailFreelancerWorkDto getDetailFreelancerWorkDto = workService.provideService(id, freelancerId, createFreelancerWorkDto);
        ApiResponse<GetDetailFreelancerWorkDto> res = ApiResponse.<GetDetailFreelancerWorkDto>builder().items(getDetailFreelancerWorkDto).build();
        return ResponseEntity.ok(res);
    }

    @PutMapping(value="/freelancerWorkService/{id}/uploadImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload images for freelancer providing service")
    public ResponseEntity<ApiResponse<GetDetailFreelancerWorkDto>> uploadImages(@PathVariable("id") String id, @RequestParam("images") MultipartFile[] images) {
        GetDetailFreelancerWorkDto getDetailFreelancerWorkDto = workService.uploadImages(id, images);
        ApiResponse<GetDetailFreelancerWorkDto> res =  ApiResponse.<GetDetailFreelancerWorkDto>builder().items(getDetailFreelancerWorkDto).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping
    @Operation(summary = "Get all services")
    public ResponseEntity<ApiResponse<List<GetWorkDto>>> getAll(@RequestParam(required = false) String freelancerId) {
        List<GetWorkDto> freelancers = workService.getAllWorks(freelancerId);
        ApiResponse<List<GetWorkDto>> res = ApiResponse.<List<GetWorkDto>>builder().items(freelancers).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/freelancers")
    @Operation(summary = "Get requests of freelancers on subcribing to service")
    public ResponseEntity<ApiResponse<List<GetDetailFreelancerWorkDto>>> getAllFreelancer(@RequestParam(required = false) String id, @RequestParam(required = false) String postId) throws JsonProcessingException {
        List<GetDetailFreelancerWorkDto> getDetailFreelancerWorkDtos = workService.getAllFreelancersByWork(id, postId);
        ApiResponse<List<GetDetailFreelancerWorkDto>> res = ApiResponse.<List<GetDetailFreelancerWorkDto>>builder().items(getDetailFreelancerWorkDtos).build();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a work")
    public ResponseEntity<ApiResponse<GetWorkDto>> update(@PathVariable("id") String id, @RequestBody CreateWorkDto createWorkDto) throws JsonProcessingException {
        GetWorkDto getWorkDto = workService.updateWork(id, createWorkDto);
        ApiResponse<GetWorkDto> res = ApiResponse.<GetWorkDto>builder().items(getWorkDto).build();
        return ResponseEntity.ok(res);
    }
}
