-- ====================================================
-- EduLink Database Schema
-- Run this script to set up all databases
-- ====================================================

-- =====================
-- Identity Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_identity;
USE edulink_identity;

CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role ENUM('OPERATOR','EDUCATION_BOARD_OFFICER','COMPLIANCE_OFFICER','REGULATOR','SCHOOL_ADMIN','TEACHER','STUDENT') NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    must_change_password BOOLEAN DEFAULT FALSE,
    temporary_password VARCHAR(255),
    school_id VARCHAR(50),
    created_at DATETIME,
    updated_at DATETIME
);

-- Role-specific tables for faster retrieval and reporting
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    school_id VARCHAR(50),
    class_id BIGINT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teachers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    school_id VARCHAR(50),
    subject VARCHAR(100),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compliance_officers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    department VARCHAR(100),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS regulators (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    jurisdiction VARCHAR(255),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS board_officers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    board_name VARCHAR(255),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS school_admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    school_id VARCHAR(50),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS schools (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20),
    email VARCHAR(255),
    principal_name VARCHAR(255),
    established_date DATE,
    created_at DATETIME
);

-- Sample Users (passwords are BCrypt encoded; plain text shown in comments)
INSERT INTO users (email, password, full_name, role, active, must_change_password, school_id) VALUES
('operator@edulink.com',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'System Operator',        'OPERATOR',                 true, false, NULL),
('boardofficer@edulink.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'Dr. James Wilson',       'EDUCATION_BOARD_OFFICER',  true, false, NULL),
('compliance@edulink.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'Sarah Johnson',          'COMPLIANCE_OFFICER',       true, false, NULL),
('regulator@edulink.com',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'Mark Thompson',          'REGULATOR',                true, false, NULL),
('admin@greenwood.edu',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'Principal Emily Carter', 'SCHOOL_ADMIN',             true, false, 'SCH001'),
('teacher@greenwood.edu',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'Mr. Robert Brown',       'TEACHER',                  true, false, 'SCH001'),
('student@greenwood.edu',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh', 'Alice Smith',            'STUDENT',                  true, false, 'SCH001');
-- NOTE: The DataInitializer will create users with correct passwords automatically on first run.
-- Passwords: Operator@123, Board@1234, Comply@1234, Regul@1234, Admin@1234, Teacher@123, Student@123

INSERT INTO schools (id, name, address, phone, email, principal_name, established_date, created_at) VALUES
('SCH001', 'Greenwood High School', '123 Education Lane, Springfield', '555-1234', 'info@greenwood.edu', 'Principal Emily Carter', '2000-09-01', NOW());

-- =====================
-- Student Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_student;
USE edulink_student;

CREATE TABLE IF NOT EXISTS student_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    student_code VARCHAR(50),
    full_name VARCHAR(255),
    email VARCHAR(255),
    date_of_birth DATE,
    grade VARCHAR(50),
    section VARCHAR(10),
    school_id VARCHAR(50),
    parent_name VARCHAR(255),
    parent_contact VARCHAR(20),
    enrolled_at DATETIME
);

CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT,
    course_id BIGINT,
    course_name VARCHAR(255),
    course_code VARCHAR(50),
    status VARCHAR(50),
    enrolled_at DATETIME
);

CREATE TABLE IF NOT EXISTS assignment_submissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT,
    assignment_id BIGINT,
    assignment_title VARCHAR(255),
    submission_content TEXT,
    file_url VARCHAR(500),
    status VARCHAR(50),
    submitted_at DATETIME
);

-- =====================
-- Course Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_course;
USE edulink_course;

CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(50) UNIQUE,
    course_name VARCHAR(255),
    description TEXT,
    school_id VARCHAR(50),
    teacher_id BIGINT,
    subject VARCHAR(100),
    grade VARCHAR(50),
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME
);

CREATE TABLE IF NOT EXISTS classrooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(255),
    grade VARCHAR(50),
    section VARCHAR(10),
    school_id VARCHAR(50),
    teacher_id BIGINT,
    course_id BIGINT,
    capacity INT,
    created_at DATETIME
);

CREATE TABLE IF NOT EXISTS learning_materials (
    course_code VARCHAR(50) PRIMARY KEY,
    teacher_id BIGINT,
    title VARCHAR(255),
    description TEXT,
    file_url VARCHAR(500),
    material_type VARCHAR(50),
    uploaded_at DATETIME
);

CREATE TABLE IF NOT EXISTS assignments (
    course_code VARCHAR(50) PRIMARY KEY,
    teacher_id BIGINT,
    title VARCHAR(255),
    description TEXT,
    due_date DATETIME,
    max_marks INT,
    created_at DATETIME
);

-- =====================
-- Exam Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_exam;
USE edulink_exam;

CREATE TABLE IF NOT EXISTS exams (
    course_code VARCHAR(50) PRIMARY KEY,
    teacher_id BIGINT,
    exam_title VARCHAR(255),
    exam_type VARCHAR(50),
    total_marks INT,
    passing_marks INT,
    school_id VARCHAR(50),
    exam_date DATETIME,
    created_at DATETIME
);

CREATE TABLE IF NOT EXISTS grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT,
    student_id BIGINT,
    teacher_id BIGINT,
    marks_obtained INT,
    total_marks INT,
    grade VARCHAR(5),
    remarks TEXT,
    graded_at DATETIME
);

-- =====================
-- Attendance Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_attendance;
USE edulink_attendance;

CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT,
    course_id BIGINT,
    school_id VARCHAR(50),
    attendance_date DATE,
    status ENUM('PRESENT','ABSENT','LATE','EXCUSED'),
    marked_by VARCHAR(255),
    created_at DATETIME
);

-- =====================
-- Compliance Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_compliance;
USE edulink_compliance;

CREATE TABLE IF NOT EXISTS compliance_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id VARCHAR(50),
    audit_type VARCHAR(100),
    auditor_email VARCHAR(255),
    status ENUM('COMPLIANT','NON_COMPLIANT','UNDER_REVIEW'),
    findings TEXT,
    recommendations TEXT,
    audit_date DATETIME,
    created_at DATETIME
);

-- =====================
-- Notification Service DB
-- =====================
CREATE DATABASE IF NOT EXISTS edulink_notification;
USE edulink_notification;

CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_id VARCHAR(255),
    recipient_email VARCHAR(255),
    recipient_role VARCHAR(50),
    title VARCHAR(255),
    message TEXT,
    type VARCHAR(50),
    is_read BOOLEAN DEFAULT FALSE,
    created_at DATETIME
);

SELECT 'EduLink databases and tables created successfully!' AS status;
