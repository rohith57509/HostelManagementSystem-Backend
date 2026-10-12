package com.hostel.hostelmanagement.controller;

import com.hostel.hostelmanagement.dto.ComplaintRequest;
import com.hostel.hostelmanagement.entity.Complaint;
import com.hostel.hostelmanagement.enums.ComplaintStatus;
import com.hostel.hostelmanagement.repository.ComplaintRepository;
import com.hostel.hostelmanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintController(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @GetMapping("/student/{id}")
    public List<Complaint> getComplaintsByStudent(@PathVariable Long id) {
        return complaintRepository.findByStudentId(id);
    }

    @PostMapping("/student/{id}")
    public Complaint addComplaint(@PathVariable Long id, @RequestBody ComplaintRequest r) {
        Complaint complaint = new Complaint();
        complaint.setStudent(userRepository.findById(id).orElseThrow());
        complaint.setTitle(r.getTitle());
        complaint.setDescription(r.getDescription());
        complaint.setStatus(ComplaintStatus.OPEN);
        return complaintRepository.save(complaint);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id, @RequestParam ComplaintStatus status) {
        return complaintRepository.findById(id)
                .map(c -> {
                    c.setStatus(status);
                    return ResponseEntity.ok(complaintRepository.save(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}