package com.hostel.hostelmanagement.dto;

public class PaymentProcessRequest {
    private String paymentMethod;
    private String transactionId;
    private String upiId;

    public PaymentProcessRequest() {}

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getUpiId() { return upiId; }
    public void setUpiId(String upiId) { this.upiId = upiId; }
}
