package com.gongsi.app.service;


import com.gongsi.app.persistence.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.gongsi.app.service", "com.gongsi.app.persistence"})
@EnableTransactionManagement
public class TestConfig {

    @Bean
    public SessionFactory sessionFactory() {
        new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
        builder.addAnnotatedClasses(User.class).addProperties(cfg.getProperties());
        return builder.buildSessionFactory();
    }

    @Bean(name = "dataSource")
    public BasicDataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration().configure();
        ds.setDriverClassName(cfg.getProperty("hibernate.connection.driver_class"));
        ds.setUrl(cfg.getProperty("hibernate.connection.url"));
        ds.setUsername(cfg.getProperty("hibernate.connection.username"));
        ds.setPassword(cfg.getProperty("hibernate.connection.password"));
        ds.setInitialSize(Integer.parseInt(cfg.getProperty("hibernate.connection.pool_size")));
        return ds;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}