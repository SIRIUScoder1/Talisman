package com.talisman.daoImpl;

import com.talisman.dao.ProductDAO;
import com.talisman.entity.Product;
import com.talisman.model.Paginate;
import com.talisman.model.ProductInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Transactional
public class ProductDAOImpl implements ProductDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public ProductDAOImpl(final SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }


    @Override
    public Product findProduct(final String productCode) {

        Session currentSession = this.sessionFactory.getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();

        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate[] restrictions = new Predicate[] {
                builder.equal(productRoot.get("code"),productCode)
        };
        criteriaQuery.select(productRoot).where(restrictions);
        Query<Product> productQuery = currentSession.createQuery(criteriaQuery);
        List<Product> productList = productQuery.getResultList();
        if(productList == null || productList.isEmpty()) {
            return null;
        } else {
            return productList.get(0);
        }
    }

    @Override
    public ProductInfo findProductInformation(final String productCode) {

        Product product = this.findProduct(productCode);
        if(product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice());
    }

    @Override
    public Paginate<ProductInfo> queryProducts(final int page, final int maxResult, final int maxNavigationPage) {

        return this.queryProducts(page, maxResult, maxNavigationPage, null);
    }

    @Override
    public Paginate<ProductInfo> queryProducts(final int page, final int maxResult, final int maxNavigationPage,
                                               final String name) {

        String sqlQuery = "select new " + ProductInfo.class.getName() + "(p.code, p.name, p.price)" +
                            " from " +  Product.class.getName() + " p ";
        if(name != null && name.length() > 0) {
            sqlQuery += " Where lower(p.name) like :name";
        }
        sqlQuery += " order by p.createDate desc ";

        Session currentSession = this.sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery(sqlQuery);
        if(name != null && name.length() > 0) {
            query.setParameter("name","%"+name.toLowerCase()+"%");
        }
        return new Paginate<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }

    @Override
    public void save(final ProductInfo productInformation) {

        String productCode = productInformation.getCode();
        Product product = null;
        boolean isNewProduct = false;

        if(productCode != null) {
            product = this.findProduct(productCode);
        }
        if(product == null) {
            isNewProduct = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(productCode);
        product.setName(productInformation.getName());
        product.setPrice(productInformation.getPrice());

        if(productInformation.getImageData() != null) {
            byte[] image = productInformation.getImageData().getBytes();
            if(image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if(isNewProduct) {
            this.sessionFactory.getCurrentSession().persist(product);
        }
        // will throw hibernate exceptions if any.
        this.sessionFactory.getCurrentSession().flush();
    }
}
