package com.hostel.hostelmanagement.controller;

import com.hostel.hostelmanagement.dto.GenerateBillRequest;
import com.hostel.hostelmanagement.dto.PaymentProcessRequest;
import com.hostel.hostelmanagement.entity.Payment;
import com.hostel.hostelmanagement.entity.User;
import com.hostel.hostelmanagement.enums.PaymentStatus;
import com.hostel.hostelmanagement.repository.PaymentRepository;
import com.hostel.hostelmanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentController(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    // View all payments (Admin & Warden)
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // View student's payments
    @GetMapping("/student/{studentId}")
    public List<Payment> getPaymentsByStudent(@PathVariable Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    // Process payment for a bill
    @PostMapping("/{paymentId}/pay")
    public ResponseEntity<?> payBill(@PathVariable Long paymentId, @RequestBody PaymentProcessRequest req) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }

        if (payment.getStatus() == PaymentStatus.PAID) {
            return ResponseEntity.badRequest().body("Bill has already been paid.");
        }

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentMethod(req.getPaymentMethod() != null ? req.getPaymentMethod() : "UPI");
        String txn = req.getTransactionId();
        if (txn == null || txn.isBlank()) {
            txn = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        payment.setTransactionId(txn);
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
    }

    // Admin creates new bill for student
    @PostMapping("/generate")
    public ResponseEntity<?> generateBill(@RequestBody GenerateBillRequest req) {
        User student = userRepository.findById(req.getStudentId()).orElse(null);
        if (student == null) {
            return ResponseEntity.badRequest().body("Student not found.");
        }

        LocalDate due = req.getDueDate() != null ? req.getDueDate() : LocalDate.now().plusDays(15);
        Payment payment = new Payment(
                student,
                req.getBillTitle(),
                req.getBillType() != null ? req.getBillType() : "ROOM_RENT",
                req.getAmount(),
                due,
                PaymentStatus.PENDING
        );

        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
    }
}
