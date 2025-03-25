package com.bean;

import java.util.Date;
import java.util.List;

public class Invoice {
    private Integer invoiceId;
    private Integer totalPrice;
    private Date createDate;
    private List<Order> orders;

    public Invoice() {
    }

    public Invoice(Integer invoiceId, Integer totalPrice, Date createDate, List<Order> orders) {
        this.invoiceId = invoiceId;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.orders = orders;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}