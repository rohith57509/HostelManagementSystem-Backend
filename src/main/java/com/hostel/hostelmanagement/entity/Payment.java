package com.hostel.hostelmanagement.entity;

import com.hostel.hostelmanagement.enums.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    private String billTitle;
    private String billType;
    private Double amount;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String paymentMethod;
    private String transactionId;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Payment() {}

    public Payment(User student, String billTitle, String billType, Double amount, LocalDate dueDate, PaymentStatus status) {
        this.student = student;
        this.billTitle = billTitle;
        this.billType = billType;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public String getBillTitle() { return billTitle; }
    public void setBillTitle(String billTitle) { this.billTitle = billTitle; }

    public String getBillType() { return billType; }
    public void setBillType(String billType) { this.billType = billType; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
