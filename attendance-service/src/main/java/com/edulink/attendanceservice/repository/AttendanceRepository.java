package com.edulink.attendanceservice.repository;
import com.edulink.attendanceservice.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findBySchoolId(String schoolId);
    List<Attendance> findByCourseId(Long courseId);
}
