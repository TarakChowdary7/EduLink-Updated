package com.edulink.courseservice.repository;
import com.edulink.courseservice.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    List<ClassRoom> findBySchoolId(String schoolId);
    List<ClassRoom> findByCourseId(Long courseId);
    List<ClassRoom> findByTeacherEmail(String teacherEmail);
    Optional<ClassRoom> findByClassName(String className);
    boolean existsByClassName(String className);
}
