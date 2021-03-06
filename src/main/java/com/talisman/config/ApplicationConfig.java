package com.talisman.config;

import com.talisman.dao.AccountDAO;
import com.talisman.dao.OrderDAO;
import com.talisman.dao.ProductDAO;
import com.talisman.daoImpl.AccountDAOImpl;
import com.talisman.daoImpl.OrderDAOImpl;
import com.talisman.daoImpl.ProductDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.hibernate.SessionFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("com.talisman.*")
@EnableTransactionManagement
@PropertySource("classpath:hibernate-cfg.properties")
public class ApplicationConfig {

    @Autowired
    Environment environment;

    @Bean(name = "accountDAO")
    @Autowired
    public AccountDAO getAccountDAO(final SessionFactory sessionFactory) {
        return new AccountDAOImpl(sessionFactory);
    }

    @Bean(name = "orderDAO")
    @Autowired
    public OrderDAO getOrderDAO(final SessionFactory sessionFactory, final ProductDAO productDAO) {
        return new OrderDAOImpl(sessionFactory, productDAO);
    }

    @Bean(name = "productDAO")
    @Autowired
    public ProductDAO getProductDAO(final SessionFactory sessionFactory) {
        return new ProductDAOImpl(sessionFactory);
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty("database-driver"));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("username"));
        dataSource.setPassword(environment.getProperty("password"));

        System.out.println("getting the datasource " + dataSource);

        return dataSource;
    }

    @Bean(name="sessionFactory")
    @Autowired
    public SessionFactory getSessionFactory(final DataSource dataSource) throws IOException {

        Properties properties = new Properties();

        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql",environment.getProperty("hibernate.show_sql"));
        properties.put("current_session_context_class", environment.getProperty("current_session_context_class"));

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

        localSessionFactoryBean.setPackagesToScan(new String[] {"com.talisman.entity"});
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.afterPropertiesSet();

        SessionFactory sessionFactory = localSessionFactoryBean.getObject();

        System.out.println("getting Session Factory " + sessionFactory);

        return sessionFactory;
    }

    @Bean(name="transactionManager")
    @Autowired
    public HibernateTransactionManager getTransactionManager(final SessionFactory sessionFactory) {

        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return  transactionManager;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(new String[] { "messages/validator" });
        return resourceBundleMessageSource;
    }
}
