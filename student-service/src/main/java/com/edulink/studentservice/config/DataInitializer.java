package com.edulink.studentservice.config;

import com.edulink.studentservice.entity.*;
import com.edulink.studentservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DataInitializer {
    private final StudentProfileRepository profileRepo;
    private final EnrollmentRepository enrollmentRepo;
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(StudentProfileRepository profileRepo,
                           EnrollmentRepository enrollmentRepo) {
        this.profileRepo = profileRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (profileRepo.count() == 0) {
                StudentProfile s1 = StudentProfile.builder()
                        .userId("student@greenwood.edu").studentCode("STU001").fullName("Alice Smith")
                        .email("student@greenwood.edu").grade("Grade 10").section("A")
                        .schoolId("SCH001").parentName("John Smith").parentContact("9876543210")
                        .dateOfBirth(LocalDate.of(2007, 5, 15)).build();
                profileRepo.save(s1);

                enrollmentRepo.save(Enrollment.builder().studentId(1L).courseId(1L)
                        .courseName("Mathematics").courseCode("MATH101").status("ACTIVE").build());
                enrollmentRepo.save(Enrollment.builder().studentId(1L).courseId(2L)
                        .courseName("Physics").courseCode("PHY101").status("ACTIVE").build());
                enrollmentRepo.save(Enrollment.builder().studentId(1L).courseId(3L)
                        .courseName("English Literature").courseCode("ENG101").status("ACTIVE").build());
                log.info("==> Student sample data initialized");
            }
        };
    }
}
