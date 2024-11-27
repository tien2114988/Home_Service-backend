package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.test.*;
import com.threeChickens.homeService.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Test", description = "APIs for Test of Service")
@RequestMapping("api/tests")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/{id}/questions")
    @Operation(summary = "Add a question for a test")
    public ResponseEntity<ApiResponse<List<GetQuestionDto>>> getQuestionsByTestId(@PathVariable("id") String id, @RequestParam(required = false) Integer questionCount) throws Exception {
        List<GetQuestionDto> getQuestionDtos = testService.getQuestionsByTestId(id, questionCount);
        ApiResponse<List<GetQuestionDto>> res = ApiResponse.<List<GetQuestionDto>>builder().items(getQuestionDtos).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/questions")
    @Operation(summary = "Add a question for a test")
    public ResponseEntity<ApiResponse<GetQuestionDto>> addQuestion(@PathVariable("id") String id, @RequestBody CreateQuestionDto createQuestionDto) throws Exception {
        GetQuestionDto getQuestionDto = testService.addQuestion(id, createQuestionDto);
        ApiResponse<GetQuestionDto> res = ApiResponse.<GetQuestionDto>builder().items(getQuestionDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/testResults")
    @Operation(summary = "Do test")
    public ResponseEntity<ApiResponse<GetTestResultDto>> doTest(@PathVariable("id") String id, @RequestBody CreateTestResultDto createTestResultDto) throws Exception {
        GetTestResultDto getTestResultDto = testService.doTest(id, createTestResultDto);
        ApiResponse<GetTestResultDto> res = ApiResponse.<GetTestResultDto>builder().items(getTestResultDto).build();
        return ResponseEntity.ok(res);
    }

//    @PostMapping("/questions/{id}/choices")
//    @Operation(summary = "Add a choice for a question")
//    public ResponseEntity<ApiResponse<GetChoiceDto>> addChoice(@PathVariable("id") String id, @RequestBody CreateChoiceDto createChoiceDto) throws Exception {
//        GetChoiceDto getChoiceDto = testService.addChoice(id, createChoiceDto);
//        ApiResponse<GetChoiceDto> res = ApiResponse.<GetChoiceDto>builder().items(getChoiceDto).build();
//        return ResponseEntity.ok(res);
//    }

    @DeleteMapping("/questions/{id}")
    @Operation(summary = "Delete a question")
    public ResponseEntity<ApiResponse<?>> deleteQuestion(@PathVariable("id") String id) throws Exception {
        testService.deleteQuestion(id);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

//    @DeleteMapping("/questions/choices/{id}")
//    @Operation(summary = "Delete a choice")
//    public ResponseEntity<ApiResponse<?>> deleteChoice(@PathVariable("id") String id) throws Exception {
//        testService.deleteChoice(id);
//        return ResponseEntity.ok(ApiResponse.builder().build());
//    }

    @PatchMapping("/questions/{id}")
    @Operation(summary = "Update a question")
    public ResponseEntity<ApiResponse<GetQuestionDto>> updateQuestion(@PathVariable("id") String id, @RequestBody UpdateQuestionDto updateQuestionDto) throws Exception {
        GetQuestionDto getQuestionDto = testService.updateQuestion(id, updateQuestionDto);
        ApiResponse<GetQuestionDto> res = ApiResponse.<GetQuestionDto>builder().items(getQuestionDto).build();
        return ResponseEntity.ok(res);
    }

//    @PatchMapping("/questions/choices/{id}")
//    @Operation(summary = "Update a choice")
//    public ResponseEntity<ApiResponse<GetChoiceDto>> updateChoice(@PathVariable("id") String choiceId, @RequestBody CreateChoiceDto createChoiceDto) throws Exception {
//        GetChoiceDto getChoiceDto = testService.updateChoice(choiceId, createChoiceDto);
//        ApiResponse<GetChoiceDto> res = ApiResponse.<GetChoiceDto>builder().items(getChoiceDto).build();
//        return ResponseEntity.ok(res);
//    }
}
