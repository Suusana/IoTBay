package com.bean;

import java.util.List;
import java.io.Serializable;
public class Cart implements Serializable {
    private Integer cartId;
    private Long userId;
    private List<Product> products;
    private double totalPrice;

    public Cart() {
    }

    public Cart(Integer cartId, Long userId, List<Product> products, double totalPrice) {
        this.cartId = cartId;
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
