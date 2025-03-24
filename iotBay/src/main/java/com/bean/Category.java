package com.bean;

public class Category {
    private Integer categoryId;
    private String category;
    private Integer productId;
    private String product;

    public Category() {
    }

    public Category(Integer categoryId, String category, Integer productId, String product) {
        this.categoryId = categoryId;
        this.category = category;
        this.productId = productId;
        this.product = product;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}