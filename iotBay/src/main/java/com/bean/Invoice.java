package com.bean;

import java.util.Date;
import java.io.Serializable;
public class Invoice implements Serializable {
    private Integer invoiceId;
    private Integer totalPrice;
    private Date createDate;
    private Order order;

    public Invoice() {
    }

    public Invoice(Integer invoiceId, Integer totalPrice, Date createDate, Order order) {
        this.invoiceId = invoiceId;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}