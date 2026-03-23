package com.edulink.notificationservice.controller;
import com.edulink.notificationservice.dto.ApiResponse;
import com.edulink.notificationservice.entity.Notification;
import com.edulink.notificationservice.repository.NotificationRepository;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationRepository repo;

    public NotificationController(NotificationRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('TEACHER','SCHOOL_ADMIN')")
    public ResponseEntity<ApiResponse<Notification>> sendNotification(@RequestBody Notification notification) {
        log.info("Sending notification to: {} (id: {}, role: {})", notification.getRecipientEmail(), notification.getRecipientId(), notification.getRecipientRole());
        Notification saved = repo.save(notification);
        log.info("Notification saved with id: {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification sent", saved));
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<Notification>>> getMyNotifications(Authentication auth) {
        log.info("Fetching notifications for user: {}", auth.getName());
        List<Notification> notifications = repo.findByRecipientEmailAndReadStatusFalse(auth.getName());
        log.info("Found {} notifications by email", notifications.size());
        if (notifications.isEmpty()) {
            Object userId = auth.getDetails();
            if (userId != null) {
                notifications = repo.findByRecipientIdAndReadStatusFalse(String.valueOf(userId));
                log.info("Fallback: found {} notifications by id", notifications.size());
            }
        }
        return ResponseEntity.ok(ApiResponse.success("Notifications", notifications));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Notification>> markRead(@PathVariable Long id) {
        return repo.findById(id).map(n -> { n.setReadStatus(true); return ResponseEntity.ok(ApiResponse.success("Marked as read", repo.save(n))); })
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
