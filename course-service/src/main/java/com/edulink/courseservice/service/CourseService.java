package com.edulink.courseservice.service;
import com.edulink.courseservice.entity.*;
import com.edulink.courseservice.repository.*;
import com.edulink.courseservice.exception.ClassAlreadyExistsException;
import com.edulink.courseservice.exception.CourseAlreadyExistsException;
import com.edulink.courseservice.exception.CourseNotFoundException;
import com.edulink.courseservice.exception.SchoolNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final ClassRoomRepository classRepo;
    private final LearningMaterialRepository materialRepo;
    private final AssignmentRepository assignmentRepo;
    private final RestTemplate restTemplate;

    public CourseService(CourseRepository courseRepo, ClassRoomRepository classRepo,
                         LearningMaterialRepository materialRepo, AssignmentRepository assignmentRepo,
                         RestTemplate restTemplate) {
        this.courseRepo = courseRepo;
        this.classRepo = classRepo;
        this.materialRepo = materialRepo;
        this.assignmentRepo = assignmentRepo;
        this.restTemplate = restTemplate;
    }

    public Course createCourse(Course course) {
        if (course.getCourseCode() == null || course.getCourseCode().isEmpty()) {
            throw new IllegalArgumentException("Course code must be provided");
        }

        courseRepo.findByCourseCode(course.getCourseCode()).ifPresent(existing -> {
            throw new CourseAlreadyExistsException(course.getCourseCode());
        });

        if (course.getSchoolId() == null || course.getSchoolId().isEmpty()) {
            throw new IllegalArgumentException("schoolId must be provided in course payload");
        }

        verifySchoolExists(course.getSchoolId());

        return courseRepo.save(course);
    }

    private void verifySchoolExists(String schoolId) {
        String identityUrl = "http://localhost:8081/compliance/identity/schools/" + schoolId;
        try {
            restTemplate.getForEntity(identityUrl, Object.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new SchoolNotFoundException(schoolId);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new SchoolNotFoundException(schoolId);
            }
            throw ex;
        }
    }
    public ClassRoom createClass(ClassRoom c) {
        if (c.getClassName() == null || c.getClassName().isEmpty()) {
            throw new IllegalArgumentException("Class name must be provided");
        }

        if (classRepo.existsByClassName(c.getClassName())) {
            throw new ClassAlreadyExistsException(c.getClassName());
        }

        if (c.getSchoolId() == null || c.getSchoolId().isEmpty()) {
            throw new IllegalArgumentException("schoolId must be provided in class payload");
        }

        verifySchoolExists(c.getSchoolId());

        return classRepo.save(c);
    }
    public LearningMaterial uploadMaterial(LearningMaterial m) {
        if (m.getCourseCode() == null || m.getCourseCode().isEmpty()) {
            throw new IllegalArgumentException("courseCode must be provided when uploading learning material");
        }

        Course course = courseRepo.findByCourseCode(m.getCourseCode())
                .orElseThrow(() -> new CourseNotFoundException(m.getCourseCode()));
        m.setCourseCode(course.getCourseCode());

        return materialRepo.save(m);
    }
    public Assignment createAssignment(Assignment a) {
        courseRepo.findByCourseCode(a.getCourseCode())
                .orElseThrow(() -> new CourseNotFoundException(a.getCourseCode()));
        return assignmentRepo.save(a);
    }

    public List<Course> getCoursesBySchool(String schoolId) { return courseRepo.findBySchoolId(schoolId); }
    public List<ClassRoom> getClassesBySchool(String schoolId) { return classRepo.findBySchoolId(schoolId); }
    public List<ClassRoom> getClassesByTeacher(String teacherEmail) { return classRepo.findByTeacherEmail(teacherEmail); }
    public List<LearningMaterial> getMaterialsByCourse(Long courseId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) return new ArrayList<>();
        return materialRepo.findByCourseCode(course.getCourseCode());
    }
    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) return new ArrayList<>();
        return assignmentRepo.findByCourseCode(course.getCourseCode());
    }
    public List<LearningMaterial> getMaterialsByClassId(Long classId) {
        ClassRoom classRoom = classRepo.findById(classId).orElse(null);
        if (classRoom == null) return List.of();
        Course course = courseRepo.findById(classRoom.getCourseId()).orElse(null);
        if (course == null) return List.of();
        return materialRepo.findByCourseCode(course.getCourseCode());
    }
    public List<Assignment> getAssignmentsByClassId(Long classId) {
        ClassRoom classRoom = classRepo.findById(classId).orElse(null);
        if (classRoom == null) return List.of();
        Course course = courseRepo.findById(classRoom.getCourseId()).orElse(null);
        if (course == null) return List.of();
        return assignmentRepo.findByCourseCode(course.getCourseCode());
    }
    public List<LearningMaterial> getMaterialsByCourseId(Long courseId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) return new ArrayList<>();
        return materialRepo.findByCourseCode(course.getCourseCode());
    }
    public List<LearningMaterial> getMaterialsByCourseCode(String courseCode) {
        return materialRepo.findByCourseCode(courseCode);
    }
    public List<Assignment> getAssignmentsByCourseId(Long courseId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) return new ArrayList<>();
        return assignmentRepo.findByCourseCode(course.getCourseCode());
    }
    public List<Assignment> getAssignmentsByCourseCode(String courseCode) {
        return assignmentRepo.findByCourseCode(courseCode);
    }

    public Course getCourseByCode(String courseCode) {
        return courseRepo.findByCourseCode(courseCode).orElse(null);
    }
}
