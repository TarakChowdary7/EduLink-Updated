package com.edulink.courseservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique = true) private String courseCode;
    private String courseName;
    private String description;
    private String schoolId;
    private Long teacherId;
    private String subject;
    private String grade;
    private boolean active = true;
    private LocalDateTime createdAt;

    public Course() {
    }

    public Course(Long id, String courseCode, String courseName, String description, String schoolId,
                  Long teacherId, String subject, String grade, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.schoolId = schoolId;
        this.teacherId = teacherId;
        this.subject = subject;
        this.grade = grade;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String courseCode;
        private String courseName;
        private String description;
        private String schoolId;
        private Long teacherId;
        private String subject;
        private String grade;
        private boolean active = true;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public Builder courseName(String courseName) { this.courseName = courseName; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder schoolId(String schoolId) { this.schoolId = schoolId; return this; }
        public Builder teacherId(Long teacherId) { this.teacherId = teacherId; return this; }
        public Builder subject(String subject) { this.subject = subject; return this; }
        public Builder grade(String grade) { this.grade = grade; return this; }
        public Builder active(boolean active) { this.active = active; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Course build() {
            return new Course(id, courseCode, courseName, description, schoolId, teacherId, subject, grade, active, createdAt);
        }
    }
}
