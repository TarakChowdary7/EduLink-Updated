package com.edulink.examservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
public class Exam {
    @Id
    private String courseCode;
    private String teacherEmail;
    private String examTitle;
    private String examType;
    private int totalMarks;
    private int passingMarks;
    private String schoolId;
    private LocalDateTime examDate;
    private LocalDateTime createdAt;

    public Exam() {
    }

    public Exam(String courseCode, String teacherEmail, String examTitle, String examType,
                int totalMarks, int passingMarks, String schoolId, LocalDateTime examDate,
                LocalDateTime createdAt) {
        this.courseCode = courseCode;
        this.teacherEmail = teacherEmail;
        this.examTitle = examTitle;
        this.examType = examType;
        this.totalMarks = totalMarks;
        this.passingMarks = passingMarks;
        this.schoolId = schoolId;
        this.examDate = examDate;
        this.createdAt = createdAt;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }
    public String getExamTitle() { return examTitle; }
    public void setExamTitle(String examTitle) { this.examTitle = examTitle; }
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }
    public int getPassingMarks() { return passingMarks; }
    public void setPassingMarks(int passingMarks) { this.passingMarks = passingMarks; }
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public LocalDateTime getExamDate() { return examDate; }
    public void setExamDate(LocalDateTime examDate) { this.examDate = examDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String courseCode;
        private String teacherEmail;
        private String examTitle;
        private String examType;
        private int totalMarks;
        private int passingMarks;
        private String schoolId;
        private LocalDateTime examDate;
        private LocalDateTime createdAt;

        public Builder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public Builder teacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; return this; }
        public Builder examTitle(String examTitle) { this.examTitle = examTitle; return this; }
        public Builder examType(String examType) { this.examType = examType; return this; }
        public Builder totalMarks(int totalMarks) { this.totalMarks = totalMarks; return this; }
        public Builder passingMarks(int passingMarks) { this.passingMarks = passingMarks; return this; }
        public Builder schoolId(String schoolId) { this.schoolId = schoolId; return this; }
        public Builder examDate(LocalDateTime examDate) { this.examDate = examDate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Exam build() {
            return new Exam(courseCode, teacherEmail, examTitle, examType, totalMarks, passingMarks, schoolId, examDate, createdAt);
        }
    }
}
