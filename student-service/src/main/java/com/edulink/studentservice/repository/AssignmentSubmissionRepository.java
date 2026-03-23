package com.edulink.studentservice.repository;
import com.edulink.studentservice.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    List<AssignmentSubmission> findByStudentId(Long studentId);
}
