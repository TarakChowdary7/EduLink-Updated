package com.edulink.studentservice.client;

import com.edulink.studentservice.dto.ApiResponse;
import com.edulink.studentservice.dto.LearningMaterialDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class CourseServiceClient {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceClient.class);

    @Value("${course.service.url:http://localhost:8083}")
    private String courseServiceUrl;

    private final RestTemplate restTemplate;

    public CourseServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LearningMaterialDto> getMaterialsByCourseCode(String courseCode, String token) {
        try {
            String url = courseServiceUrl + "/student/materials/" + courseCode;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<ApiResponse<List<LearningMaterialDto>>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<ApiResponse<List<LearningMaterialDto>>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getData();
            }
        } catch (Exception e) {
            log.error("Failed to fetch materials for course: {}", courseCode, e);
        }
        return List.of();
    }

    public List<LearningMaterialDto> getAssignmentsByCourseCode(String courseCode, String token) {
        try {
            String url = courseServiceUrl + "/student/assignments/" + courseCode;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<ApiResponse<List<LearningMaterialDto>>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<ApiResponse<List<LearningMaterialDto>>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getData();
            }
        } catch (Exception e) {
            log.error("Failed to fetch assignments for course: {}", courseCode, e);
        }
        return List.of();
    }

    public boolean courseExists(Long courseId, String token) {
        try {
            String url = courseServiceUrl + "/student/courses/" + courseId; // Assuming course-service has /student/courses/{id} to get course
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("Failed to check if course exists: {}", courseId, e);
            return false;
        }
    }

    public Long getCourseIdByCode(String courseCode, String token) {
        try {
            String url = courseServiceUrl + "/internal/course/by-code/" + courseCode;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object id = response.getBody().get("id");
                if (id instanceof Number) {
                    return ((Number) id).longValue();
                }
            }
        } catch (Exception e) {
            log.error("Failed to get course ID by code: {}", courseCode, e);
        }
        return null;
    }
}