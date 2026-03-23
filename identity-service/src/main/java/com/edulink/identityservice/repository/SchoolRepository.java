package com.edulink.identityservice.repository;

import com.edulink.identityservice.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, String> {
}