package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.SchoolAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SchoolAdminRepository extends JpaRepository<SchoolAdmin, Long> {
	Optional<SchoolAdmin> findByUserId(String userId);
}
