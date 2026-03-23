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
            if (repo.count() == 0) {
                for (int i = 1; i <= 5; i++) {
                    repo.save(Attendance.builder().studentId(1L).courseId(1L).schoolId("SCH001")
                            .attendanceDate(LocalDate.now().minusDays(i))
                            .status(i % 5 == 0 ? "ABSENT" : "PRESENT").markedBy("teacher@greenwood.edu").build());
                }
                log.info("==> Attendance sample data initialized");
            }
        };
    }
}
