package com.edulink.complianceservice.config;
import com.edulink.complianceservice.entity.ComplianceRecord;
import com.edulink.complianceservice.entity.School;
import com.edulink.complianceservice.repository.ComplianceRecordRepository;
import com.edulink.complianceservice.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private final ComplianceRecordRepository repo;

    @Autowired
    private final SchoolRepository schoolRepo;

    public DataInitializer(ComplianceRecordRepository repo, SchoolRepository schoolRepo) {
        this.repo = repo;
        this.schoolRepo = schoolRepo;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            if (schoolRepo.count() == 0) {
                School school1 = new School();
                school1.setSchoolId("SCH001");
                school1.setName("Greenwood High School");
                school1.setAddress("123 Main St");
                school1.setCity("Springfield");
                school1.setState("IL");
                school1.setCountry("USA");
                school1.setPhone("555-1234");
                school1.setEmail("info@greenwood.edu");
                schoolRepo.save(school1);

                School school2 = new School();
                school2.setSchoolId("SCH002");
                school2.setName("Riverside Elementary");
                school2.setAddress("456 River Rd");
                school2.setCity("Riverside");
                school2.setState("CA");
                school2.setCountry("USA");
                school2.setPhone("555-5678");
                school2.setEmail("contact@riverside.edu");
                schoolRepo.save(school2);

                log.info("==> School sample data initialized");
            }
            if (repo.count() == 0) {
                ComplianceRecord record1 = new ComplianceRecord();
                record1.setSchoolId("SCH001");
                record1.setAuditType("ANNUAL_AUDIT");
                record1.setAuditorEmail("compliance@edulink.com");
                record1.setStatus("COMPLIANT");
                record1.setFindings("All policies met");
                record1.setRecommendations("Continue current practices");
                repo.save(record1);

                ComplianceRecord record2 = new ComplianceRecord();
                record2.setSchoolId("SCH002");
                record2.setAuditType("SPOT_CHECK");
                record2.setAuditorEmail("compliance@edulink.com");
                record2.setStatus("UNDER_REVIEW");
                record2.setFindings("Minor documentation gaps");
                record2.setRecommendations("Update student records");
                repo.save(record2);

                log.info("==> Compliance sample data initialized");
            }
        };
    }
}
