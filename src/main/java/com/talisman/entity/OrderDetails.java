package com.talisman.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;

import java.io.Serializable;

@Entity
@Table(name = "Order_Details")
public class OrderDetails implements Serializable {

    private String id;
    private Order order;
    private Product product;
    private int quantity;
    private double price;
    private double amount;

    @Id
    @Column(name = "ID", length = 50, nullable = false)
    public String getId() {
        return this.id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false,
    foreignKey = @ForeignKey(name = "ORDER_DETAIL_FOREIGN_KEY"))
    public Order getOrder() {
        return this.order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false,
    foreignKey = @ForeignKey(name = "ORDER_DETAIL_FOREIGN_KEY"))
    public Product getProduct() {
        return this.product;
    }

    @Column(name = "Quantity", nullable = false)
    public int getQuantity() {
        return this.quantity;
    }

    @Column(name = "Price", nullable = false)
    public double getPrice() {
        return this.price;
    }

    @Column(name = "Amount", nullable = false)
    public double getAmount() {
        return this.amount;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public void setProduct(final Product product) {
        this.product = product;
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
