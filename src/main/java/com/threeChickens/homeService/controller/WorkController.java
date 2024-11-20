package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.work.FreelancerDto;
import com.threeChickens.homeService.dto.work.FreelancerWorkDto;
import com.threeChickens.homeService.dto.work.CreateWorkDto;
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
    public ResponseEntity<ApiResponse<?>> provideService(@PathVariable("id") String id, @PathVariable("freelancerId") String freelancerId, @RequestBody FreelancerWorkDto freelancerWorkDto) {
        workService.provideService(id, freelancerId, freelancerWorkDto);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @GetMapping("/{id}/freelancers")
    @Operation(summary = "Get all Freelancers with empty calendar on a service",
    description = "Get all Freelancers with empty calendar at the time that a post occurs")
    public ResponseEntity<ApiResponse<List<FreelancerDto>>> getAllFreelancer(@PathVariable("id") String id, @RequestParam("postId") String postId) {
        List<FreelancerDto> freelancers = workService.getAllFreelancersWithEmptyCalendar(id, postId);
        ApiResponse<List<FreelancerDto>> res = ApiResponse.<List<FreelancerDto>>builder().items(freelancers).build();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Create a Test for a Work")
    public ResponseEntity<ApiResponse<CreateWorkDto>> update(@PathVariable("id") String id, @RequestBody CreateWorkDto createWorkDto) {
        createWorkDto = workService.updateWork(id, createWorkDto);
        ApiResponse<CreateWorkDto> res = ApiResponse.<CreateWorkDto>builder().items(createWorkDto).build();
        return ResponseEntity.ok(res);
    }
}
