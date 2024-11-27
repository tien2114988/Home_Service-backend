package com.threeChickens.homeService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.work.CreateFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.CreateWorkDto;
import com.threeChickens.homeService.dto.work.GetFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.GetWorkDto;
import com.threeChickens.homeService.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Work", description = "APIs for Freelancer register to provide Service and do Test")
@RequestMapping("api/works")
public class WorkController {
    @Autowired
    private WorkService workService;

    @PutMapping("/{id}/freelancers/{freelancerId}")
    @Operation(summary = "Freelancer register to provide Service")
    public ResponseEntity<ApiResponse<GetFreelancerWorkDto>> provideService(@PathVariable("id") String id, @PathVariable("freelancerId") String freelancerId, @RequestBody CreateFreelancerWorkDto createFreelancerWorkDto) {
        GetFreelancerWorkDto getFreelancerWorkDto = workService.provideService(id, freelancerId, createFreelancerWorkDto);
        ApiResponse<GetFreelancerWorkDto> res = ApiResponse.<GetFreelancerWorkDto>builder().items(getFreelancerWorkDto).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping
    @Operation(summary = "Get all services")
    public ResponseEntity<ApiResponse<List<GetWorkDto>>> getAll() {
        List<GetWorkDto> freelancers = workService.getAllWorks();
        ApiResponse<List<GetWorkDto>> res = ApiResponse.<List<GetWorkDto>>builder().items(freelancers).build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/freelancers")
    @Operation(summary = "Get requests of freelancers on subcribing to service")
    public ResponseEntity<ApiResponse<List<GetFreelancerWorkDto>>> getAllFreelancer(@RequestParam(required = false) String id, @RequestParam(required = false) String postId) {
        List<GetFreelancerWorkDto> getFreelancerWorkDtos = workService.getAllFreelancersByWork(id, postId);
        ApiResponse<List<GetFreelancerWorkDto>> res = ApiResponse.<List<GetFreelancerWorkDto>>builder().items(getFreelancerWorkDtos).build();
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
