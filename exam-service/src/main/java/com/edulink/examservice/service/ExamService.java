package com.edulink.examservice.service;

import com.edulink.examservice.entity.Exam;
import com.edulink.examservice.repository.ExamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;

@Service
public class ExamService {
    private static final Logger log = LoggerFactory.getLogger(ExamService.class);

    private final ExamRepository examRepository;
    private final RestTemplate restTemplate;

    @Value("${course.service.url:http://localhost:8083}")
    private String courseServiceUrl;

    public ExamService(ExamRepository examRepository, RestTemplate restTemplate) {
        this.examRepository = examRepository;
        this.restTemplate = restTemplate;
    }

    public Exam createExam(Exam exam) {
        // Set teacherEmail from JWT token
        String teacherEmail = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        exam.setTeacherEmail(teacherEmail);

        // Check if course exists. Do not block creation if integration auth/connectivity fails.
        try {
            restTemplate.getForObject(courseServiceUrl + "/internal/course/by-code/" + exam.getCourseCode(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Course not found with code: " + exam.getCourseCode());
        } catch (Exception e) {
            log.warn("Skipping strict course validation due to integration issue: {}", e.getMessage());
        }
        return examRepository.save(exam);
    }

    public List<Exam> getExamsByCourseId(Long courseId) {
        // Since courseId is not stored, this method may need to be updated to take courseCode
        // For now, return empty list or implement logic to get courseCode from course-service
        return List.of();
    }

    public List<Exam> getExamsBySchoolId(String schoolId) {
        return examRepository.findBySchoolId(schoolId);
    }
}