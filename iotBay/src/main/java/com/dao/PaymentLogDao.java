package com.dao;

import com.bean.PaymentLog;
import java.sql.*;

public class PaymentLogDao {
    private final Connection conn;

    public PaymentLogDao(Connection conn) {
        this.conn = conn;
    }

    public void log(PaymentLog log) {
        String sql = "INSERT INTO PaymentLog (payment_id, user_id, order_id, action, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, log.getPaymentId());
            ps.setInt(2, log.getUserId());
            ps.setInt(3, log.getOrderId());
            ps.setString(4, log.getAction());
            ps.setTimestamp(5, log.getTimestamp());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
