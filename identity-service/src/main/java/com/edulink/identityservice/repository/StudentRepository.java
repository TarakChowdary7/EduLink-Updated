package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(String userId);
    List<Student> findBySchoolId(String schoolId);
    List<Student> findByClassId(Long classId);
    List<Student> findByClassIdAndSchoolId(Long classId, String schoolId);
}
