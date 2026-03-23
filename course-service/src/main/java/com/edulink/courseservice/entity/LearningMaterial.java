package com.edulink.courseservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "learning_materials")
public class LearningMaterial {
    @Id private String courseCode;
    private String teacherEmail;
    private String title;
    private String description;
    private String fileUrl;
    private String materialType; // PDF, VIDEO, LINK, DOCUMENT
    private LocalDateTime uploadedAt;

    public LearningMaterial() {
    }

    public LearningMaterial(String courseCode, String teacherEmail, String title, String description,
                            String fileUrl, String materialType, LocalDateTime uploadedAt) {
        this.courseCode = courseCode;
        this.teacherEmail = teacherEmail;
        this.title = title;
        this.description = description;
        this.fileUrl = fileUrl;
        this.materialType = materialType;
        this.uploadedAt = uploadedAt;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    @PrePersist protected void onCreate() { uploadedAt = LocalDateTime.now(); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String courseCode;
        private String teacherEmail;
        private String title;
        private String description;
        private String fileUrl;
        private String materialType;
        private LocalDateTime uploadedAt;

        public Builder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
        public Builder teacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder fileUrl(String fileUrl) { this.fileUrl = fileUrl; return this; }
        public Builder materialType(String materialType) { this.materialType = materialType; return this; }
        public Builder uploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; return this; }
        public LearningMaterial build() {
            return new LearningMaterial(courseCode, teacherEmail, title, description, fileUrl, materialType, uploadedAt);
        }
    }
}
