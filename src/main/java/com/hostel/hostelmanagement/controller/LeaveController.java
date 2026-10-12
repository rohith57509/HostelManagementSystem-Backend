package com.hostel.hostelmanagement.controller;

import com.hostel.hostelmanagement.dto.LeaveRequestDto;
import com.hostel.hostelmanagement.entity.LeaveRequest;
import com.hostel.hostelmanagement.enums.LeaveStatus;
import com.hostel.hostelmanagement.repository.LeaveRequestRepository;
import com.hostel.hostelmanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    public LeaveController(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    @GetMapping("/student/{id}")
    public List<LeaveRequest> getLeaveRequestsByStudent(@PathVariable Long id) {
        return leaveRequestRepository.findByStudentId(id);
    }

    @PostMapping("/student/{id}")
    public LeaveRequest addLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequestDto r) {
        LeaveRequest req = new LeaveRequest();
        req.setStudent(userRepository.findById(id).orElseThrow());
        req.setFromDate(r.getFromDate());
        req.setToDate(r.getToDate());
        req.setReason(r.getReason());
        req.setStatus(LeaveStatus.PENDING);
        return leaveRequestRepository.save(req);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateStatus(@PathVariable Long id, @RequestParam LeaveStatus status) {
        return leaveRequestRepository.findById(id)
                .map(l -> {
                    l.setStatus(status);
                    return ResponseEntity.ok(leaveRequestRepository.save(l));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}