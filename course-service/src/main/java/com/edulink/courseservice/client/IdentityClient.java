package com.edulink.courseservice.client;

import com.edulink.courseservice.dto.ApiResponse;
import com.edulink.courseservice.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class IdentityClient {

    private static final Logger log = LoggerFactory.getLogger(IdentityClient.class);

    @Value("${identity.service.url:http://localhost:8081}")
    private String identityServiceUrl;

    private final RestTemplate restTemplate;

    public IdentityClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUserByEmail(String email) {
        try {
            String url = identityServiceUrl + "/auth/user-by-email?email=" + email;
            ResponseEntity<UserDto> response = restTemplate.getForEntity(url, UserDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("Failed to fetch user by email: {}", email, e);
        }
        return null;
    }

    public List<UserDto> getStudentsByClassAndSchool(Long classId, String schoolId) {
        try {
            String url = identityServiceUrl + "/teacher/students-by-class?classId=" + classId + "&schoolId=" + schoolId;
            ResponseEntity<ApiResponse<List<UserDto>>> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, null, new ParameterizedTypeReference<ApiResponse<List<UserDto>>>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().isSuccess()) {
                return response.getBody().getData();
            }
        } catch (Exception e) {
            log.error("Failed to fetch students for class {} in school {}", classId, schoolId, e);
        }
        return null;
    }
}