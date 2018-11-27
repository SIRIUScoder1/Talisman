package com.talisman.daoImpl;

import com.talisman.dao.AccountDAO;
import com.talisman.entity.UserAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
public class AccountDAOImpl implements AccountDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public AccountDAOImpl(final SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserAccount findAccount(final String userName) {

        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();

        CriteriaQuery<UserAccount> criteria = builder.createQuery(UserAccount.class);
        Root<UserAccount> userAccountRoot = criteria.from(UserAccount.class);
        Predicate[] restrictions = new Predicate[] {
                builder.equal(userAccountRoot.get("userName"),userName)
        };
        criteria.select(userAccountRoot).where(restrictions);
        Query<UserAccount> userAccountQuery = currentSession.createQuery(criteria);
        List<UserAccount> accountResults = userAccountQuery.getResultList();
        if(accountResults == null || accountResults.isEmpty()) {
            return null;
        } else {
            return accountResults.get(0);
        }
    }
}
