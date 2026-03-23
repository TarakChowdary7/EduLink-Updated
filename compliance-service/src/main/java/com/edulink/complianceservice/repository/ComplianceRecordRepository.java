package com.edulink.complianceservice.repository;
import com.edulink.complianceservice.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {
    List<ComplianceRecord> findBySchoolId(String schoolId);
    List<ComplianceRecord> findByStatus(String status);
}
