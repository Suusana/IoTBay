package com.dao;

import com.bean.Category;

import java.sql.*;

public class DBManager {
    private final Connection connection;

    private CustomerDao customerDao;
    private PaymentDao paymentDao;
    private ProductDao productDao;
    private StaffDao staffDao;
    private CategoryDao categoryDao;
    private UserAccessLogDao userAccessLogDao;
    private OrderDao orderDao;
    private InvoiceDao invoiceDao;
    private PaymentLogDao paymentLogDao;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public CustomerDao getCustomerDao() {
        if (customerDao == null) {
            customerDao = new CustomerDao(connection);
        }
        return customerDao;
    }

    public InvoiceDao getInvoiceDao() {
        if (invoiceDao == null) {
            invoiceDao = new InvoiceDao(connection);
        }
        return invoiceDao;
    }

    public PaymentDao getPaymentDao(){
        if (paymentDao == null) {
            paymentDao = new PaymentDao(connection);
        }
        return paymentDao;
    }

    public ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDao(connection);
        }
        return productDao;
    }

    public StaffDao getStaffDao() {
        if (staffDao == null) {
            staffDao = new StaffDao(connection);
        }
        return staffDao;
    }

    public CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao(connection);
        }
        return categoryDao;
    }

    public UserAccessLogDao getUserAccessLogDao() {
        if (userAccessLogDao == null) {
            userAccessLogDao = new UserAccessLogDao(connection);
        }
        return userAccessLogDao;
    }

    public OrderDao getOrderDao() {
        if (orderDao == null) {
            orderDao = new OrderDao(connection);
        }
        return orderDao;
    }

    public PaymentLogDao getPaymentLogDao() {
        if (paymentLogDao == null) {
            paymentLogDao = new PaymentLogDao(connection);
        }
        return paymentLogDao;
    }
}
