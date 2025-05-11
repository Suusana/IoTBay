package com.dao;

import com.bean.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {

    private final Connection conn;

    public PaymentDao(Connection conn) {
        this.conn = conn;
    }

    public void save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (order_id, method, card_holder, card_number, expiry_date, amount, payment_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getMethod());
            ps.setString(3, payment.getCardHolder());
            ps.setString(4, payment.getCardNumber());
            ps.setDate(5, new java.sql.Date(payment.getExpiryDate().getTime()));
            ps.setBigDecimal(6, payment.getAmount());
            ps.setDate(7, new java.sql.Date(payment.getPaymentDate().getTime()));
            ps.setString(8, payment.getStatus());

            ps.executeUpdate();
        }
    }

    public List<Payment> getPaymentsByOrderId(int orderId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setMethod(rs.getString("method"));
                p.setCardHolder(rs.getString("card_holder"));
                p.setCardNumber(rs.getString("card_number"));
                p.setExpiryDate(rs.getDate("expiry_date"));
                p.setAmount(rs.getBigDecimal("amount"));
                p.setPaymentDate(rs.getDate("payment_date"));
                p.setStatus(rs.getString("status"));
                payments.add(p);
            }
        }
        return payments;
    }

    public void update(Payment payment) throws SQLException {
        String sql = "UPDATE payments SET method = ?, card_holder = ?, amount = ?, status = ? WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setBigDecimal(3, payment.getAmount());
            ps.setString(4, payment.getStatus());
            ps.setInt(5, payment.getPaymentId());
            ps.executeUpdate();
        }
    }

    public void delete(int paymentId) throws SQLException {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }
}
