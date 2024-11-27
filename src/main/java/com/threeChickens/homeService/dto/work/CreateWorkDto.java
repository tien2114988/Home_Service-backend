package com.threeChickens.homeService.dto.work;

import com.threeChickens.homeService.dto.test.CreateTestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkDto {
    private CreateTestDto createTestDto;
}
