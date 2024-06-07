package com.example.demo3.config;



  import org.hibernate.SessionFactory;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.ComponentScan;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
  import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
  import org.springframework.jdbc.datasource.DriverManagerDataSource;
  import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
  import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
  import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
  import org.springframework.orm.jpa.JpaTransactionManager;
  import org.springframework.orm.jpa.JpaVendorAdapter;
  import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
  import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
  import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

    @Configuration
    @ComponentScan("com.example.demo3")
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackages = "com.example.demo3.repos")
    public class HibernateConfig {

//@Bean
//public LocalSessionFactoryBean sessionFactory() {
//
//    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//    sessionFactory.setDataSource(dataSource());
//    sessionFactory.setPackagesToScan("com.example.demo3.models");
//    sessionFactory.setHibernateProperties(hibernateProperties());
//    return sessionFactory;
//}
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan("com.example.demo3.models");
            em.setJpaProperties(hibernateProperties());
            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            return em;
        }
        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/test");
            dataSource.setUsername("root");
            dataSource.setPassword("");
            return dataSource;
        }


        private Properties hibernateProperties() {
            Properties properties = new Properties();
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");
            properties.put("hibernate.hbm2ddl.auto", "update");

            return properties;
        }
        @Bean
        public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
            return transactionManager;
        }
    }




