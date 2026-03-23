package com.edulink.studentservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long courseId;
    private String courseName;
    private String courseCode;
    private String status; // ACTIVE, COMPLETED, DROPPED
    private LocalDateTime enrolledAt;

    public Enrollment() {
    }

    public Enrollment(Long id, Long studentId, Long courseId, String courseName,
                      String courseCode, String status, LocalDateTime enrolledAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.status = status;
        this.enrolledAt = enrolledAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    @PrePersist protected void onCreate() { enrolledAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long studentId;
        private Long courseId;
        private String courseName;
        private String courseCode;
        private String status;
        private LocalDateTime enrolledAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder courseName(String courseName) { this.courseName = courseName; return this; }
        public Builder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder enrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; return this; }
        public Enrollment build() {
            return new Enrollment(id, studentId, courseId, courseName, courseCode, status, enrolledAt);
        }
    }
}
