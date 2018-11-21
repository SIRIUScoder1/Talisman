package com.talisman.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Products")
public class Product implements Serializable {

    private String code;
    private String name;
    private double price;
    private byte[] image;

    private Date createDate;

    @Id
    @Column(name = "Product_Code", length = 20, nullable = false)
    public String getCode() {
        return this.code;
    }

    @Column(name = "Product_Name", length = 255, nullable = false)
    public String getName() {
        return this.name;
    }

    @Column(name = "Product_Price", length = 255, nullable = false)
    public double getPrice() {
        return this.price;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Product_Create_Date", nullable = false)
    public Date getCreateDate() {
        return this.createDate;
    }

    @Lob
    @Column(name = "Product_Image", nullable = false)
    public byte[] getImage() {
        return this.image;
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

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public void setImage(final byte[] image) {
        this.image = image;
    }
}
