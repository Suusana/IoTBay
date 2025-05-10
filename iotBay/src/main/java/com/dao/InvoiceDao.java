package com.dao;

import com.bean.Invoice;

import java.sql.*;

public class InvoiceDao {
    private final Connection conn;

    public InvoiceDao(Connection conn) {
        this.conn = conn;
    }

    public Invoice findInvoiceByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM Invoice WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setTotalPrice(rs.getInt("total_price"));
                invoice.setCreateDate(rs.getDate("create_date"));
                return invoice;
            }
        }
        return null;
    }
}
