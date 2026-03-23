package com.edulink.complianceservice.controller;
import com.edulink.complianceservice.dto.ApiResponse;
import com.edulink.complianceservice.dto.CreateSchoolRequest;
import com.edulink.complianceservice.entity.ComplianceRecord;
import com.edulink.complianceservice.entity.School;
import com.edulink.complianceservice.repository.ComplianceRecordRepository;
import com.edulink.complianceservice.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/compliance")
public class ComplianceController {

    @Autowired
    private final ComplianceRecordRepository repo;

    @Autowired
    private final SchoolService schoolService;

    public ComplianceController(ComplianceRecordRepository repo, SchoolService schoolService) {
        this.repo = repo;
        this.schoolService = schoolService;
    }

    @PostMapping("/create-school")
    @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    public ResponseEntity<ApiResponse<School>> createSchool(@RequestBody CreateSchoolRequest request) {
        School school = schoolService.createSchool(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("School created successfully", school));
    }

    @GetMapping("/schools")
    @PreAuthorize("hasRole('COMPLIANCE_OFFICER') or hasRole('EDUCATION_BOARD_OFFICER') or hasRole('REGULATOR')")
    public ResponseEntity<ApiResponse<List<School>>> getAllSchools() {
        return ResponseEntity.ok(ApiResponse.success("Schools retrieved successfully", schoolService.getAllSchools()));
    }

    @GetMapping("/schools/{schoolId}")
    @PreAuthorize("hasRole('COMPLIANCE_OFFICER') or hasRole('EDUCATION_BOARD_OFFICER') or hasRole('REGULATOR')")
    public ResponseEntity<ApiResponse<School>> getSchoolById(@PathVariable String schoolId) {
        School school = schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(ApiResponse.success("School retrieved successfully", school));
    }

    @PostMapping("/audit-school")
    @PreAuthorize("hasRole('COMPLIANCE_OFFICER')")
    public ResponseEntity<ApiResponse<ComplianceRecord>> auditSchool(
            Authentication auth, @RequestBody ComplianceRecord record) {
        record.setAuditorEmail(auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("School audited", repo.save(record)));
    }

    @GetMapping("/compliance-status")
    @PreAuthorize("hasRole('COMPLIANCE_OFFICER') or hasRole('REGULATOR')")
    public ResponseEntity<ApiResponse<List<ComplianceRecord>>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Compliance status", repo.findAll()));
    }

    @GetMapping("/audit-records")
    @PreAuthorize("hasRole('COMPLIANCE_OFFICER') or hasRole('REGULATOR')")
    public ResponseEntity<ApiResponse<List<ComplianceRecord>>> getAuditRecords() {
        return ResponseEntity.ok(ApiResponse.success("Audit records", repo.findAll()));
    }
}
