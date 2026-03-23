package com.edulink.courseservice.controller;
import com.edulink.courseservice.client.IdentityClient;
import com.edulink.courseservice.dto.ApiResponse;
import com.edulink.courseservice.dto.UserDto;
import com.edulink.courseservice.entity.*;
import com.edulink.courseservice.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/teacher") @PreAuthorize("hasRole('TEACHER')")
public class TeacherController {
    private final CourseService courseService;
    private final IdentityClient identityClient;

    public TeacherController(CourseService courseService, IdentityClient identityClient) {
        this.courseService = courseService;
        this.identityClient = identityClient;
    }

    @GetMapping("/classes")
    public ResponseEntity<ApiResponse<List<ClassRoom>>> getClasses(Authentication auth) {
        String email = auth.getName();
        List<ClassRoom> classes = courseService.getClassesByTeacher(email);
        return ResponseEntity.ok(ApiResponse.success("Classes retrieved", classes));
    }

    @PostMapping("/upload-material")
    public ResponseEntity<ApiResponse<LearningMaterial>> uploadMaterial(@RequestBody LearningMaterial material, Authentication auth) {
        material.setTeacherEmail(auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Material uploaded", courseService.uploadMaterial(material)));
    }

    @PostMapping("/create-assignment")
    public ResponseEntity<ApiResponse<Assignment>> createAssignment(@RequestBody Assignment assignment, Authentication auth) {
        assignment.setTeacherEmail(auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Assignment created", courseService.createAssignment(assignment)));
    }

    @PostMapping("/create-exam")
    public ResponseEntity<ApiResponse<Object>> createExam(@RequestBody Object exam) {
        return ResponseEntity.ok(ApiResponse.success("Exam created (see exam-service)", exam));
    }

    @PostMapping("/grade-student")
    public ResponseEntity<ApiResponse<Object>> gradeStudent(@RequestBody Object gradeRequest) {
        return ResponseEntity.ok(ApiResponse.success("Grade submitted (see exam-service)", gradeRequest));
    }

    @GetMapping("/students/{classId}")
    public ResponseEntity<ApiResponse<List<UserDto>>> getStudents(@PathVariable Long classId, Authentication auth) {
        String email = auth.getName();
        // Get the class to find schoolId
        List<ClassRoom> classes = courseService.getClassesByTeacher(email);
        ClassRoom classRoom = classes.stream()
                .filter(c -> c.getId().equals(classId))
                .findFirst()
                .orElse(null);
        if (classRoom == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Class not found or not assigned to teacher"));
        }
        String schoolId = classRoom.getSchoolId();
        List<UserDto> students = identityClient.getStudentsByClassAndSchool(classId, schoolId);
        return ResponseEntity.ok(ApiResponse.success("Students in class " + classId + " retrieved", students));
    }
}
