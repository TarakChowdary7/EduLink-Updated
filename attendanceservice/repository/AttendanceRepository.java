package com.edulink.attendanceservice.repository;
import com.edulink.attendanceservice.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findBySchoolId(String schoolId);
    List<Attendance> findByCourseId(Long courseId);
    
    @Query("SELECT a FROM Attendance a WHERE a.studentId = :studentId AND a.courseId = :courseId AND a.attendanceDate = :attendanceDate")
    Optional<Attendance> findByStudentIdAndCourseIdAndAttendanceDate(
            @Param("studentId") Long studentId, 
            @Param("courseId") Long courseId, 
            @Param("attendanceDate") LocalDate attendanceDate);
}
