package com.bean;

import java.io.Serializable;
import java.sql.Date;

public class PaymentLog implements Serializable {
    private int logId;
    private int paymentId;
    private int userId;
    private int orderId;
    private String action;
    private Date timestamp;

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
