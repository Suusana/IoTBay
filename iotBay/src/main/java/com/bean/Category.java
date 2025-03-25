package com.bean;

import java.util.List;

public class Category {
    private Integer categoryId;
    private String category;
    private List<Product> products;

    public Category() {
    }

    public Category(Integer categoryId, String category, List<Product> products) {
        this.categoryId = categoryId;
        this.category = category;
        this.products = products;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}