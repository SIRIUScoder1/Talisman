package com.talisman.model;

import com.talisman.entity.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ProductInfo {

    private String code;
    private String name;
    private double price;
    private CommonsMultipartFile imageData;

    private boolean newProduct = false;

    public ProductInfo() {

    }

    public ProductInfo(final Product product) {

        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public ProductInfo(final String code, final String name, final double price) {

        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public CommonsMultipartFile getImageData() {
        return this.imageData;
    }

    public boolean isNewProduct() {
        return this.newProduct;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void setImageData(final CommonsMultipartFile imageData) {
        this.imageData = imageData;
    }

    public void setNewProduct(final boolean newProduct) {
        this.newProduct = newProduct;
    }
}
