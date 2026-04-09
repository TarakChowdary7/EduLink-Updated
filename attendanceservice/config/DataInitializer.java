package com.edulink.attendanceservice.config;
import com.edulink.attendanceservice.entity.Attendance;
import com.edulink.attendanceservice.repository.AttendanceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.logging.Logger;

@Configuration
public class DataInitializer {
    private final AttendanceRepository repo;
    private static final Logger log = Logger.getLogger(DataInitializer.class.getName());

    public DataInitializer(AttendanceRepository repo) {
        this.repo = repo;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            // Only initialize sample data if database is empty
            if (repo.count() == 0) {
                // Create just 2 sample records with different students and courses
                repo.save(Attendance.builder()
                        .studentId(1L)
                        .courseId(1L)
                        .schoolId("SCH001")
                        .attendanceDate(LocalDate.now().minusDays(1))
                        .status("PRESENT")
                        .markedBy("teacher@greenwood.edu")
                        .build());
                
                repo.save(Attendance.builder()
                        .studentId(2L)
                        .courseId(2L)
                        .schoolId("SCH001")
                        .attendanceDate(LocalDate.now())
                        .status("ABSENT")
                        .markedBy("teacher@greenwood.edu")
                        .build());
                
                log.info("==> Attendance sample data initialized with 2 records");
            } else {
                log.info("==> Attendance data already exists, skipping initialization");
            }
        };
    }
}
