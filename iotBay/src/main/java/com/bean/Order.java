package com.bean;

import com.enums.OrderStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private Integer orderId;
    private Date createDate;
    private OrderStatus orderStatus;
    private List<Product> products;
    private Integer quantity;
    private Customer buyer;

    public Order() {
    }

    public Order(Integer orderId, Date createDate, OrderStatus orderStatus,
                 List<Product> products, Integer quantity, Customer buyer) {
        this.orderId = orderId;
        this.createDate = createDate;
        this.orderStatus = orderStatus;
        this.products = products;
        this.quantity = quantity;
        this.buyer = buyer;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
