package com.controller.OrderController;

import com.bean.Order;
import com.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrder")
public class ViewOrder extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        Connection connection = (Connection) getServletContext().getAttribute("dbConn");
        orderDao = new OrderDao(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String orderIdPara = request.getParameter("orderId");
        String orderDatePara = request.getParameter("orderDate");

        List<Order> orderList = new ArrayList<>();
        int customerId = 1; //模拟ID 后续从session获取
    }
}
