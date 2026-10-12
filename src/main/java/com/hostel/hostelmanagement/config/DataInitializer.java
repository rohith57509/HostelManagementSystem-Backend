package com.hostel.hostelmanagement.config;

import com.hostel.hostelmanagement.entity.Payment;
import com.hostel.hostelmanagement.entity.StudentLocation;
import com.hostel.hostelmanagement.entity.User;
import com.hostel.hostelmanagement.enums.PaymentStatus;
import com.hostel.hostelmanagement.enums.Role;
import com.hostel.hostelmanagement.repository.PaymentRepository;
import com.hostel.hostelmanagement.repository.StudentLocationRepository;
import com.hostel.hostelmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(UserRepository userRepo, PasswordEncoder enc, StudentLocationRepository locRepo, PaymentRepository payRepo) {
        return args -> {
            if (!userRepo.existsByEmail("admin@hostel.com")) {
                userRepo.save(new User("System Admin", "admin@hostel.com", enc.encode("admin123"), Role.ADMIN));
            }
            if (!userRepo.existsByEmail("warden@hostel.com")) {
                userRepo.save(new User("Demo Warden", "warden@hostel.com", enc.encode("warden123"), Role.WARDEN));
            }
            if (!userRepo.existsByEmail("student@hostel.com")) {
                User s = new User("Demo Student", "student@hostel.com", enc.encode("student123"), Role.STUDENT);
                s.setStudentId("STU-2026-001");
                s.setDepartment("Computer Science");
                userRepo.save(s);
            }

            // Seed sample locations for demo students if empty
            if (locRepo.count() == 0) {
                List<User> students = userRepo.findAll().stream()
                        .filter(u -> u.getRole() == Role.STUDENT)
                        .toList();

                if (!students.isEmpty()) {
                    // Demo Student 1: Inside Campus
                    User s1 = students.get(0);
                    locRepo.save(new StudentLocation(s1, 28.6139, 77.2090, "Hostel Block A - Study Room", "ON_CAMPUS", true));

                    if (students.size() > 1) {
                        locRepo.save(new StudentLocation(students.get(1), 28.6145, 77.2105, "Central Dining Hall", "ON_CAMPUS", true));
                    }
                    if (students.size() > 2) {
                        locRepo.save(new StudentLocation(students.get(2), 28.6250, 77.2180, "Off-Campus City Market", "OFF_CAMPUS", true));
                    }
                    if (students.size() > 3) {
                        locRepo.save(new StudentLocation(students.get(3), 28.5355, 77.3910, "Metro Station (Outstation Leave)", "OFF_CAMPUS", true));
                    }
                }
            }

            // Seed sample hostel bills and payments if empty
            if (payRepo.count() == 0) {
                userRepo.findByEmail("student@hostel.com").ifPresent(stu -> {
                    // 1. Pending Room Rent
                    Payment p1 = new Payment(stu, "Hostel Room Rent - Oct 2026", "ROOM_RENT", 4500.0, LocalDate.now().plusDays(10), PaymentStatus.PENDING);
                    payRepo.save(p1);

                    // 2. Pending Mess Fee
                    Payment p2 = new Payment(stu, "Mess Food Charges - Oct 2026", "MESS", 2800.0, LocalDate.now().plusDays(5), PaymentStatus.PENDING);
                    payRepo.save(p2);

                    // 3. Paid Admission Security Deposit
                    Payment p3 = new Payment(stu, "Hostel Caution & Security Deposit", "SECURITY_DEPOSIT", 5000.0, LocalDate.now().minusDays(30), PaymentStatus.PAID);
                    p3.setPaymentMethod("UPI");
                    p3.setTransactionId("TXN-SEC984102");
                    p3.setPaidAt(LocalDateTime.now().minusDays(30));
                    payRepo.save(p3);
                });

                // Additional sample bills for other students to populate admin dashboard
                List<User> allStudents = userRepo.findAll().stream()
                        .filter(u -> u.getRole() == Role.STUDENT && !"student@hostel.com".equals(u.getEmail()))
                        .toList();

                if (!allStudents.isEmpty()) {
                    User s2 = allStudents.get(0);
                    Payment p4 = new Payment(s2, "Hostel Room Rent - Oct 2026", "ROOM_RENT", 4500.0, LocalDate.now().minusDays(2), PaymentStatus.OVERDUE);
                    payRepo.save(p4);

                    Payment p5 = new Payment(s2, "Mess Food Charges - Sep 2026", "MESS", 2800.0, LocalDate.now().minusDays(32), PaymentStatus.PAID);
                    p5.setPaymentMethod("CARD");
                    p5.setTransactionId("TXN-CARD77319");
                    p5.setPaidAt(LocalDateTime.now().minusDays(33));
                    payRepo.save(p5);
                }

                if (allStudents.size() > 1) {
                    User s3 = allStudents.get(1);
                    Payment p6 = new Payment(s3, "Hostel Room Rent - Oct 2026", "ROOM_RENT", 4500.0, LocalDate.now().plusDays(12), PaymentStatus.PAID);
                    p6.setPaymentMethod("NET_BANKING");
                    p6.setTransactionId("TXN-NB550211");
                    p6.setPaidAt(LocalDateTime.now().minusDays(1));
                    payRepo.save(p6);
                }
            }
        };
    }
}