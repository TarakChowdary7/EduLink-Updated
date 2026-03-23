package com.edulink.examservice.config;
import com.edulink.examservice.entity.*;
import com.edulink.examservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Configuration
public class DataInitializer {
    private final ExamRepository examRepo;
    private final GradeRepository gradeRepo;
    private static final Logger log = Logger.getLogger(DataInitializer.class.getName());

    public DataInitializer(ExamRepository examRepo, GradeRepository gradeRepo) {
        this.examRepo = examRepo;
        this.gradeRepo = gradeRepo;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            Exam e1;
            if (examRepo.findByCourseCode("MATH101").isEmpty()) {
                e1 = examRepo.save(Exam.builder().courseCode("MATH101").teacherEmail("teacher@greenwood.edu")
                        .examTitle("Mid-Term Mathematics").examType("MID_TERM")
                        .totalMarks(100).passingMarks(35).schoolId("SCH001")
                        .examDate(LocalDateTime.now().plusDays(30)).build());
            } else {
                e1 = examRepo.findByCourseCode("MATH101").get(0);
            }

            if (gradeRepo.findByStudentId(1L).isEmpty()) {
                gradeRepo.save(Grade.builder().examId(e1.getCourseCode()).studentId(1L).teacherEmail("teacher@greenwood.edu")
                        .marksObtained(85).totalMarks(100).grade("A").remarks("Excellent performance").build());
                log.info("==> Exam sample data initialized");
            }
        };
    }
}
