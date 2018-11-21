package com.talisman.model;

public class OrderDetailInfo {

    private String id;
    private String productCode;
    private String productName;
    private int quantity;
    private double price;
    private double amount;

    public OrderDetailInfo(final String id, final String productCode, final String productName,
                           final int quantity, final double price, final double amount) {

        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {

        return id;
    }

    public String getProductCode() {

        return productCode;
    }

    public String getProductName() {

        return productName;
    }

    public int getQuantity() {

        return quantity;
    }

    public double getPrice() {

        return price;
    }

    public double getAmount() {

        return amount;
    }

    public void setId(final String id) {

        this.id = id;
    }

    public void setProductCode(final String productCode) {

        this.productCode = productCode;
    }

    public void setProductName(final String productName) {

        this.productName = productName;
    }

    public void setQuantity(final int quantity) {

        this.quantity = quantity;
    }

    public void setPrice(final double price) {

        this.price = price;
    }

    public void setAmount(final double amount) {

        this.amount = amount;
    }
}
