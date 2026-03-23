package com.edulink.examservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String examId;
    private Long studentId;
    private String teacherEmail;
    private int marksObtained;
    private int totalMarks;
    private String grade;
    private String remarks;
    private LocalDateTime gradedAt;

    public Grade() {
    }

    public Grade(Long id, String examId, Long studentId, String teacherEmail, int marksObtained,
                 int totalMarks, String grade, String remarks, LocalDateTime gradedAt) {
        this.id = id;
        this.examId = examId;
        this.studentId = studentId;
        this.teacherEmail = teacherEmail;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.grade = grade;
        this.remarks = remarks;
        this.gradedAt = gradedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }
    public int getMarksObtained() { return marksObtained; }
    public void setMarksObtained(int marksObtained) { this.marksObtained = marksObtained; }
    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getGradedAt() { return gradedAt; }
    public void setGradedAt(LocalDateTime gradedAt) { this.gradedAt = gradedAt; }

    @PrePersist
    protected void onCreate() { gradedAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String examId;
        private Long studentId;
        private String teacherEmail;
        private int marksObtained;
        private int totalMarks;
        private String grade;
        private String remarks;
        private LocalDateTime gradedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder examId(String examId) { this.examId = examId; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder teacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; return this; }
        public Builder marksObtained(int marksObtained) { this.marksObtained = marksObtained; return this; }
        public Builder totalMarks(int totalMarks) { this.totalMarks = totalMarks; return this; }
        public Builder grade(String grade) { this.grade = grade; return this; }
        public Builder remarks(String remarks) { this.remarks = remarks; return this; }
        public Builder gradedAt(LocalDateTime gradedAt) { this.gradedAt = gradedAt; return this; }

        public Grade build() {
            return new Grade(id, examId, studentId, teacherEmail, marksObtained, totalMarks, grade, remarks, gradedAt);
        }
    }
}
