package com.dao;

import com.bean.Order;
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
        PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where user_id=?");
        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orders.add(mapOrder(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return orders;
    }

//OrderID primaryKey 1:1
    public Order findOrderByOrderId(int orderId, int customerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where order_id=? AND user_id=?");
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

    public List<Order> findOrderByDate(java.sql.Date date, int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where create_date=? AND user_id=?");
        preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
        preparedStatement.setInt(2, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orders.add(mapOrder(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return orders;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setCreateDate(rs.getTimestamp("create_date"));
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
        order.setBuyer(null); // 可根据需要加载 Customer 对象
        order.setProductIds(new Integer[0]); // 可根据需要加载产品列表
        return order;
    }


}
