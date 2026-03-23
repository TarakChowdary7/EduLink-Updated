package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.ComplianceOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComplianceOfficerRepository extends JpaRepository<ComplianceOfficer, Long> {
    Optional<ComplianceOfficer> findByUserId(String userId);
}
