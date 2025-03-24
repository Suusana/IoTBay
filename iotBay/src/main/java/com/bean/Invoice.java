package com.bean;

import java.util.Date;

public class Invoice {
    private Integer invoiceId;
    private Integer totalPrice;
    private Date createDate;
    private Integer orderId;

    public Invoice() {
    }

    public Invoice(Integer invoiceId, Integer totalPrice, Date createDate, Integer orderId) {
        this.invoiceId = invoiceId;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.orderId = orderId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
