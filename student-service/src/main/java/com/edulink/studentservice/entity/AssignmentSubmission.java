package com.edulink.studentservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment_submissions")
public class AssignmentSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long assignmentId;
    private String assignmentTitle;
    private String submissionContent;
    private String fileUrl;
    private String status; // SUBMITTED, GRADED, LATE
    private LocalDateTime submittedAt;

    public AssignmentSubmission() {
    }

    public AssignmentSubmission(Long id, Long studentId, Long assignmentId, String assignmentTitle,
                                String submissionContent, String fileUrl, String status,
                                LocalDateTime submittedAt) {
        this.id = id;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.submissionContent = submissionContent;
        this.fileUrl = fileUrl;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }
    public String getAssignmentTitle() { return assignmentTitle; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }
    public String getSubmissionContent() { return submissionContent; }
    public void setSubmissionContent(String submissionContent) { this.submissionContent = submissionContent; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    @PrePersist protected void onCreate() { submittedAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long studentId;
        private Long assignmentId;
        private String assignmentTitle;
        private String submissionContent;
        private String fileUrl;
        private String status;
        private LocalDateTime submittedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder assignmentId(Long assignmentId) { this.assignmentId = assignmentId; return this; }
        public Builder assignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; return this; }
        public Builder submissionContent(String submissionContent) { this.submissionContent = submissionContent; return this; }
        public Builder fileUrl(String fileUrl) { this.fileUrl = fileUrl; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder submittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; return this; }
        public AssignmentSubmission build() {
            return new AssignmentSubmission(id, studentId, assignmentId, assignmentTitle,
                    submissionContent, fileUrl, status, submittedAt);
        }
    }
}
