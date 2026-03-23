package com.edulink.identityservice.dto;

import com.edulink.identityservice.entity.Role;
import java.time.LocalDateTime;

public class UserResponse {
    private String id;
    private String email;
    private String fullName;
    private Role role;
    private boolean active;
    private boolean mustChangePassword;
    private String temporaryPassword;
    private String schoolId;
    private Long classId;
    private LocalDateTime createdAt;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String email;
        private String fullName;
        private Role role;
        private boolean active;
        private boolean mustChangePassword;
        private String temporaryPassword;
        private String schoolId;
        private Long classId;
        private LocalDateTime createdAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder mustChangePassword(boolean mustChangePassword) {
            this.mustChangePassword = mustChangePassword;
            return this;
        }

        public Builder temporaryPassword(String temporaryPassword) {
            this.temporaryPassword = temporaryPassword;
            return this;
        }

        public Builder schoolId(String schoolId) {
            this.schoolId = schoolId;
            return this;
        }

        public Builder classId(Long classId) {
            this.classId = classId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserResponse build() {
            UserResponse response = new UserResponse();
            response.id = this.id;
            response.email = this.email;
            response.fullName = this.fullName;
            response.role = this.role;
            response.active = this.active;
            response.mustChangePassword = this.mustChangePassword;
            response.temporaryPassword = this.temporaryPassword;
            response.schoolId = this.schoolId;
            response.classId = this.classId;
            response.createdAt = this.createdAt;
            return response;
        }
    }
}
