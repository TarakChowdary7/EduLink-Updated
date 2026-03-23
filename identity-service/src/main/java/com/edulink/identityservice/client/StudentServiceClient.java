package com.edulink.identityservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentServiceClient {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceClient.class);
    private final RestTemplate restTemplate;
    private final String studentServiceUrl = "http://localhost:8082/student/profile"; // Adjust port as needed

    public StudentServiceClient() {
        this.restTemplate = new RestTemplate();
    }

    public void createStudentProfile(String userId, String fullName, String email, String schoolId, Long classId) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("userId", userId);
            request.put("fullName", fullName);
            request.put("email", email);
            request.put("schoolId", schoolId);
            request.put("classId", classId);
            log.info("Calling student-service to create profile for userId: {}", userId);
            ResponseEntity<Void> response = restTemplate.postForEntity(studentServiceUrl, request, Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Student profile created successfully for userId: {}", userId);
            } else {
                log.error("Failed to create student profile for userId: {}, status: {}", userId, response.getStatusCode());
            }
        } catch (RestClientException e) {
            log.error("Error calling student-service for userId: {}", userId, e);
        }
    }
}
