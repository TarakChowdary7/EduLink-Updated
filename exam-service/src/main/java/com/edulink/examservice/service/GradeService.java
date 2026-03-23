package com.edulink.examservice.service;

import com.edulink.examservice.entity.Grade;
import com.edulink.examservice.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public Grade gradeStudent(Grade grade) {
        // Set teacherEmail from JWT token
        String teacherEmail = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
        grade.setTeacherEmail(teacherEmail);

        // Business logic for grading can be added here
        // For example, validation, calculating grade based on marks, etc.
        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesByExamId(String examId) {
        return gradeRepository.findByExamId(examId);
    }
}