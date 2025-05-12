package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Payment implements Serializable {
    private Integer paymentId;
    private String method;
    private String cardHolder;
    private String cardNumber;
    private Date expiryDate;         // 변경됨: String → java.sql.Date
    private String cvv;
    private BigDecimal amount;
    private String status;
    private Integer orderId;
    private Integer userId;
    private Date paymentDate;

    public Payment() {}

    public Payment(Integer paymentId, String method, String cardHolder, String cardNumber,
                   Date expiryDate, String cvv, BigDecimal amount, String status,
                   Integer orderId, Integer userId, Date paymentDate) {
        this.paymentId = paymentId;
        this.method = method;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
        this.status = status;
        this.orderId = orderId;
        this.userId = userId;
        this.paymentDate = paymentDate;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {               // getter 수정
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) { // setter 수정
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
