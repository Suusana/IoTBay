package com.dao;

import java.sql.*;

public class DBManager {
    private final Connection connection;

    private ProductDao productDao;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDao(connection);
        }
        return productDao;
    }
}
