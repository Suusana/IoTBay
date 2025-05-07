package com.dao;

import com.bean.Category;
import com.bean.Product;

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
        while(ProductTable.next()) {
         Product product = new Product();
         product.setProductId(ProductTable.getInt("product_id"));
         product.setProductName(ProductTable.getString("product_name"));
         product.setQuantity(ProductTable.getInt("quantity"));
         product.setPrice(ProductTable.getDouble("price"));
         product.setDescription(ProductTable.getString("description"));
         product.setImage(ProductTable.getString("image"));
          int categoryId = ProductTable.getInt("category_id");
          Category category = new Category();
          category.setCategoryId(categoryId);
          product.setCategory(category);
         products.add(product);
        }
        return products;
    }

    public void getProductById(int productID) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where product_id=?");
        preparedstatement.setInt(1,productID);
        preparedstatement.executeQuery();
        // return product;
    }

    public void getProductByName(String productName) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where product_name=?");
        preparedstatement.setString(1,productName);
        preparedstatement.executeQuery();
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

    public void getProductByCategoryId(int categoryId) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where category_id=?");
        preparedstatement.setInt(1,categoryId);
        preparedstatement.executeQuery();
    }
    //Product Update - staff only
    public void updateProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Product SET price = ? productName=? category_id=? quantity=? image=? description=? category_id=? WHERE product_id = ?");
        preparedStatement.setInt(1, product.getProductId());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setString(3, product.getProductName());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setString(5,product.getImage());
        preparedStatement.setString(6,product.getDescription());
        preparedStatement.setInt(7,product.getCategory().getCategoryId());
        preparedStatement.executeUpdate();
    }

    //Product Delete - staff only
    public void deleteProduct(int productId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from product where product_id = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
    }

    //FIndProductByID
    public Product findProductById(int productId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Product WHERE product_id = ?");
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

        Product product = null;
        if (rs.next()) {
            product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
        }

        rs.close();
        stmt.close();
        return product;
    }



}
