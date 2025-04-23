package com.dao;
import com.bean.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private final Statement statement;
    private final Connection connection;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

    //Product CRUD
    public int getProductCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from product");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }
    //Product CREATE - staff only
    public void createProduct(Product product) throws SQLException {

    }
    //Product READ - to everyone
    public List<Product> getAllProducts(Product product) throws SQLException {
        List<Product> products = new ArrayList<>();
        return products;
    }

    public void getProductById(int productID) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where product_id=?");
        preparedstatement.setInt(1,productID);
        preparedstatement.executeQuery();
       // return product;
    }
    //Product Update - staff only
    public void updateProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Product SET price = ? productName=? category=? quantity=? image=? description=? WHERE product_id = ?");
        preparedStatement.setInt(1, product.getProductId());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setString(3, product.getProductName());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setString(5,product.getImage());
        preparedStatement.setString(6,product.getDescription());
        //preparedStatement.setInt(7,product.getCategory());
        preparedStatement.executeUpdate();
    }
    //Product Delete - staff only
    public void deleteProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from product where productId = ?");
        preparedStatement.setInt(1, product.getProductId());
        preparedStatement.executeUpdate();
    }
}
