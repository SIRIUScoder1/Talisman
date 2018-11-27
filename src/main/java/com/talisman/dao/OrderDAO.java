package com.talisman.dao;

import com.talisman.model.CartDetailInfo;
import com.talisman.model.OrderDetailInfo;
import com.talisman.model.OrderInfo;
import com.talisman.model.Paginate;

import java.util.List;

public interface OrderDAO {

    public void saveOrder(final CartDetailInfo cartDetailInformation);

    public Paginate<OrderInfo> getOrderInformation(final int page, final int maxResult, final int maxNavigationPage);

    public OrderInfo getOrderInformation(final String orderId);

    public List<OrderDetailInfo> getOrderDetailInformation(final String orderId);
}
