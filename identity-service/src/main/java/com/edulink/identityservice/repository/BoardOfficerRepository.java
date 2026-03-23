package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.BoardOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BoardOfficerRepository extends JpaRepository<BoardOfficer, Long> {
    Optional<BoardOfficer> findByUserId(String userId);
}
