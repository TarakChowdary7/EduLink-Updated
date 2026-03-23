package com.edulink.courseservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "classrooms")
public class ClassRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String className;
    private String grade;
    private String section;
    private String schoolId;
    private String teacherEmail;
    private Long courseId;
    private int capacity;
    private LocalDateTime createdAt;

    public ClassRoom() {
    }

    public ClassRoom(Long id, String className, String grade, String section, String schoolId,
                     String teacherEmail, Long courseId, int capacity, LocalDateTime createdAt) {
        this.id = id;
        this.className = className;
        this.grade = grade;
        this.section = section;
        this.schoolId = schoolId;
        this.teacherEmail = teacherEmail;
        this.courseId = courseId;
        this.capacity = capacity;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String className;
        private String grade;
        private String section;
        private String schoolId;
        private String teacherEmail;
        private Long courseId;
        private int capacity;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder className(String className) { this.className = className; return this; }
        public Builder grade(String grade) { this.grade = grade; return this; }
        public Builder section(String section) { this.section = section; return this; }
        public Builder schoolId(String schoolId) { this.schoolId = schoolId; return this; }
        public Builder teacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder capacity(int capacity) { this.capacity = capacity; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ClassRoom build() {
            return new ClassRoom(id, className, grade, section, schoolId, teacherEmail, courseId, capacity, createdAt);
        }
    }
}
