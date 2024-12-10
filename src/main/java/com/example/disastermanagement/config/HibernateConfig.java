package com.example.disastermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.example.disastermanagement.models"); // Scans for @Entity classes
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        try {
            sessionFactoryBean.afterPropertiesSet(); // Initialize the LocalSessionFactoryBean
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SessionFactory", e);
        }
        return sessionFactoryBean.getObject(); // Return the actual SessionFactory object
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/disaster_db"); // Replace with your DB URL
        dataSource.setUsername("root"); // Replace with your DB username
        dataSource.setPassword("Liberty@1992"); // Replace with your DB password
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql", "true"); // Enable SQL logging
        properties.put("hibernate.format_sql", "true"); // Format SQL for readability
        properties.put("hibernate.hbm2ddl.auto", "update"); // Automatically update the schema
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}