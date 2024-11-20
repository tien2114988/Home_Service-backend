package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.test.CreateTestDto;
import com.threeChickens.homeService.dto.test.GetTestDto;
import com.threeChickens.homeService.entity.Test;
import com.threeChickens.homeService.entity.Work;
import com.threeChickens.homeService.repository.TestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ModelMapper modelMapper;

    public GetTestDto createTest(Work work, CreateTestDto createTestDto){
        Test test;
        if(work.getTest()!=null){
            test = work.getTest();
            modelMapper.map(createTestDto, test);
        }else{
            test = modelMapper.map(createTestDto, Test.class);
            test.setWork(work);
        }
        test = testRepository.save(test);
        return modelMapper.map(test, GetTestDto.class);
    }

}
