package com.edulink.studentservice.controller;

import com.edulink.studentservice.client.CourseServiceClient;
import com.edulink.studentservice.client.ExamServiceClient;
import com.edulink.studentservice.client.AttendanceServiceClient;
import com.edulink.studentservice.dto.*;
import com.edulink.studentservice.entity.*;
import com.edulink.studentservice.service.StudentService;
import com.edulink.studentservice.util.JwtExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// ...existing code...
@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final JwtExtractor jwtExtractor;
    private final CourseServiceClient courseServiceClient;
    private final ExamServiceClient examServiceClient;
    private final AttendanceServiceClient attendanceServiceClient;

    public StudentController(StudentService studentService,
                             JwtExtractor jwtExtractor,
                             CourseServiceClient courseServiceClient,
                             ExamServiceClient examServiceClient,
                             AttendanceServiceClient attendanceServiceClient) {
        this.studentService = studentService;
        this.jwtExtractor = jwtExtractor;
        this.courseServiceClient = courseServiceClient;
        this.examServiceClient = examServiceClient;
        this.attendanceServiceClient = attendanceServiceClient;
    }

    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getCourses(HttpServletRequest req) {
        String email = jwtExtractor.extractEmail(req);
        return ResponseEntity.ok(ApiResponse.success("Courses retrieved", studentService.getEnrolledCoursesByEmail(email)));
    }

    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse<Void>> enrollInCourse(HttpServletRequest req, @RequestBody Map<String, String> request) {
        String email = jwtExtractor.extractEmail(req);
        String courseCode = request.get("courseCode");
        if (courseCode == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("courseCode is required"));
        }
        String token = jwtExtractor.extractToken(req);
        studentService.enrollInCourseByEmailAndCode(email, courseCode, courseServiceClient, token);
        return ResponseEntity.ok(ApiResponse.success("Enrolled in course successfully", null));
    }

    @GetMapping("/materials/{courseCode}")
    public ResponseEntity<ApiResponse<List<LearningMaterialDto>>> getMaterials(HttpServletRequest req, @PathVariable String courseCode) {
        String token = jwtExtractor.extractToken(req);
        List<LearningMaterialDto> materials = courseServiceClient.getMaterialsByCourseCode(courseCode, token);
        return ResponseEntity.ok(ApiResponse.success("Materials retrieved", materials));
    }

    @GetMapping("/assignments/{courseCode}")
    public ResponseEntity<ApiResponse<List<LearningMaterialDto>>> getAssignments(HttpServletRequest req, @PathVariable String courseCode) {
        String token = jwtExtractor.extractToken(req);
        List<LearningMaterialDto> assignments = courseServiceClient.getAssignmentsByCourseCode(courseCode, token);
        return ResponseEntity.ok(ApiResponse.success("Assignments retrieved", assignments));
    }

    @PostMapping("/assignments/upload")
    public ResponseEntity<ApiResponse<AssignmentSubmission>> submitAssignment(
            HttpServletRequest req, @Valid @RequestBody SubmitAssignmentRequest request) {
        String email = jwtExtractor.extractEmail(req);
        AssignmentSubmission sub = studentService.submitAssignmentByEmail(email, request);
        return ResponseEntity.ok(ApiResponse.success("Assignment submitted successfully", sub));
    }

    @GetMapping("/grades")
    public ResponseEntity<ApiResponse<Object>> getGrades(HttpServletRequest req) {
        String email = jwtExtractor.extractEmail(req);
        String token = jwtExtractor.extractToken(req);
        Long studentId = studentService.getStudentProfileByEmail(email).getId();
        ApiResponse<List<GradeDto>> response = examServiceClient.getStudentGrades("Bearer " + token, studentId);
        return ResponseEntity.ok(ApiResponse.success("Grades for student " + email, response.getData()));
    }

    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse<Object>> getAttendance(HttpServletRequest req) {
        String email = jwtExtractor.extractEmail(req);
        String token = jwtExtractor.extractToken(req);
        Long studentId = studentService.getStudentProfileByEmail(email).getId();
        ApiResponse<List<AttendanceDto>> response = attendanceServiceClient.getStudentAttendance("Bearer " + token, studentId);
        return ResponseEntity.ok(ApiResponse.success("Attendance for student " + email, response.getData()));
    }

    @PostMapping("/profile")
    public ResponseEntity<?> createStudentProfile(@Valid @RequestBody CreateStudentProfileRequest request) {
        log.info("Received request to create student profile for userId: {}", request.getUserId());
        StudentProfile profile = StudentProfile.builder()
                .userId(request.getUserId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .schoolId(request.getSchoolId())
                .classId(request.getClassId())
                .build();
        StudentProfile saved = studentService.createProfile(profile);
        log.info("Student profile created with id: {} for userId: {}", saved.getId(), request.getUserId());
        return ResponseEntity.ok().build();
    }
}
