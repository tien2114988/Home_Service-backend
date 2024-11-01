package com.threeChickens.homeService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.threeChickens.homeService.exception.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    @Builder.Default
    private int returnCode = StatusCode.SUCCESS.getCode();

    @Builder.Default
    private String message = StatusCode.SUCCESS.getMessage();

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int count;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private T items;
}
