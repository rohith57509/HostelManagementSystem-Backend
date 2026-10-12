package com.hostel.hostelmanagement.controller;

import com.hostel.hostelmanagement.entity.StudentLocation;
import com.hostel.hostelmanagement.entity.User;
import com.hostel.hostelmanagement.repository.StudentLocationRepository;
import com.hostel.hostelmanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final StudentLocationRepository studentLocationRepository;

    public AdminController(UserRepository userRepository, StudentLocationRepository studentLocationRepository) {
        this.userRepository = userRepository;
        this.studentLocationRepository = studentLocationRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student-locations")
    public List<StudentLocation> getAllStudentLocations() {
        return studentLocationRepository.findAll();
    }
}
