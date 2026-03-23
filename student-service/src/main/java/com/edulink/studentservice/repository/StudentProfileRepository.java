package com.edulink.studentservice.repository;
import com.edulink.studentservice.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUserId(String userId);
    Optional<StudentProfile> findByEmail(String email);
    List<StudentProfile> findBySchoolId(String schoolId);
}
