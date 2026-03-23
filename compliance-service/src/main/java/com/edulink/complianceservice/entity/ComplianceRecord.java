package com.edulink.complianceservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name = "compliance_records")
public class ComplianceRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String schoolId; private String auditType; private String auditorEmail;
    private String status; // COMPLIANT, NON_COMPLIANT, UNDER_REVIEW
    private String findings; private String recommendations;
    private LocalDateTime auditDate; private LocalDateTime createdAt;

    public ComplianceRecord() {}

    public ComplianceRecord(Long id, String schoolId, String auditType, String auditorEmail, String status, String findings, String recommendations, LocalDateTime auditDate, LocalDateTime createdAt) {
        this.id = id;
        this.schoolId = schoolId;
        this.auditType = auditType;
        this.auditorEmail = auditorEmail;
        this.status = status;
        this.findings = findings;
        this.recommendations = recommendations;
        this.auditDate = auditDate;
        this.createdAt = createdAt;
    }

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); auditDate = LocalDateTime.now(); }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }

    public String getAuditType() { return auditType; }
    public void setAuditType(String auditType) { this.auditType = auditType; }

    public String getAuditorEmail() { return auditorEmail; }
    public void setAuditorEmail(String auditorEmail) { this.auditorEmail = auditorEmail; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFindings() { return findings; }
    public void setFindings(String findings) { this.findings = findings; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public LocalDateTime getAuditDate() { return auditDate; }
    public void setAuditDate(LocalDateTime auditDate) { this.auditDate = auditDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
