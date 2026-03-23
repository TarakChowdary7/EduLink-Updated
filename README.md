# EduLink – School Education & Digital Learning Management System
## Backend Microservices (Java Spring Boot)

---

## Architecture Overview

```
EduLink Microservices
├── identity-service     → Port 8081  (Auth, Users, Roles, JWT)
├── student-service      → Port 8082  (Student profiles, Enrollments, Submissions)
├── course-service       → Port 8083  (Courses, Classes, Materials, Assignments)
├── exam-service         → Port 8084  (Exams, Grades)
├── attendance-service   → Port 8085  (Attendance tracking)
├── compliance-service   → Port 8086  (Audits, Board, Regulator APIs)
└── notification-service → Port 8087  (Notifications, Alerts)
```

---

## Database Schema Overview
- **users**: User accounts (id, email, fullName, role, password, etc.)
- **students/teachers/compliance_officers/regulators/board_officers**: Role-specific tables (userId/email, additional fields)
- **courses**: Course definitions (id, courseCode, courseName, subject, grade, teacherId)
- **classrooms**: Class instances (id, className, grade, section, teacherEmail, courseId)
- **learning_materials**: Uploaded materials (id, courseId, teacherEmail, title, fileUrl, materialType)
- **assignments**: Assignments (id, courseId, teacherEmail, title, dueDate, maxMarks)
- **exams/grades/submissions**: In exam-service
- **attendance_records**: In attendance-service
- **audit_records**: In compliance-service
- **notifications**: In notification-service

Relations: classrooms → courses, learning_materials → courses, assignments → courses.

---

## Tech Stack
- Java 17
- Spring Boot 3.2.0
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA
- MySQL 8.x
- Maven
- REST APIs (Postman testable)

---

## Prerequisites
1. Java 17+
2. Maven 3.8+
3. MySQL 8.x running on localhost:3306
   - Username: root | Password: root (change in application.properties if different)

---

## Quick Start

### Step 1: Create Databases
```sql
-- Run this in MySQL Workbench or CLI:
source database-schema.sql
```
Or let Spring Boot auto-create them (ddl-auto=update is set).

### Step 2: Start All Services (run each in separate terminal)
```bash
cd identity-service && mvn spring-boot:run
cd student-service  && mvn spring-boot:run
cd course-service   && mvn spring-boot:run
cd exam-service     && mvn spring-boot:run
cd attendance-service && mvn spring-boot:run
cd compliance-service && mvn spring-boot:run
cd notification-service && mvn spring-boot:run
```

### Step 3: Test with Postman
Import `POSTMAN_COLLECTION.json` into Postman.

---

## Sample Credentials (auto-created on first startup)

| Role                   | Email                      | Password      |
|------------------------|----------------------------|---------------|
| Operator               | operator@edulink.com       | Operator@123  |
| Education Board Officer| boardofficer@edulink.com   | Board@1234    |
| Compliance Officer     | compliance@edulink.com     | Comply@1234   |
| Regulator              | regulator@edulink.com      | Regul@1234    |
| School Admin           | admin@greenwood.edu        | Admin@1234    |
| Teacher                | teacher@greenwood.edu      | Teacher@123   |
| Student                | student@greenwood.edu      | Student@123   |

---

## How to Test with Postman (Step-by-step)

1. **Login** → POST `http://localhost:6060/auth/login`  
   → Copy `accessToken` from response

2. **Set Token** in Postman:  
   Add header: `Authorization: Bearer <your_token>`

3. **Call role-specific APIs** using the appropriate token.

---

## User Creation Hierarchy

```
Operator
  ├── Creates: Education Board Officer
  ├── Creates: Compliance Officer
  └── Creates: Regulator

Compliance Officer
  └── Creates: School Administrator

School Administrator
  ├── Creates: Teacher (with temp password)
  └── Creates: Student (with temp password)
```

---

## API Endpoints Summary

### Identity Service (Port 8081)
| Method | Endpoint                             | Role Required       |
|--------|--------------------------------------|---------------------|
| POST   | /auth/login                          | Public              |
| POST   | /auth/change-password                | Any (Authenticated) |
| POST   | /auth/refresh-token                  | Public              |
| POST   | /operator/create-compliance-officer  | OPERATOR            |
| POST   | /operator/create-board-officer       | OPERATOR            |
| POST   | /operator/create-regulator           | OPERATOR            |
| GET    | /operator/users                      | OPERATOR            |
| POST   | /admin/create-teacher                | SCHOOL_ADMIN        |
| POST   | /admin/create-student                | SCHOOL_ADMIN        |
| GET    | /admin/teachers                      | SCHOOL_ADMIN        |
| GET    | /admin/students                      | SCHOOL_ADMIN        |
| POST   | /compliance/identity/create-school-admin | COMPLIANCE_OFFICER |

