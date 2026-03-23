package com.edulink.examservice.repository;
import com.edulink.examservice.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ExamRepository extends JpaRepository<Exam, String> {
    List<Exam> findByCourseCode(String courseCode);
    List<Exam> findBySchoolId(String schoolId);
}
