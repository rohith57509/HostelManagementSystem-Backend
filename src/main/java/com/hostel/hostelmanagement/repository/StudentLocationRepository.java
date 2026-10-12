package com.hostel.hostelmanagement.repository;

import com.hostel.hostelmanagement.entity.StudentLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentLocationRepository extends JpaRepository<StudentLocation, Long> {
    Optional<StudentLocation> findByStudentId(Long studentId);
}
