package com.edulink.complianceservice.controller;
import com.edulink.complianceservice.dto.ApiResponse;
import com.edulink.complianceservice.entity.ComplianceRecord;
import com.edulink.complianceservice.repository.ComplianceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/board")
@PreAuthorize("hasRole('EDUCATION_BOARD_OFFICER')")
public class BoardController {

    @Autowired
    private final ComplianceRecordRepository complianceRepo;

    public BoardController(ComplianceRecordRepository complianceRepo) {
        this.complianceRepo = complianceRepo;
    }

    @GetMapping("/schools")
    public ResponseEntity<ApiResponse<List<Map<String,Object>>>> getSchools() {
        List<Map<String,Object>> schools = new ArrayList<>();
        schools.add(Map.of("schoolId","SCH001","name","Greenwood High School","city","Chennai","studentsCount",450,"teachersCount",32));
        schools.add(Map.of("schoolId","SCH002","name","Sunrise Academy","city","Mumbai","studentsCount",380,"teachersCount",28));
        return ResponseEntity.ok(ApiResponse.success("Schools retrieved", schools));
    }

    @GetMapping("/academic-performance")
    public ResponseEntity<ApiResponse<Object>> academicPerformance() {
        Map<String,Object> perf = Map.of(
            "averagePassRate","87.5%", "topPerformingSchool","Greenwood High School",
            "overallGradeA","42%", "overallGradeB","35%", "overallGradeC","23%");
        return ResponseEntity.ok(ApiResponse.success("Academic performance", perf));
    }

    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<Object>> getReports() {
        return ResponseEntity.ok(ApiResponse.success("Board reports", Map.of("totalSchools", 2, "totalStudents", 830, "totalTeachers", 60)));
    }

    @GetMapping("/compliance-summary")
    public ResponseEntity<ApiResponse<List<ComplianceRecord>>> complianceSummary() {
        return ResponseEntity.ok(ApiResponse.success("Compliance summary", complianceRepo.findAll()));
    }
}
