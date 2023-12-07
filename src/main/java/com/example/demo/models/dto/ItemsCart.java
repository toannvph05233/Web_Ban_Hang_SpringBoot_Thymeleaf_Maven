package com.example.demo.models.dto;


import com.example.demo.models.Product;

public class ItemsCart {
    private Product product;
    private int quantity;

    public ItemsCart() {
    }

    public ItemsCart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
