package com.dao;

import com.bean.Order;
import com.bean.Product;
import com.enums.OrderStatus;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private final Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void saveOrder(Order order, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `Order`" +
                " (create_date, order_status, user_id, product_id, quantity) VALUES (?, ?, ?, ?, ?)");
        String now = LocalDateTime.now()
                .withSecond(0)
                .withNano(0)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        preparedStatement.setString(1, now);
        preparedStatement.setString(2, order.getOrderStatus().toString());
        preparedStatement.setInt(3, userId);

        Product product = order.getProducts().get(0);
        preparedStatement.setInt(4, product.getProductId());
        preparedStatement.setInt(5, product.getQuantity());
        preparedStatement.executeUpdate();
    }

    public List<Order> getOrdersByUserId(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Order` WHERE user_id=?");

        preparedStatement.setInt(1, customerId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getInt("order_id"));
            order.setCreateDate(rs.getTimestamp("create_date"));
            order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
            order.setQuantity(rs.getInt("quantity"));
            orders.add(order);
        }
        return orders;
    }

    public Order searchOrderByOrderId(int orderId, int customerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Order` WHERE order_id=? AND user_id=?");

        preparedStatement.setInt(1, orderId);
        preparedStatement.setInt(2, customerId);
        ResultSet rs = preparedStatement.executeQuery();
        Order order = new Order();
        if (rs.next()) {
            order.setOrderId(rs.getInt("order_id"));
            order.setCreateDate(rs.getTimestamp("create_date"));
            order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
            order.setQuantity(rs.getInt("quantity"));
        }

        return order;
    }

    public List<Order> searchOrderByDate(String dateStr, int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        LocalDate date = LocalDate.parse(dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String start = date.atStartOfDay().format(formatter);
        String end = date.plusDays(1).atStartOfDay().format(formatter);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Order` WHERE user_id = ? AND create_date >= ? AND create_date < ? ORDER BY create_date DESC");

        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, start);
        preparedStatement.setString(3, end);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getInt("order_id"));
            order.setCreateDate(rs.getTimestamp("create_date"));
            order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
            order.setQuantity(rs.getInt("quantity"));
            orders.add(order);
        }
        return orders;
    }


    public Order findOrderByOrderId(int orderId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Order` where order_id=?");
        preparedStatement.setInt(1, orderId);
        ResultSet rs = preparedStatement.executeQuery();
        Order order = new Order();
        if(rs.next()) {
            order.setOrderId(rs.getInt("order_id"));
            order.setCreateDate(rs.getTimestamp("create_date"));
            order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
            order.setQuantity(rs.getInt("quantity"));
        }
        return order;
    }

    public void updateOrderStatus(int orderId, OrderStatus newStatus) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("UPDATE \"Order\" SET order_status = ? WHERE order_id = ?");
            stmt.setString(1, newStatus.name());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
            stmt.close();

    }

    public void updateOrderQuantity(int orderId, int quantity) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("UPDATE `Order` SET quantity = ? WHERE order_id = ?");
        stmt.setInt(1, quantity);
        stmt.setInt(2, orderId);
        stmt.executeUpdate();
        stmt.close();
    }

    public int getProductIdByOrderId(int orderId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select product_id from `Order` WHERE order_id = ?");
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("product_id");
    }
}
