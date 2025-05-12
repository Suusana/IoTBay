package com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Payment implements Serializable {
    private Integer paymentId;
    private Double Amount;
    private String method;
    private Integer AccountNum;
    private String AccountName;
    private Integer BSB;
    private Date date;

    public Payment() {
    }

    public Payment(Integer paymentId, Double amount, String method, Integer accountNum,
                   String accountName, Integer BSB, Date date) {
        this.paymentId = paymentId;
        Amount = amount;
        this.method = method;
        AccountNum = accountNum;
        AccountName = accountName;
        this.BSB = BSB;
        this.date = date;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getAccountNum() {
        return AccountNum;
    }

    public void setAccountNum(Integer accountNum) {
        AccountNum = accountNum;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public Integer getBSB() {
        return BSB;
    }

    public void setBSB(Integer BSB) {
        this.BSB = BSB;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
