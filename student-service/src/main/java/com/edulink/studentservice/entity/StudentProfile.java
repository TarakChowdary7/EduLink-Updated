package com.edulink.studentservice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userId;
    private String studentCode;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String grade;
    private String section;
    private String schoolId;
    private String parentName;
    private String parentContact;
    private LocalDateTime enrolledAt;
    private Long classId;

    public StudentProfile() {
    }

    public StudentProfile(Long id, String userId, String studentCode, String fullName, String email,
                          LocalDate dateOfBirth, String grade, String section, String schoolId,
                          String parentName, String parentContact, LocalDateTime enrolledAt, Long classId) {
        this.id = id;
        this.userId = userId;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.grade = grade;
        this.section = section;
        this.schoolId = schoolId;
        this.parentName = parentName;
        this.parentContact = parentContact;
        this.enrolledAt = enrolledAt;
        this.classId = classId;
    }
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }
    public String getParentContact() { return parentContact; }
    public void setParentContact(String parentContact) { this.parentContact = parentContact; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    @PrePersist protected void onCreate() { enrolledAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String userId;
        private String studentCode;
        private String fullName;
        private String email;
        private LocalDate dateOfBirth;
        private String grade;
        private String section;
        private String schoolId;
        private String parentName;
        private String parentContact;
        private LocalDateTime enrolledAt;
        private Long classId;
    public Builder classId(Long classId) { this.classId = classId; return this; }

        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder studentCode(String studentCode) { this.studentCode = studentCode; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder dateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }
        public Builder grade(String grade) { this.grade = grade; return this; }
        public Builder section(String section) { this.section = section; return this; }
        public Builder schoolId(String schoolId) { this.schoolId = schoolId; return this; }
        public Builder parentName(String parentName) { this.parentName = parentName; return this; }
        public Builder parentContact(String parentContact) { this.parentContact = parentContact; return this; }
        public Builder enrolledAt(LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; return this; }
        public StudentProfile build() {
            return new StudentProfile(id, userId, studentCode, fullName, email,
                dateOfBirth, grade, section, schoolId, parentName, parentContact, enrolledAt, classId);
        }
    }
}
