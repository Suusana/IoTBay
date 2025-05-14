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
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setString(3, payment.getCardNumber());
            ps.setDate(4, payment.getExpiryDate());
            ps.setString(5, payment.getCvv());
            ps.setBigDecimal(6, payment.getAmount());
            ps.setDate(7, payment.getPaymentDate());
            ps.setString(8, payment.getStatus());
            ps.setInt(9, userId);
            ps.setInt(10, orderId);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    payment.setPaymentId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET payment_method = ?, card_holder = ?, card_number = ?, expiry_date = ?, cvv = ?, amount = ?, payment_date = ?, status = ? WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setString(3, payment.getCardNumber());
            ps.setDate(4, payment.getExpiryDate());
            ps.setString(5, payment.getCvv());
            ps.setBigDecimal(6, payment.getAmount());
            ps.setDate(7, payment.getPaymentDate());
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
                return extractPayment(rs);
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
                payments.add(extractPayment(rs));
            }
        }
        return payments;
    }

    public List<Payment> getPaymentsByDateAndUser(String date, int userId) throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE DATE(payment_date) = ? AND user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, date);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Payment p = extractPayment(rs);
            list.add(p);
        }
        return list;
    }

    public List<Payment> getPaymentsByOrderIdAndUser(int orderId, int userId) throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE order_id = ? AND user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, orderId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Payment p = extractPayment(rs);
            list.add(p);
        }
        return list;
    }

    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(extractPayment(rs));
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
                payments.add(extractPayment(rs));
            }
        }
        return payments;
    }

    public Payment getPaymentByIdAndUser(int paymentId, int userId) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE payment_id = ? AND user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractPayment(rs);
            }
        }
        return null;
    }

    public Payment findDuplicateCard(int userId, String cardNumber, Date expiryDate) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE user_id = ? AND card_number = ? AND DATE(expiry_date) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, cardNumber);
            stmt.setDate(3, expiryDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractPayment(rs);
            }
        }
        return null;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
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
