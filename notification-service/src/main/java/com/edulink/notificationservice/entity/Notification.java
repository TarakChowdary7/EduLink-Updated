package com.edulink.notificationservice.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name = "notifications") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String recipientId; private String recipientEmail; private String recipientRole;
    private String title; private String message; private String type; // INFO, ALERT, WARNING
    @Column(name = "is_read")
    private boolean readStatus;
    private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); readStatus = false; }

    public String getRecipientId() { return recipientId; }
    public String getRecipientEmail() { return recipientEmail; }
    public String getRecipientRole() { return recipientRole; }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }

    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }
    public void setTitle(String title) { this.title = title; }
    public void setMessage(String message) { this.message = message; }
    public void setType(String type) { this.type = type; }
}
