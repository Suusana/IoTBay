package com.dao;

import com.bean.Order;
import com.bean.Product;
import com.enums.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private final Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public List<Order> findOrderByCustomerId(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"Order\" WHERE user_id=? ORDER BY create_date DESC");

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

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM \"Order\" WHERE user_id = ? AND DATE(create_date) = ? ORDER BY create_date DESC");

        stmt.setInt(1, userId);
        stmt.setString(2, dateStr);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            orders.add(mapOrder(rs));
        }

        rs.close();
        stmt.close();
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


    public List<Product> findProductsByOrderId(int orderId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.product_name, p.price " +
                "FROM Product p " +
                "JOIN OrderProduct op ON p.product_id = op.product_id " +
                "WHERE op.order_id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setPrice(rs.getDouble("price"));
            products.add(product);
        }

        rs.close();
        stmt.close();

        return products;
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

    public int findOrderQuantity(int orderId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT quantity FROM \"Order\" WHERE order_id = ?");
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();
        int quantity = 0;
        if (rs.next()) {
            quantity = rs.getInt("quantity");
        }
        rs.close();
        stmt.close();
        return quantity;
    }





    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setCreateDate(rs.getTimestamp("create_date"));
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
        order.setBuyer(null); // 可根据需要加载 Customer 对象

        List<Product> products = new ArrayList<>();
        ProductDao productDao = new ProductDao(connection);

        Product product = productDao.findProductById(rs.getInt("product_id"));
        if (product != null) {
            // ✅ 从 Order 表中读取数量，设置到 product 对象
            product.setQuantity(rs.getInt("quantity"));
            products.add(product);
        }

        order.setProducts(products); // ✅ 一个订单一个商品

        return order;
    }

}
