package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment implements Serializable {

    private Integer paymentId;
    private String method;
    private String cardHolder;
    private String cardNumber;
    private java.sql.Date expiryDate;
    private String cvc;

    private String bsb;
    private String accountName;
    private String accountNumber;

    private BigDecimal amount;
    private String status;
    private Integer orderId;
    private Integer userId;

    private Timestamp paymentDate;  // changed from java.sql.Date

    public Payment() {}

    public Payment(Integer paymentId, String method, String cardHolder, String cardNumber,
                   java.sql.Date expiryDate, String cvc, String bsb, String accountName, String accountNumber,
                   BigDecimal amount, String status, Integer orderId, Integer userId, Timestamp paymentDate) {
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

    public java.sql.Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(java.sql.Date expiryDate) { this.expiryDate = expiryDate; }

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

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }
}
