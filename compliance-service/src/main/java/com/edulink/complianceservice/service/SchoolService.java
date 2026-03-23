package com.edulink.complianceservice.service;

import com.edulink.complianceservice.dto.CreateSchoolRequest;
import com.edulink.complianceservice.entity.School;
import com.edulink.complianceservice.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School createSchool(CreateSchoolRequest request) {
        if (schoolRepository.existsBySchoolId(request.getSchoolId())) {
            throw new IllegalArgumentException("School with ID " + request.getSchoolId() + " already exists");
        }

        School school = new School();
        school.setSchoolId(request.getSchoolId());
        school.setName(request.getName());
        school.setAddress(request.getAddress());
        school.setCity(request.getCity());
        school.setState(request.getState());
        school.setCountry(request.getCountry());
        school.setPhone(request.getPhone());
        school.setEmail(request.getEmail());

        return schoolRepository.save(school);
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public School getSchoolById(String schoolId) {
        return schoolRepository.findBySchoolId(schoolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School not found: " + schoolId));
    }
}