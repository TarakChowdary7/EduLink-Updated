package com.edulink.courseservice.repository;
import com.edulink.courseservice.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    List<Assignment> findByCourseCode(String courseCode);
}
