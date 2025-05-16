package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Payment implements Serializable {

    // Unique identifier for each payment
    private Integer paymentId;

    // Payment method: 'Credit Card' or 'Bank Transfer'
    private String method;

    // For Credit Card payments
    private String cardHolder;
    private String cardNumber;
    private Date expiryDate;
    private String cvc;

    // For Bank Transfer payments
    private String bsb;
    private String accountName;
    private String accountNumber;

    // Common fields
    private BigDecimal amount;
    private String status;         // 'Pending' or 'Paid'
    private Integer orderId;
    private Integer userId;
    private Date paymentDate;      // Filled when payment is finalized

    public Payment() {}

    public Payment(Integer paymentId, String method, String cardHolder, String cardNumber,
                   Date expiryDate, String cvc, String bsb, String accountName, String accountNumber,
                   BigDecimal amount, String status, Integer orderId, Integer userId, Date paymentDate) {
        this.paymentId = paymentId;
        this.method = method;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
        this.bsb = bsb;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
        this.orderId = orderId;
        this.userId = userId;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters

    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getCvc() { return cvc; }
    public void setCvc(String cvc) { this.cvc = cvc; }

    public String getBsb() { return bsb; }
    public void setBsb(String bsb) { this.bsb = bsb; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
}
