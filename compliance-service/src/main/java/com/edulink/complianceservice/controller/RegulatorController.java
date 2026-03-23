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
@RequestMapping("/regulator")
@PreAuthorize("hasRole('REGULATOR')")
public class RegulatorController {

    @Autowired
    private final ComplianceRecordRepository repo;

    public RegulatorController(ComplianceRecordRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/compliance-reports")
    public ResponseEntity<ApiResponse<List<ComplianceRecord>>> complianceReports() {
        return ResponseEntity.ok(ApiResponse.success("Compliance reports", repo.findAll()));
    }

    @GetMapping("/accreditation-status")
    public ResponseEntity<ApiResponse<Object>> accreditationStatus() {
        List<Map<String,Object>> acc = List.of(
            Map.of("schoolId","SCH001","school","Greenwood High School","status","ACCREDITED","validUntil","2026-12-31"),
            Map.of("schoolId","SCH002","school","Sunrise Academy","status","ACCREDITED","validUntil","2025-08-31"));
        return ResponseEntity.ok(ApiResponse.success("Accreditation status", acc));
    }

    @GetMapping("/system-audit")
    public ResponseEntity<ApiResponse<Object>> systemAudit() {
        return ResponseEntity.ok(ApiResponse.success("System audit", Map.of(
            "totalAudits", repo.count(),
            "compliantSchools", repo.findByStatus("COMPLIANT").size(),
            "nonCompliantSchools", repo.findByStatus("NON_COMPLIANT").size())));
    }
}
