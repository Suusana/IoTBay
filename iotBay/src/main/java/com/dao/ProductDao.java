package com.dao;

import com.bean.Category;
import com.bean.Product;

import java.nio.file.FileSystemNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private final Connection connection;
   // private String db_url = "jdbc:mysql://localhost:3306/iotbay";
    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    //Product CRUD
    //This is just to check whether the code is working
    public int getProductCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from product");
        ResultSet totalProductAmount = preparedStatement.executeQuery();
        if(totalProductAmount.next()) {
            return totalProductAmount.getInt(1);
        }
        return 0;
    }

    //Product CREATE - staff only
    public void createProduct(Product product) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO product (product_name, price, quantity, description, image, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, product.getProductName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.setString(4, product.getDescription());
        ps.setString(5, product.getImage()); // assuming image filename or path
        ps.setInt(6, product.getCategory().getCategoryId());

        ps.executeUpdate();
    }
/* class
    Product_T
*   private Integer productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String description;
    private String image;
    private Category category;
*
* sql
* create table Product
(
//    product_id   integer        not null
//        constraint Product_pk
//            primary key autoincrement,
//    product_name varchar(255)   not null,
//    quantity     integer        not null,
//    price        decimal(10, 2) not null,
//    description  text,
//    image        text           not null,
    category_id  integer        not null
);
* */
    //Product READ - everyone can see it
    //getAllProducts shows all the products from the database -> the shop.jsp will show all these products
    //Feature 2 - READ - Customer and staff user can list the IoT device records
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product");
        ResultSet ProductTable = preparedStatement.executeQuery();

        CategoryDao cd = new CategoryDao(connection);
        while(ProductTable.next()) {
         Product product = new Product();
         product.setProductId(ProductTable.getInt("product_id"));
         product.setProductName(ProductTable.getString("product_name"));
         product.setQuantity(ProductTable.getInt("quantity"));
         product.setPrice(ProductTable.getDouble("price"));
         product.setDescription(ProductTable.getString("description"));
         product.setImage(ProductTable.getString("image"));
          int categoryId = ProductTable.getInt("category_id");
          Category category = cd.getCategoryById(categoryId);
          product.setCategory(category);
         products.add(product); //add to List
        }
        return products;
    }

    public Product getProductById(int productID) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where product_id=?");
        preparedstatement.setInt(1,productID);
        ResultSet rs = preparedstatement.executeQuery();

        CategoryDao cd = new CategoryDao(connection);
        Product product = new Product();

        if(rs.next()) {
            product.setProductId(productID);
            product.setProductName(rs.getString("product_name"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));
            int categoryId = rs.getInt("category_id");
            Category category = cd.getCategoryById(categoryId);
            product.setCategory(category);
            return product;
        }
        return null;
    }

    public Product getProductByName(String productName) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where product_name=?");
        preparedstatement.setString(1,productName);
        //ResultSet : access data rows from the result table
        ResultSet rs = preparedstatement.executeQuery(); //executeQuery is for SELECT statement
        //use preparedstatement.executeUpdate(); for insert, update, delete
        if(rs.next()){
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setQuantity(rs.getInt("quantity"));
            product.setPrice(rs.getDouble("price"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));
            int categoryId = rs.getInt("category_id");

            CategoryDao cd = new CategoryDao(connection);
            Category category = cd.getCategoryById(categoryId);
            product.setCategory(category);

            return product;
        }
        return null;
    }
//    create table Category
//            (
//                    category_id integer      not null
//                            constraint Category_pk
//                            primary key autoincrement,
//                    category    varchar(255) not null
//            );
//-- create categories
//    INSERT INTO Category (category) VALUES
//                                    ('Smart Home'),
//                                            ('Health & Fitness'),
//                                            ('Security Devices'),
//                                            ('Industrial Devices'),
//                                            ('Wearables'),
//                                            ('Agriculture & Environment'),
//                                            ('Automotive & Transport'),
//                                            ('Smart Appliances'),
//                                            ('Energy & Utilities'),
//                                            ('Networking & Hubs');

    public List<Product> getProductByCategory(int categoryId) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where category_id=?");
        preparedstatement.setInt(1,categoryId);

        List<Product> products= new ArrayList<>();
        ResultSet rs = preparedstatement.executeQuery();

        while(rs.next()) {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setQuantity(rs.getInt("quantity"));
            product.setPrice(rs.getDouble("price"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));

            CategoryDao cd = new CategoryDao(connection);
            Category category = cd.getCategoryById(categoryId);
            product.setCategory(category);
            products.add(product);
        }
        //rs.close();
        //preparedstatement.close();

        return products;
    }
    //Product Update - staff only
    public void updateProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Product SET price = ?, product_name=?, category_id=? ,quantity=? ,image=? ,description=? WHERE product_id = ?");

        preparedStatement.setDouble(1,product.getPrice());
        preparedStatement.setString(2, product.getProductName());
        preparedStatement.setInt(3,product.getCategory().getCategoryId());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setString(5,product.getImage());
        preparedStatement.setString(6,product.getDescription());

        preparedStatement.setInt(7, product.getProductId());
        preparedStatement.executeUpdate();

        System.out.println("Update product: " + product.getProductName());
    }

    //Product Delete - staff only
    public void deleteProduct(int productId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from product where product_id = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
    }

    // get the last product
    public Product getLastProduct() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product order by product_id desc limit 1");
        ResultSet rs = preparedStatement.executeQuery();
        Product product = new Product();
        if(rs.next()) {
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));
        }
        return product;
    }

    public List<Product> getEightProducts() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product limit 8");
        List<Product> products = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setDescription(rs.getString("description"));
            product.setImage(rs.getString("image"));
            product.setPrice(rs.getDouble("price"));
            products.add(product);
        }
        return products;
    }
}
