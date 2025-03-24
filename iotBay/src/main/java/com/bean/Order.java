package com.bean;

import com.enums.OrderStatus;

import java.util.Date;

public class Order {
    private Integer orderId;
    private Date createDate;
    private OrderStatus orderStatus;
    private Integer[] productIds;
    private Integer userId;

    public Order() {
    }

    public Order(Integer orderId, Date createDate, OrderStatus orderStatus, Integer[] productIds, Integer userId) {
        this.orderId = orderId;
        this.createDate = createDate;
        this.orderStatus = orderStatus;
        this.productIds = productIds;
        this.userId = userId;
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

    public Integer[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Integer[] productIds) {
        this.productIds = productIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
