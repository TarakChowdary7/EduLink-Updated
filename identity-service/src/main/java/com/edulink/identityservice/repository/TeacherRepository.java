package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserId(String userId);
    List<Teacher> findBySchoolId(String schoolId);
    List<Teacher> findBySubject(String subject);
}
