package com.edulink.courseservice.repository;
import com.edulink.courseservice.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, String> {
    List<LearningMaterial> findByCourseCode(String courseCode);
}
