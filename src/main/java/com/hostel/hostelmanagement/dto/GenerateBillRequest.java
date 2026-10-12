package com.hostel.hostelmanagement.dto;

import java.time.LocalDate;

public class GenerateBillRequest {
    private Long studentId;
    private String billTitle;
    private String billType;
    private Double amount;
    private LocalDate dueDate;

    public GenerateBillRequest() {}

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getBillTitle() { return billTitle; }
    public void setBillTitle(String billTitle) { this.billTitle = billTitle; }

    public String getBillType() { return billType; }
    public void setBillType(String billType) { this.billType = billType; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
