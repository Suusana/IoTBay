package com.bean;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
public class Invoice implements Serializable {
    private Long invoiceId;
    private Integer totalPrice;
    private Date createDate;
    private Order order;

    public Invoice() {
    }

    public Invoice(Long invoiceId, Integer totalPrice, Date createDate, Order order) {
        this.invoiceId = invoiceId;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.order = order;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
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