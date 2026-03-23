package com.edulink.studentservice.client;

import com.edulink.studentservice.dto.ApiResponse;
import com.edulink.studentservice.dto.GradeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "exam-service", url = "${exam.service.url:http://localhost:8084}")
public interface ExamServiceClient {

    @GetMapping("/student/grades")
    ApiResponse<List<GradeDto>> getStudentGrades(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("studentId") Long studentId);
}
