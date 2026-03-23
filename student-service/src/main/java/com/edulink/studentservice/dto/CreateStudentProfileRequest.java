package com.edulink.studentservice.dto;

public class CreateStudentProfileRequest {
    private String userId;
    private String fullName;
    private String email;
    private String schoolId;
    private Long classId;
    // Add more fields as needed

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }
}