### Student Service (Port 8082)
| Method | Endpoint                      | Role    |
|--------|-------------------------------|---------|
| GET    | /student/courses              | STUDENT |
| GET    | /student/materials/{courseId} | STUDENT |
| POST   | /student/assignments/upload   | STUDENT |
| GET    | /student/grades               | STUDENT |
| GET    | /student/attendance           | STUDENT |

### Course Service (Port 8083)
| Method | Endpoint                    | Role         |
|--------|-----------------------------|--------------|
| GET    | /teacher/classes            | TEACHER      |
| POST   | /teacher/upload-material    | TEACHER      |
| POST   | /teacher/create-assignment  | TEACHER      |
| POST   | /teacher/create-exam        | TEACHER      |
| POST   | /teacher/grade-student      | TEACHER      |
| GET    | /teacher/students/{classId} | TEACHER      |
| POST   | /admin/create-course        | SCHOOL_ADMIN |
| POST   | /admin/create-class         | SCHOOL_ADMIN |
| GET    | /admin/attendance-report    | SCHOOL_ADMIN |

### Course Service (Port 8083)
| Method | Endpoint                      | Role         | Description |
|--------|-------------------------------|--------------|-------------|
| GET    | /teacher/classes              | TEACHER      | Get assigned classes |
| POST   | /teacher/upload-material      | TEACHER      | Upload learning material |
| POST   | /teacher/create-assignment    | TEACHER      | Create assignment |
| POST   | /teacher/create-exam          | TEACHER      | Create exam (exam-service) |
| POST   | /teacher/grade-student        | TEACHER      | Grade student (exam-service) |
| GET    | /teacher/students/{classId}   | TEACHER      | Get students in class (student-service) |
| GET    | /student/materials/{courseId}  | STUDENT      | Get learning materials for course |
| GET    | /student/assignments/{courseId}| STUDENT      | Get assignments for course |
| POST   | /admin/create-course          | SCHOOL_ADMIN | Create course |
| POST   | /admin/create-class           | SCHOOL_ADMIN | Create class |
| GET    | /admin/attendance-report      | SCHOOL_ADMIN | Attendance report (attendance-service) |

### Exam Service (Port 8084)
| Method | Endpoint                  | Role    |
|--------|---------------------------|---------|
| POST   | /teacher/create-exam      | TEACHER |
| POST   | /teacher/grade-student    | TEACHER |
| GET    | /student/grades           | STUDENT |

### Attendance Service (Port 8085)
| Method | Endpoint                  | Role         |
|--------|---------------------------|--------------|
| POST   | /teacher/mark-attendance  | TEACHER      |
| GET    | /student/attendance       | STUDENT      |
| GET    | /admin/attendance-report  | SCHOOL_ADMIN |

### Compliance Service (Port 8086)
| Method | Endpoint                        | Role                        |
|--------|---------------------------------|-----------------------------|
| POST   | /compliance/audit-school        | COMPLIANCE_OFFICER          |
| GET    | /compliance/compliance-status   | COMPLIANCE_OFFICER/REGULATOR|
| GET    | /compliance/audit-records       | COMPLIANCE_OFFICER/REGULATOR|
| GET    | /board/schools                  | EDUCATION_BOARD_OFFICER     |
| GET    | /board/academic-performance     | EDUCATION_BOARD_OFFICER     |
| GET    | /board/reports                  | EDUCATION_BOARD_OFFICER     |
| GET    | /board/compliance-summary       | EDUCATION_BOARD_OFFICER     |
| GET    | /regulator/compliance-reports   | REGULATOR                   |
| GET    | /regulator/accreditation-status | REGULATOR                   |
| GET    | /regulator/system-audit         | REGULATOR                   |

### Notification Service (Port 8087)
| Method | Endpoint                  | Role    |
|--------|---------------------------|---------|
| POST   | /notifications/send       | Any     |
| GET    | /notifications/my         | Any     |
| PUT    | /notifications/{id}/read  | Any     |

---

## JWT Token Flow
1. Login → Receive `accessToken` + `refreshToken`
2. Include `Authorization: Bearer <accessToken>` on every request
3. Token contains: email, role, userId
4. Each microservice validates JWT independently (stateless)
5. `@PreAuthorize("hasRole('ROLE_NAME')")` controls endpoint access

## Temporary Password Flow (Teacher/Student)
1. Admin creates account → system generates temp password
2. Response includes `temporaryPassword`
3. User logs in with temp password
4. `mustChangePassword: true` in login response
5. User calls `/auth/change-password` to set new password
6. User can now access all features

---

## Project Structure (Each Service)
```
src/main/java/com/edulink/{service}/
  ├── config/           → SecurityConfig, DataInitializer
  ├── controller/       → REST Controllers
  ├── dto/              → Request/Response DTOs
  ├── entity/           → JPA Entities
  ├── exception/        → Global Exception Handler
  ├── repository/       → JPA Repositories
  ├── security/         → JwtAuthFilter
  ├── service/          → Business Logic
  └── util/             → JwtUtil, PasswordGenerator
```
