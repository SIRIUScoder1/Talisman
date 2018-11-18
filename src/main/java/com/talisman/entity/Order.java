package com.talisman.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Orders", uniqueConstraints = {@UniqueConstraint(columnNames = "Order_Num")})
public class Order implements Serializable {

    private String id;
    private Date orderDate;
    private int orderNum;
    private double amount;

    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;


    @Id
    @Column(name = "ID", length = 50)
    public String getId() {
        return this.id;
    }

    @Column(name = "Order_Date", nullable = false)
    public Date getOrderDate() {
        return this.orderDate;
    }

    @Column(name = "Order_Num", nullable = false)
    public int getOrderNum() {
        return this.orderNum;
    }

    @Column(name = "Amount", nullable = false)
    public double getAmount() {
        return this.amount;
    }

    @Column(name = "Customer_Name", length = 255, nullable = false)
    public String getCustomerName() {
        return this.customerName;
    }

    @Column(name = "Customer_Address", length = 255, nullable = false)
    public String getCustomerAddress() {
        return this.customerAddress;
    }

    @Column(name = "Customer_Email", length = 255, nullable = false)
    public String getCustomerEmail() {
        return this.customerEmail;
    }

    @Column(name = "Customer_Phone", length = 255, nullable = false)
    public String getCustomerPhone() {
        return this.customerPhone;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderNum(final int orderNum) {
        this.orderNum = orderNum;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerAddress(final String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerEmail(final String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerPhone(final String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
