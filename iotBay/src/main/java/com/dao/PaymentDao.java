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

    public void save(Payment payment, Integer userId, Integer orderId) throws SQLException {
        String sql = "INSERT INTO Payment (payment_method, card_holder, card_number, expiry_date, cvv, amount, payment_date, status, user_id, order_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setString(3, payment.getCardNumber());
            ps.setString(4, (payment.getExpiryDate() != null) ? payment.getExpiryDate().toString() : null);
            ps.setString(5, payment.getCvv());
            ps.setBigDecimal(6, payment.getAmount());
            ps.setString(7, (payment.getPaymentDate() != null) ? payment.getPaymentDate().toString() : null);
            ps.setString(8, payment.getStatus());
            ps.setInt(9, userId);
            ps.setInt(10, orderId);
            ps.executeUpdate();
        }
    }

    public void update(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET payment_method = ?, card_holder = ?, card_number = ?, expiry_date = ?, cvv = ?, amount = ?, payment_date = ?, status = ? WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setString(3, payment.getCardNumber());
            ps.setString(4, (payment.getExpiryDate() != null) ? payment.getExpiryDate().toString() : null);
            ps.setString(5, payment.getCvv());
            ps.setBigDecimal(6, payment.getAmount());
            ps.setString(7, (payment.getPaymentDate() != null) ? payment.getPaymentDate().toString() : null);
            ps.setString(8, payment.getStatus());
            ps.setInt(9, payment.getPaymentId());
            ps.executeUpdate();
        }
    }

    public void delete(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payment WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }

    public Payment getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setMethod(rs.getString("payment_method"));
                p.setCardHolder(rs.getString("card_holder"));
                p.setCardNumber(rs.getString("card_number"));
                try {
                    p.setExpiryDate(Date.valueOf(rs.getString("expiry_date")));
                } catch (Exception e) {
                    p.setExpiryDate(null);
                }
                p.setCvv(rs.getString("cvv"));
                p.setAmount(rs.getBigDecimal("amount"));
                try {
                    p.setPaymentDate(Date.valueOf(rs.getString("payment_date")));
                } catch (Exception e) {
                    p.setPaymentDate(null);
                }
                p.setStatus(rs.getString("status"));
                return p;
            }
        }
        return null;
    }

    public List<Payment> getPaymentsByOrderId(int orderId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setMethod(rs.getString("payment_method"));
                p.setCardHolder(rs.getString("card_holder"));
                p.setCardNumber(rs.getString("card_number"));
                try {
                    p.setExpiryDate(Date.valueOf(rs.getString("expiry_date")));
                } catch (Exception e) {
                    p.setExpiryDate(null);
                }
                p.setCvv(rs.getString("cvv"));
                p.setAmount(rs.getBigDecimal("amount"));
                try {
                    p.setPaymentDate(Date.valueOf(rs.getString("payment_date")));
                } catch (Exception e) {
                    p.setPaymentDate(null);
                }
                p.setStatus(rs.getString("status"));
                payments.add(p);
            }
        }
        return payments;
    }

    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setMethod(rs.getString("payment_method"));
                p.setCardHolder(rs.getString("card_holder"));
                p.setCardNumber(rs.getString("card_number"));
                try {
                    p.setExpiryDate(Date.valueOf(rs.getString("expiry_date")));
                } catch (Exception e) {
                    p.setExpiryDate(null);
                }
                p.setCvv(rs.getString("cvv"));
                p.setAmount(rs.getBigDecimal("amount"));
                try {
                    p.setPaymentDate(Date.valueOf(rs.getString("payment_date")));
                } catch (Exception e) {
                    p.setPaymentDate(null);
                }
                p.setStatus(rs.getString("status"));
                payments.add(p);
            }
        }
        return payments;
    }

    public List<Payment> getPaymentsByUserId(int userId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setMethod(rs.getString("payment_method"));
                p.setCardHolder(rs.getString("card_holder"));
                p.setCardNumber(rs.getString("card_number"));
                try {
                    p.setExpiryDate(Date.valueOf(rs.getString("expiry_date")));
                } catch (Exception e) {
                    p.setExpiryDate(null);
                }
                p.setCvv(rs.getString("cvv"));
                p.setAmount(rs.getBigDecimal("amount"));
                try {
                    p.setPaymentDate(Date.valueOf(rs.getString("payment_date")));
                } catch (Exception e) {
                    p.setPaymentDate(null);
                }
                p.setStatus(rs.getString("status"));
                payments.add(p);
            }
        }
        return payments;
    }
}
