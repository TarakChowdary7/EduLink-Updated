package com.edulink.attendanceservice.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long courseId;
    private String schoolId;
    private LocalDate attendanceDate;
    private String status; // PRESENT, ABSENT, LATE, EXCUSED
    private String markedBy;
    private LocalDateTime createdAt;

    public Attendance() {
    }

    public Attendance(Long id, Long studentId, Long courseId, String schoolId,
                      LocalDate attendanceDate, String status, String markedBy,
                      LocalDateTime createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.schoolId = schoolId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.markedBy = markedBy;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMarkedBy() { return markedBy; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long studentId;
        private Long courseId;
        private String schoolId;
        private LocalDate attendanceDate;
        private String status;
        private String markedBy;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder studentId(Long studentId) { this.studentId = studentId; return this; }
        public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
        public Builder schoolId(String schoolId) { this.schoolId = schoolId; return this; }
        public Builder attendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder markedBy(String markedBy) { this.markedBy = markedBy; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Attendance build() {
            return new Attendance(id, studentId, courseId, schoolId, attendanceDate, status, markedBy, createdAt);
        }
    }
}
