package com.talisman.dao;

import com.talisman.entity.Product;
import com.talisman.model.Paginate;
import com.talisman.model.ProductInfo;

public interface ProductDAO {

    public Product findProduct(final String productCode);

    public ProductInfo findProductInformation(final String productCode);

    public Paginate<ProductInfo> queryProducts(final int page, final int maxResult, final int maxNavigationPage);

    public Paginate<ProductInfo> queryProducts(final int page, final int maxResult, final int maxNavigationPage,
                                               final String name);

    public void save(final ProductInfo productInformation);
}
