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
        String sql = "INSERT INTO \"Order\" (create_date, order_status, user_id, product_id, quantity) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            order.setOrderId(resultSet.getInt(1));
        }
        resultSet.close();
        preparedStatement.close();
    }



    public List<Order> findOrderByCustomerId(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"Order\" WHERE user_id=? ORDER BY order_id");

        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orders.add(mapOrder(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return orders;
    }

    public Order searchOrderByOrderId(int orderId, int customerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"Order\" WHERE order_id=? AND user_id=?");

        preparedStatement.setInt(1, orderId);
        preparedStatement.setInt(2, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Order order = null;
        if (resultSet.next()) {
            order = mapOrder(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return order;
    }

    public List<Order> searchOrderByDate(String dateStr, int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        LocalDate date = LocalDate.parse(dateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String start = date.atStartOfDay().format(formatter);
        String end = date.plusDays(1).atStartOfDay().format(formatter);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"Order\" WHERE user_id = ? AND create_date >= ? AND create_date < ? ORDER BY create_date DESC");

        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, start);
        preparedStatement.setString(3, end);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orders.add(mapOrder(resultSet));
        }

        resultSet.close();
        preparedStatement.close();
        return orders;
    }


    public Order findOrderByOrderId(int orderId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"Order\" WHERE order_id=?");
        preparedStatement.setInt(1, orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Order order = null;
        if(resultSet.next()) {
            order = mapOrder(resultSet);
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

        PreparedStatement stmt = connection.prepareStatement("UPDATE \"Order\" SET quantity = ? WHERE order_id = ?");
        stmt.setInt(1, quantity);
        stmt.setInt(2, orderId);
        stmt.executeUpdate();
        stmt.close();
    }


    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setCreateDate(rs.getTimestamp("create_date"));
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));

        List<Product> products = new ArrayList<>();
        ProductDao productDao = new ProductDao(connection);

        Product product = productDao.findProductById(rs.getInt("product_id"));
        if (product != null) {
            product.setQuantity(rs.getInt("quantity"));
            products.add(product);
        }

        order.setProducts(products);

        return order;
    }

}
