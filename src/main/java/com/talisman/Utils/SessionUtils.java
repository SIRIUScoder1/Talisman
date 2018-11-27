package com.talisman.Utils;

import com.talisman.model.CartDetailInfo;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {

    public static CartDetailInfo getCartDetailInSession(final HttpServletRequest request) {

        CartDetailInfo cartDetailInformation = (CartDetailInfo) request.getSession().getAttribute("myCart");

        if(cartDetailInformation == null) {
            cartDetailInformation = new CartDetailInfo();

            request.getSession().setAttribute("myCart", cartDetailInformation);
        }
        return cartDetailInformation;
    }

    public static void removeCartDetailInSession(final HttpServletRequest request) {

        request.getSession().removeAttribute("myCart");
    }

    public static void storeLastOrderCartDetailInSession(final HttpServletRequest request, final CartDetailInfo cartDetailInformation) {

        request.getSession().setAttribute("lastOrderedCart", cartDetailInformation);
    }

    public static CartDetailInfo getLastOrderCartDetailInSession(final HttpServletRequest request) {

        return (CartDetailInfo) request.getSession().getAttribute("lastOrderedCart");
    }
}
