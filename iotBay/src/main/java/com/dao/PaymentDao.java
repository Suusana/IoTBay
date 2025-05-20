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

    // Save new payment
    public void save(Payment payment, Integer userId, Integer orderId) throws SQLException {
        String sql = "INSERT INTO Payment (payment_method, card_holder, card_number, expiry_date, cvc, " +
                "bsb, account_name, account_number, amount, payment_date, status, user_id, order_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setString(3, payment.getCardNumber());
            ps.setDate(4, payment.getExpiryDate());
            ps.setString(5, payment.getCvc());
            ps.setString(6, payment.getBsb());
            ps.setString(7, payment.getAccountName());
            ps.setString(8, payment.getAccountNumber());
            ps.setBigDecimal(9, payment.getAmount());
            ps.setLong(10, payment.getPaymentDate().getTime()); // timestamp 저장
            ps.setString(11, payment.getStatus());
            ps.setInt(12, userId);
            ps.setInt(13, orderId);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    payment.setPaymentId(rs.getInt(1));
                }
            }
        }
    }

    // Update payment
    public int update(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET payment_method = ?, card_holder = ?, card_number = ?, " +
                "expiry_date = ?, cvc = ?, bsb = ?, account_name = ?, account_number = ?, amount = ?, " +
                "payment_date = ?, status = ? WHERE payment_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getMethod());
            ps.setString(2, payment.getCardHolder());
            ps.setString(3, payment.getCardNumber());
            ps.setDate(4, payment.getExpiryDate());
            ps.setString(5, payment.getCvc());
            ps.setString(6, payment.getBsb());
            ps.setString(7, payment.getAccountName());
            ps.setString(8, payment.getAccountNumber());
            ps.setBigDecimal(9, payment.getAmount());
            ps.setLong(10, payment.getPaymentDate().getTime());
            ps.setString(11, payment.getStatus());
            ps.setInt(12, payment.getPaymentId());

            return ps.executeUpdate();
        }
    }


    // Delete payment
    public void delete(int paymentId) throws SQLException {
        String sql = "DELETE FROM Payment WHERE payment_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }

    // Get payment by ID
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

    // Get payment by ID and user ID
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

    // Get payments by order ID
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

    // Get payments by order ID and user ID
    public List<Payment> getPaymentsByOrderIdAndUser(int orderId, int userId) throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE order_id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(extractPayment(rs));
            }
        }
        return list;
    }

    // Get all payments for a user
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

    // Get all payments (admin)
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

    // Get payments using timestamp range
    public List<Payment> getPaymentsByTimestampDate(Date date, int userId) throws SQLException {
        List<Payment> list = new ArrayList<>();

        long startOfDay = date.toLocalDate()
                .atStartOfDay()
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        long endOfDay = startOfDay + 86400000L; // +1 day

        String sql = "SELECT * FROM Payment WHERE payment_date >= ? AND payment_date < ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, startOfDay);
            stmt.setLong(2, endOfDay);
            stmt.setInt(3, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(extractPayment(rs));
            }
        }
        return list;
    }

    // Find duplicate credit card by user
    public Payment findDuplicateCreditCard(int userId, String cardNumber, Date expiryDate) throws SQLException {
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

    // Convert ResultSet to Payment
    private Payment extractPayment(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setPaymentId(rs.getInt("payment_id"));
        p.setOrderId(rs.getInt("order_id"));
        p.setUserId(rs.getInt("user_id"));
        p.setMethod(rs.getString("payment_method"));
        p.setCardHolder(rs.getString("card_holder"));
        p.setCardNumber(rs.getString("card_number"));
        p.setCvc(rs.getString("cvc"));
        p.setBsb(rs.getString("bsb"));
        p.setAccountName(rs.getString("account_name"));
        p.setAccountNumber(rs.getString("account_number"));
        p.setAmount(rs.getBigDecimal("amount"));
        p.setStatus(rs.getString("status"));

        try {
            p.setExpiryDate(rs.getDate("expiry_date"));
        } catch (Exception e) {
            p.setExpiryDate(null);
        }

        try {
            long millis = rs.getLong("payment_date");
            p.setPaymentDate(new Date(millis));
        } catch (Exception e) {
            p.setPaymentDate(null);
        }

        return p;
    }
}
