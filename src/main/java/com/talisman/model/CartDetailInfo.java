package com.talisman.model;

import java.util.ArrayList;
import java.util.List;

public class CartDetailInfo {

    private int orderNumber;
    private CustomerInfo customerInformation;
    private CartProductInfo cartProductInformation;
    private final List<CartProductInfo> cartProductInfoList = new ArrayList<CartProductInfo>();

    public int getOrderNumber() {

        return this.orderNumber;
    }

    public CustomerInfo getCustomerInformation() {

        return this.customerInformation;
    }

    public List<CartProductInfo> getCartProductInfoList() {

        return this.cartProductInfoList;
    }

    public void addProductAndQuantity(final ProductInfo productInformation, final int productQuantity) {

        CartProductInfo cartProductInfo = this.findProductByCode(productInformation.getCode());
        int newQuantity;

        if(cartProductInfo == null) {

            cartProductInfo = new CartProductInfo();
            cartProductInfo.setProductInformation(productInformation);
            cartProductInfo.setProductQuantity(0);
        }

        newQuantity = cartProductInfo.getProductQuantity() + productQuantity;

        if(newQuantity > 0) {

            cartProductInfo.setProductQuantity(newQuantity);
        } else {

            this.cartProductInfoList.remove(cartProductInfo);
        }
    }

    public void updateProductQuantity(final String productCode, final int productQuantity) {

        CartProductInfo cartProductInformation = this.findProductByCode(productCode);

        if(cartProductInformation != null) {

            if(productQuantity > 0) {
                cartProductInformation.setProductQuantity(productQuantity);
            } else {
                this.cartProductInfoList.remove(cartProductInformation);
            }
        }
    }

    public void removeProduct(final ProductInfo productInformation) {

        CartProductInfo cartProductInformation = this.findProductByCode(productInformation.getCode());
        if(cartProductInformation != null) {

            this.cartProductInfoList.remove(cartProductInformation);
        }

    }

    private CartProductInfo findProductByCode(final String productCode) {

        for(CartProductInfo cartProductInfo : this.cartProductInfoList) {
            if(cartProductInfo.getProductInformation().getCode().equals(productCode)) {
                return cartProductInfo;
            }
        }
        return null;
    }

    public boolean isEmpty() {

        return this.cartProductInfoList.isEmpty();
    }

    public boolean isValidCustomer() {

        return (this.customerInformation.isValid() && this.customerInformation != null);
    }

    public int getTotalQuantity() {

        int quantity = 0;
        for(CartProductInfo cartProductInformation : this.cartProductInfoList) {

            quantity += cartProductInformation.getProductQuantity();
        }
        return quantity;
    }

    public double getTotalAmount() {

        double totalAmount = 0;
        for(CartProductInfo cartProductInformation : this.cartProductInfoList) {

            totalAmount += cartProductInformation.getAmount();
        }

        return totalAmount;
    }

    public void updateAllProducts(final CartDetailInfo cartDetailInformation) {

        List<CartProductInfo> cartProductInfoList = cartDetailInformation.getCartProductInfoList();
        for(CartProductInfo cartProductInfo : cartProductInfoList) {
            this.updateProductQuantity(cartProductInfo.getProductInformation().getCode(), cartProductInfo.getProductQuantity());
        }
    }

    public void setOrderNumber(final int orderNumber) {

        this.orderNumber = orderNumber;
    }

    public void setCustomerInformation(final CustomerInfo customerInformation) {

        this.customerInformation = customerInformation;
    }

}
