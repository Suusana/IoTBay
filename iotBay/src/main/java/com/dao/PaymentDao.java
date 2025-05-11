package com.dao;

import com.bean.Payment;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {

    private final Connection conn;

    public PaymentDao(Connection conn) {
        this.conn = conn;
    }

    public void save(Payment payment,Integer userId, Integer orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Payment (payment_method, account_number, " +
                "account_name, BSB, date, user_id, order_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1,payment.getMethod());
        ps.setInt(2,payment.getAccountNum());
        ps.setString(3,payment.getAccountName());
        ps.setInt(4,payment.getBSB());
        String now = LocalDateTime.now()
                .withSecond(0)
                .withNano(0)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ps.setString(5,now);
        ps.setInt(6,userId);
        ps.setInt(7,orderId);
        ps.executeUpdate();

    }

//    public List<Payment> getPaymentsByOrderId(int orderId) throws SQLException {
//        List<Payment> payments = new ArrayList<>();
//        String sql = "SELECT * FROM payments WHERE order_id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, orderId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Payment p = new Payment();
//                p.setPaymentId(rs.getInt("payment_id"));
//                p.setOrderId(rs.getInt("order_id"));
//                p.setMethod(rs.getString("method"));
//                p.setCardHolder(rs.getString("card_holder"));
//                p.setCardNumber(rs.getString("card_number"));
//                p.setExpiryDate(rs.getDate("expiry_date"));
//                p.setAmount(rs.getBigDecimal("amount"));
//                p.setPaymentDate(rs.getDate("payment_date"));
//                p.setStatus(rs.getString("status"));
//                payments.add(p);
//            }
//        }
//        return payments;
//    }
//
//    public void update(Payment payment) throws SQLException {
//        String sql = "UPDATE payments SET method = ?, card_holder = ?, amount = ?, status = ? WHERE payment_id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, payment.getMethod());
//            ps.setString(2, payment.getCardHolder());
//            ps.setBigDecimal(3, payment.getAmount());
//            ps.setString(4, payment.getStatus());
//            ps.setInt(5, payment.getPaymentId());
//            ps.executeUpdate();
//        }
//    }
//
//    public void delete(int paymentId) throws SQLException {
//        String sql = "DELETE FROM payments WHERE payment_id = ?";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, paymentId);
//            ps.executeUpdate();
//        }
//    }
}
