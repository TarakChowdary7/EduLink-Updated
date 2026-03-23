package com.edulink.studentservice.service;

import com.edulink.studentservice.client.CourseServiceClient;
import com.edulink.studentservice.dto.SubmitAssignmentRequest;
import com.edulink.studentservice.entity.*;
import com.edulink.studentservice.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentProfileRepository profileRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final AssignmentSubmissionRepository submissionRepo;

    public StudentService(StudentProfileRepository profileRepo,
                          EnrollmentRepository enrollmentRepo,
                          AssignmentSubmissionRepository submissionRepo) {
        this.profileRepo = profileRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.submissionRepo = submissionRepo;
    }

    public StudentProfile getStudentProfile(String userId) {
        return profileRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
    }

    public StudentProfile getStudentProfileByEmail(String email) {
        return profileRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
    }

    public List<Enrollment> getEnrolledCoursesByEmail(String email) {
        StudentProfile profile = getStudentProfileByEmail(email);
        return enrollmentRepo.findByStudentId(profile.getId());
    }

    public AssignmentSubmission submitAssignmentByEmail(String email, SubmitAssignmentRequest request) {
        StudentProfile profile = getStudentProfileByEmail(email);
        // Check if student is enrolled in the course
        if (request.getCourseId() != null) {
            List<Enrollment> enrollments = enrollmentRepo.findByStudentId(profile.getId());
            boolean enrolled = enrollments.stream().anyMatch(e -> e.getCourseId().equals(request.getCourseId()));
            if (!enrolled) {
                throw new RuntimeException("Student is not enrolled in the specified course");
            }
        }
        AssignmentSubmission submission = AssignmentSubmission.builder()
                .studentId(profile.getId())
                .assignmentId(request.getAssignmentId())
                .assignmentTitle(request.getAssignmentTitle())
                .submissionContent(request.getSubmissionContent())
                .fileUrl(request.getFileUrl())
                .status("SUBMITTED")
                .build();
        return submissionRepo.save(submission);
    }

    public void enrollInCourse(String userId, Long courseId) {
        StudentProfile profile = getStudentProfile(userId);
        // Check if already enrolled
        List<Enrollment> enrollments = enrollmentRepo.findByStudentId(profile.getId());
        boolean alreadyEnrolled = enrollments.stream().anyMatch(e -> e.getCourseId().equals(courseId));
        if (alreadyEnrolled) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        // TODO: Optionally validate course exists by calling course-service
        Enrollment enrollment = Enrollment.builder()
                .studentId(profile.getId())
                .courseId(courseId)
                .enrolledAt(java.time.LocalDateTime.now())
                .build();
        enrollmentRepo.save(enrollment);
    }

    public void enrollInCourseByEmail(String email, Long courseId) {
        StudentProfile profile = getStudentProfileByEmail(email);
        // Check if already enrolled
        List<Enrollment> enrollments = enrollmentRepo.findByStudentId(profile.getId());
        boolean alreadyEnrolled = enrollments.stream().anyMatch(e -> e.getCourseId().equals(courseId));
        if (alreadyEnrolled) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        // TODO: Optionally validate course exists by calling course-service
        Enrollment enrollment = Enrollment.builder()
                .studentId(profile.getId())
                .courseId(courseId)
                .enrolledAt(java.time.LocalDateTime.now())
                .build();
        enrollmentRepo.save(enrollment);
    }

    public void enrollInCourseByEmailAndCode(String email, String courseCode, CourseServiceClient courseServiceClient, String token) {
        StudentProfile profile = getStudentProfileByEmail(email);
        // Get courseId from courseCode
        Long courseId = courseServiceClient.getCourseIdByCode(courseCode, token);
        if (courseId == null) {
            throw new RuntimeException("Course not found");
        }
        // Check if already enrolled
        List<Enrollment> enrollments = enrollmentRepo.findByStudentId(profile.getId());
        boolean alreadyEnrolled = enrollments.stream().anyMatch(e -> e.getCourseId().equals(courseId));
        if (alreadyEnrolled) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        Enrollment enrollment = Enrollment.builder()
                .studentId(profile.getId())
                .courseId(courseId)
                .enrolledAt(java.time.LocalDateTime.now())
                .build();
        enrollmentRepo.save(enrollment);
    }

    public List<AssignmentSubmission> getSubmissions(String userId) {
        StudentProfile profile = getStudentProfile(userId);
        return submissionRepo.findByStudentId(profile.getId());
    }

    public StudentProfile createProfile(StudentProfile profile) {
        log.info("Saving student profile for userId: {}", profile.getUserId());

        profileRepo.findByUserId(profile.getUserId()).ifPresent(existing -> profile.setId(existing.getId()));
        if (profile.getId() == null && profile.getEmail() != null) {
            profileRepo.findByEmail(profile.getEmail()).ifPresent(existing -> profile.setId(existing.getId()));
        }

        StudentProfile saved = profileRepo.save(profile);
        log.info("Saved student profile with id: {}", saved.getId());
        return saved;
    }

    // Admin methods
    public List<StudentProfile> getAllStudentsBySchool(String schoolId) {
        return profileRepo.findBySchoolId(schoolId);
    }
}
