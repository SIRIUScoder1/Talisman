package com.talisman.daoImpl;

import com.talisman.dao.OrderDAO;
import com.talisman.dao.ProductDAO;
import com.talisman.entity.Order;
import com.talisman.entity.OrderDetails;
import com.talisman.entity.Product;
import com.talisman.model.CartDetailInfo;
import com.talisman.model.CustomerInfo;
import com.talisman.model.CartProductInfo;
import com.talisman.model.OrderInfo;
import com.talisman.model.OrderDetailInfo;
import com.talisman.model.Paginate;

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
import java.util.UUID;

@Transactional
public class OrderDAOImpl implements OrderDAO {

    private SessionFactory sessionFactory;
    private ProductDAO productDAO;

    @Autowired
    public OrderDAOImpl(final SessionFactory sessionFactory, final ProductDAO productDAO) {

        this.sessionFactory = sessionFactory;
        this.productDAO = productDAO;
    }

    @Override
    public void saveOrder(final CartDetailInfo cartDetailInformation) {

        Session currentSession = this.sessionFactory.getCurrentSession();

        int orderNumber = this.getMaxOrderNumbers() + 1;

        Order orderInfo = new Order();
        orderInfo.setId(UUID.randomUUID().toString());
        orderInfo.setOrderNum(orderNumber);
        orderInfo.setOrderDate(new Date());
        orderInfo.setAmount(cartDetailInformation.getTotalAmount());

        CustomerInfo customerInformation = cartDetailInformation.getCustomerInformation();
        orderInfo.setCustomerName(customerInformation.getName());
        orderInfo.setCustomerEmail(customerInformation.getEmail());
        orderInfo.setCustomerAddress(customerInformation.getAddress());
        orderInfo.setCustomerPhone(customerInformation.getPhoneNumber());

        currentSession.persist(orderInfo);

        List<CartProductInfo> cartProductInfoList = cartDetailInformation.getCartProductInfoList();

        for(CartProductInfo cartProductInformation : cartProductInfoList) {

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setId(UUID.randomUUID().toString());
            orderDetails.setOrder(orderInfo);
            orderDetails.setAmount(cartProductInformation.getAmount());
            orderDetails.setPrice(cartProductInformation.getProductInformation().getPrice());
            orderDetails.setQuantity(cartProductInformation.getProductQuantity());

            String productCode = cartProductInformation.getProductInformation().getCode();
            Product product = this.productDAO.findProduct(productCode);
            orderDetails.setProduct(product);

            currentSession.persist(orderDetails);
        }
        cartDetailInformation.setOrderNumber(orderNumber);
    }

    @Override
    public Paginate<OrderInfo> getOrderInformation(final int page, final int maxResult, final int maxNavigationPage) {

        String sqlQuery = "Select new " + OrderInfo.class.getName() + "(ord.id, ord.orderDate, ord.orderNum, ord.amount," +
                "ord.customerName, ord.customerAddress, ord.customerPhone, ord.customerEmail)" + " from " + Order.class.getName() +
                " ord " + "order by ord.orderNum desc";

        Session currentSession = this.sessionFactory.getCurrentSession();
        Query hibernateQuery = currentSession.createQuery(sqlQuery);
        return new Paginate<OrderInfo>(hibernateQuery, page, maxResult, maxNavigationPage);
    }

    @Override
    public OrderInfo getOrderInformation(final String orderId) {

        Order order = this.findOrder(orderId);
        if(order == null) {
            return null;
        }
        return new OrderInfo(order.getId(), order.getOrderDate(),
                order.getOrderNum(), order.getAmount(), order.getCustomerName(),
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }

    @Override
    public List<OrderDetailInfo> getOrderDetailInformation(String orderId) {

        String sqlQuery = "select new " + OrderDetailInfo.class.getName() +
                "(d.id, d.product.code, d.product.name , d.quantity,d.price,d.amount)" +
                " from " + OrderDetails.class.getName() + " d " +
                "where d.order.id = :orderId";
        Session currentSession = this.sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery(sqlQuery);
        query.setParameter("orderId", orderId);

        return query.list();
    }

    private int getMaxOrderNumbers() {

        String sqlQuery = "select max(o.orderNum) from " + Order.class.getName() + " o ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sqlQuery);
        List<Integer> queryResultList = query.getResultList();
        if(queryResultList.isEmpty() || queryResultList == null || queryResultList.get(0) == null) {
            return 0;
        } else {
            Integer maxOrderNum = (Integer) queryResultList.get(0);
            return maxOrderNum;
        }
    }

    public Order findOrder(final String orderId) {

        Session currentSession = this.sessionFactory.getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();

        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Predicate[] restrictions = new Predicate[] {
                builder.equal(orderRoot.get("id"), orderId)
        };
        criteriaQuery.select(orderRoot).where(restrictions);
        Query<Order> orderQuery = currentSession.createQuery(criteriaQuery);
        List<Order> orderList = orderQuery.getResultList();
        if(orderList == null || orderList.isEmpty()) {
            return null;
        } else {
            return orderList.get(0);
        }
    }
}
