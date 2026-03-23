package com.edulink.identityservice.config;

import com.edulink.identityservice.entity.Role;
import com.edulink.identityservice.entity.User;
import com.edulink.identityservice.entity.SchoolAdmin;
import com.edulink.identityservice.entity.Teacher;
import com.edulink.identityservice.entity.Student;
import com.edulink.identityservice.entity.School;
import com.edulink.identityservice.entity.ComplianceOfficer;
import com.edulink.identityservice.entity.Regulator;
import com.edulink.identityservice.entity.BoardOfficer;
import com.edulink.identityservice.repository.UserRepository;
import com.edulink.identityservice.repository.SchoolAdminRepository;
import com.edulink.identityservice.repository.TeacherRepository;
import com.edulink.identityservice.repository.StudentRepository;
import com.edulink.identityservice.repository.ComplianceOfficerRepository;
import com.edulink.identityservice.repository.RegulatorRepository;
import com.edulink.identityservice.repository.BoardOfficerRepository;
import com.edulink.identityservice.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
        private final SchoolAdminRepository schoolAdminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ComplianceOfficerRepository complianceOfficerRepository;
    private final RegulatorRepository regulatorRepository;
    private final BoardOfficerRepository boardOfficerRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;

        public DataInitializer(UserRepository userRepository, SchoolAdminRepository schoolAdminRepository, TeacherRepository teacherRepository, StudentRepository studentRepository,
                       ComplianceOfficerRepository complianceOfficerRepository, RegulatorRepository regulatorRepository, BoardOfficerRepository boardOfficerRepository,
                       SchoolRepository schoolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
                this.schoolAdminRepository = schoolAdminRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.complianceOfficerRepository = complianceOfficerRepository;
        this.regulatorRepository = regulatorRepository;
        this.boardOfficerRepository = boardOfficerRepository;
        this.schoolRepository = schoolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (!schoolRepository.existsById("SCH001")) {
                School school = new School();
                school.setId("SCH001");
                school.setName("Greenwood High School");
                school.setAddress("123 Main St");
                school.setPhone("9876543210");
                school.setEmail("info@greenwood.edu");
                school.setPrincipalName("Emily Carter");
                school.setEstablishedDate(java.time.LocalDate.of(1998, 1, 1));
                schoolRepository.save(school);
                log.info("==> SCHOOL created: SCH001 / Greenwood High School");
            }

            // Only create if not exists
            if (!userRepository.existsByEmail("operator@edulink.com")) {
                // Create Operator
                User operator = User.builder()
                        .email("operator@edulink.com")
                        .fullName("System Operator")
                        .password(passwordEncoder.encode("Operator@123"))
                        .role(Role.OPERATOR)
                        .active(true)
                        .mustChangePassword(false)
                        .build();
                userRepository.save(operator);
                log.info("==> OPERATOR created: operator@edulink.com / Operator@123");
            }

            if (!userRepository.existsByEmail("boardofficer@edulink.com")) {
                // Board Officer
                User board = User.builder()
                        .email("boardofficer@edulink.com")
                        .fullName("Dr. James Wilson")
                        .password(passwordEncoder.encode("Board@1234"))
                        .role(Role.EDUCATION_BOARD_OFFICER)
                        .active(true).mustChangePassword(false).build();
                userRepository.save(board);
                boardOfficerRepository.save(new BoardOfficer(board.getId(), board.getEmail(), board.getFullName(), "EducationBoard", board.getPassword()));
                log.info("==> BOARD OFFICER created: boardofficer@edulink.com / Board@1234");
            } else {
                userRepository.findByEmail("boardofficer@edulink.com").ifPresent(board -> {
                    if (boardOfficerRepository.findByUserId(board.getId()).isEmpty()) {
                        boardOfficerRepository.save(new BoardOfficer(board.getId(), board.getEmail(), board.getFullName(), "EducationBoard", board.getPassword()));
                        log.info("==> BOARD OFFICER record added for existing user: boardofficer@edulink.com");
                    }
                });
            }

            if (!userRepository.existsByEmail("compliance@edulink.com")) {
                // Compliance Officer
                User compliance = User.builder()
                        .email("compliance@edulink.com")
                        .fullName("Sarah Johnson")
                        .password(passwordEncoder.encode("Comply@1234"))
                        .role(Role.COMPLIANCE_OFFICER)
                        .active(true).mustChangePassword(false).build();
                userRepository.save(compliance);
                complianceOfficerRepository.save(new ComplianceOfficer(compliance.getId(), compliance.getEmail(), compliance.getFullName(), "ComplianceDept", compliance.getPassword()));
                log.info("==> COMPLIANCE OFFICER created: compliance@edulink.com / Comply@1234");
            } else {
                userRepository.findByEmail("compliance@edulink.com").ifPresent(compliance -> {
                    if (complianceOfficerRepository.findByUserId(compliance.getId()).isEmpty()) {
                        complianceOfficerRepository.save(new ComplianceOfficer(compliance.getId(), compliance.getEmail(), compliance.getFullName(), "ComplianceDept", compliance.getPassword()));
                        log.info("==> COMPLIANCE OFFICER record added for existing user: compliance@edulink.com");
                    }
                });
            }

            if (!userRepository.existsByEmail("regulator@edulink.com")) {
                // Regulator
                User reg = User.builder()
                        .email("regulator@edulink.com")
                        .fullName("Mark Thompson")
                        .password(passwordEncoder.encode("Regul@1234"))
                        .role(Role.REGULATOR)
                        .active(true).mustChangePassword(false).build();
                userRepository.save(reg);
                regulatorRepository.save(new Regulator(reg.getId(), reg.getEmail(), reg.getFullName(), "National", reg.getPassword()));
                log.info("==> REGULATOR created: regulator@edulink.com / Regul@1234");
            } else {
                userRepository.findByEmail("regulator@edulink.com").ifPresent(reg -> {
                    if (regulatorRepository.findByUserId(reg.getId()).isEmpty()) {
                        regulatorRepository.save(new Regulator(reg.getId(), reg.getEmail(), reg.getFullName(), "National", reg.getPassword()));
                        log.info("==> REGULATOR record added for existing user: regulator@edulink.com");
                    }
                });
            }

            if (!userRepository.existsByEmail("admin@greenwood.edu")) {
                // School Admin
                User admin = User.builder()
                        .email("admin@greenwood.edu")
                        .fullName("Principal Emily Carter")
                        .password(passwordEncoder.encode("Admin@1234"))
                        .role(Role.SCHOOL_ADMIN)
                        .active(true).mustChangePassword(false)
                        .schoolId("SCH001")
                        .build();
                userRepository.save(admin);
                schoolAdminRepository.save(new SchoolAdmin(admin.getId(), admin.getEmail(), admin.getFullName(), admin.getSchoolId(), "Admin@1234"));
                log.info("==> SCHOOL ADMIN created: admin@greenwood.edu / Admin@1234");
            } else {
                userRepository.findByEmail("admin@greenwood.edu").ifPresent(admin -> {
                    if (schoolAdminRepository.findByUserId(admin.getId()).isEmpty()) {
                        schoolAdminRepository.save(new SchoolAdmin(admin.getId(), admin.getEmail(), admin.getFullName(), admin.getSchoolId(), "Admin@1234"));
                        log.info("==> SCHOOL ADMIN record added for existing user: admin@greenwood.edu");
                    }
                });
            }

            if (!userRepository.existsByEmail("teacher@greenwood.edu")) {
                // Teacher
                User teacher = User.builder()
                        .email("teacher@greenwood.edu")
                        .fullName("Mr. Robert Brown")
                        .password(passwordEncoder.encode("Teacher@123"))
                        .role(Role.TEACHER)
                        .active(true).mustChangePassword(false)
                        .schoolId("SCH001")
                        .classId(1L)
                        .build();
                userRepository.save(teacher);
                teacherRepository.save(new Teacher(teacher.getId(), teacher.getEmail(), teacher.getFullName(), teacher.getSchoolId(), "Mathematics", teacher.getPassword()));
                log.info("==> TEACHER created: teacher@greenwood.edu / Teacher@123");
            } else {
                userRepository.findByEmail("teacher@greenwood.edu").ifPresent(teacher -> {
                    if (teacherRepository.findByUserId(teacher.getId()).isEmpty()) {
                        teacherRepository.save(new Teacher(teacher.getId(), teacher.getEmail(), teacher.getFullName(), teacher.getSchoolId(), "Mathematics", teacher.getPassword()));
                        log.info("==> TEACHER record added for existing user: teacher@greenwood.edu");
                    }
                });
            }

            if (!userRepository.existsByEmail("student@greenwood.edu")) {
                // Student
                User student = User.builder()
                        .email("student@greenwood.edu")
                        .fullName("Alice Smith")
                        .password(passwordEncoder.encode("Student@123"))
                        .role(Role.STUDENT)
                        .active(true).mustChangePassword(false)
                        .schoolId("SCH001")
                        .classId(1L)
                        .build();
                userRepository.save(student);
                studentRepository.save(new Student(student.getId(), student.getEmail(), student.getFullName(), student.getSchoolId(), student.getClassId(), student.getPassword()));
                log.info("==> STUDENT created: student@greenwood.edu / Student@123");
            } else {
                userRepository.findByEmail("student@greenwood.edu").ifPresent(student -> {
                    if (studentRepository.findByUserId(student.getId()).isEmpty()) {
                        studentRepository.save(new Student(student.getId(), student.getEmail(), student.getFullName(), student.getSchoolId(), student.getClassId(), student.getPassword()));
                        log.info("==> STUDENT record added for existing user: student@greenwood.edu");
                    }
                });
            }

            // Reconciliation pass to ensure roles are populated even if old data was partial.
            userRepository.findByRole(Role.EDUCATION_BOARD_OFFICER).forEach(user -> {
                if (boardOfficerRepository.findByUserId(user.getEmail()).isEmpty()) {
                    boardOfficerRepository.save(new BoardOfficer(user.getEmail(), user.getEmail(), user.getFullName(), "EducationBoard", user.getPassword()));
                    log.info("==> Reconciled BOARD OFFICER for {}", user.getEmail());
                }
            });
            userRepository.findByRole(Role.COMPLIANCE_OFFICER).forEach(user -> {
                if (complianceOfficerRepository.findByUserId(user.getEmail()).isEmpty()) {
                    complianceOfficerRepository.save(new ComplianceOfficer(user.getEmail(), user.getEmail(), user.getFullName(), "ComplianceDept", user.getPassword()));
                    log.info("==> Reconciled COMPLIANCE OFFICER for {}", user.getEmail());
                }
            });
            userRepository.findByRole(Role.REGULATOR).forEach(user -> {
                if (regulatorRepository.findByUserId(user.getEmail()).isEmpty()) {
                    regulatorRepository.save(new Regulator(user.getEmail(), user.getEmail(), user.getFullName(), "National", user.getPassword()));
                    log.info("==> Reconciled REGULATOR for {}", user.getEmail());
                }
            });
            userRepository.findByRole(Role.SCHOOL_ADMIN).forEach(user -> {
                if (schoolAdminRepository.findByUserId(user.getEmail()).isEmpty()) {
                    schoolAdminRepository.save(new SchoolAdmin(user.getEmail(), user.getEmail(), user.getFullName(), user.getSchoolId(), user.getPassword()));
                    log.info("==> Reconciled SCHOOL ADMIN for {}", user.getEmail());
                }
            });
            userRepository.findByRole(Role.TEACHER).forEach(user -> {
                if (teacherRepository.findByUserId(user.getEmail()).isEmpty()) {
                    teacherRepository.save(new Teacher(user.getEmail(), user.getEmail(), user.getFullName(), user.getSchoolId(), "Mathematics", user.getPassword()));
                    log.info("==> Reconciled TEACHER for {}", user.getEmail());
                }
            });
            userRepository.findByRole(Role.STUDENT).forEach(user -> {
                if (studentRepository.findByUserId(user.getEmail()).isEmpty()) {
                    studentRepository.save(new Student(user.getEmail(), user.getEmail(), user.getFullName(), user.getSchoolId(), user.getClassId(), user.getPassword()));
                    log.info("==> Reconciled STUDENT for {}", user.getEmail());
                }
            });

            log.info("Role table totals: board={}, compliance={}, regulator={}, schoolAdmin={}, teacher={}, student={}",
                    boardOfficerRepository.count(),
                    complianceOfficerRepository.count(),
                    regulatorRepository.count(),
                    schoolAdminRepository.count(),
                    teacherRepository.count(),
                    studentRepository.count());

            log.info("=== EduLink Data Initialization Complete ===");
        };
    }
}
