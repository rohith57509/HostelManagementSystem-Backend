package com.hostel.hostelmanagement.controller;

import com.hostel.hostelmanagement.dto.LocationUpdateRequest;
import com.hostel.hostelmanagement.entity.StudentLocation;
import com.hostel.hostelmanagement.entity.User;
import com.hostel.hostelmanagement.repository.StudentLocationRepository;
import com.hostel.hostelmanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final StudentLocationRepository locationRepo;
    private final UserRepository userRepo;

    public LocationController(StudentLocationRepository locationRepo, UserRepository userRepo) {
        this.locationRepo = locationRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<?> updateLocation(@PathVariable Long studentId, @RequestBody LocationUpdateRequest req) {
        User student = userRepo.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        StudentLocation loc = locationRepo.findByStudentId(studentId)
                .orElse(new StudentLocation());

        loc.setStudent(student);
        loc.setLatitude(req.getLatitude());
        loc.setLongitude(req.getLongitude());
        loc.setPermissionGranted(req.getPermissionGranted() != null ? req.getPermissionGranted() : true);

        String zone = req.getZoneName();
        if (zone == null || zone.isBlank()) {
            zone = "Hostel Campus (Main Gate)";
        }
        loc.setZoneName(zone);

        if (!Boolean.TRUE.equals(loc.getPermissionGranted())) {
            loc.setStatus("PERMISSION_DENIED");
        } else if (zone.toLowerCase().contains("outstation") || zone.toLowerCase().contains("off-campus")) {
            loc.setStatus("OFF_CAMPUS");
        } else {
            loc.setStatus("ON_CAMPUS");
        }

        loc.setLastUpdated(LocalDateTime.now());
        StudentLocation saved = locationRepo.save(loc);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentLocation(@PathVariable Long studentId) {
        return locationRepo.findByStudentId(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }
}
