package com.edulink.studentservice.client;

import com.edulink.studentservice.dto.ApiResponse;
import com.edulink.studentservice.dto.AttendanceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "attendance-service", url = "${attendance.service.url:http://localhost:8085}")
public interface AttendanceServiceClient {

    @GetMapping("/student/attendance")
    ApiResponse<List<AttendanceDto>> getStudentAttendance(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("studentId") Long studentId);
}
