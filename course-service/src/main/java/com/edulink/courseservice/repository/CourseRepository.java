package com.edulink.courseservice.repository;
import com.edulink.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findBySchoolId(String schoolId);
    List<Course> findByTeacherId(Long teacherId);
    Optional<Course> findByCourseCode(String courseCode);
}
