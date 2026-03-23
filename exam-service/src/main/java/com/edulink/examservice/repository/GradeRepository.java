package com.edulink.examservice.repository;
import com.edulink.examservice.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByExamId(String examId);
}
