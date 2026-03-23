package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.Regulator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RegulatorRepository extends JpaRepository<Regulator, Long> {
    Optional<Regulator> findByUserId(String userId);
}
