package com.talisman.model;

public class CartProductInfo {

    private ProductInfo productInformation;
    private int productQuantity;


    public CartProductInfo() {

        this.productQuantity = 0;
    }

    public ProductInfo getProductInformation() {

        return this.productInformation;
    }

    public int getProductQuantity() {

        return this.productQuantity;
    }

    public void setProductInformation(final ProductInfo productInformation) {

        this.productInformation = productInformation;
    }

    public void setProductQuantity(final int productQuantity) {

        this.productQuantity = productQuantity;
    }

    public double getAmount() {
        return this.productQuantity * this.productInformation.getPrice();
    }
}
