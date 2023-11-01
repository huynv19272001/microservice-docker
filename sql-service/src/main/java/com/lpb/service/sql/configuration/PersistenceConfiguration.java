package com.lpb.service.sql.configuration;

import com.lpb.service.sql.utils.constants.DBTypeEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.lpb.service", entityManagerFactoryRef = "multiEntityManager", transactionManagerRef = "multiTransactionManager")
public class PersistenceConfiguration {
    @Autowired
    private Environment env;

    private final String PACKAGE_SCAN = "com.lpb.service";

    @Primary
    @Bean(name = "mainDataSource")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.main.driverClassName")).url(env.getProperty("app.datasource.main.jdbc-url")).username(env.getProperty("app.datasource.main.username")).password(env.getProperty("app.datasource.main.password")).type(HikariDataSource.class).build();
    }

    @Bean(name = "esbDataSource")
    public DataSource esbDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.esb.driverClassName")).url(env.getProperty("app.datasource.esb.jdbc-url")).username(env.getProperty("app.datasource.esb.username")).password(env.getProperty("app.datasource.esb.password")).type(HikariDataSource.class).build();
    }

    @Bean(name = "coreDataSource")
    public DataSource coreDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.core.driverClassName")).url(env.getProperty("app.datasource.core.jdbc-url")).username(env.getProperty("app.datasource.core.username")).password(env.getProperty("app.datasource.core.password")).type(HikariDataSource.class).build();
    }

    @Bean(name = "coreDataSource2")
    public DataSource coreDataSource2() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.core2.driverClassName")).url(env.getProperty("app.datasource.core2.jdbc-url")).username(env.getProperty("app.datasource.core2.username")).password(env.getProperty("app.datasource.core2.password")).type(HikariDataSource.class).build();
    }

    @Bean(name = "PMDGDataSource")
    public DataSource PMDGDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.pmdg.driverClassName")).url(env.getProperty("app.datasource.pmdg.jdbc-url")).username(env.getProperty("app.datasource.pmdg.username")).password(env.getProperty("app.datasource.pmdg.password")).build();
    }

    @Bean(name = "WEBLPBDataSource")
    public DataSource WEBLPBDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.weblpb.driverClassName")).url(env.getProperty("app.datasource.weblpb.jdbc-url")).username(env.getProperty("app.datasource.weblpb.username")).password(env.getProperty("app.datasource.weblpb.password")).build();
    }

    @Bean(name = "DWHDataSource")
    public DataSource DWHDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.dwh.driverClassName")).url(env.getProperty("app.datasource.dwh.jdbc-url")).username(env.getProperty("app.datasource.dwh.username")).password(env.getProperty("app.datasource.dwh.password")).build();
    }

    @Bean(name = "LOSDataSource")
    public DataSource LOSDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.los.driverClassName")).url(env.getProperty("app.datasource.los.jdbc-url")).username(env.getProperty("app.datasource.los.username")).password(env.getProperty("app.datasource.los.password")).build();
    }

    @Bean(name = "LV24DataSource")
    public DataSource LV24DataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.lv24.driverClassName")).url(env.getProperty("app.datasource.lv24.jdbc-url")).username(env.getProperty("app.datasource.lv24.username")).password(env.getProperty("app.datasource.lv24.password")).build();
    }

    @Bean(name = "swiftDataSource")
    public DataSource swiftDataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty("app.datasource.swift.driverClassName")).url(env.getProperty("app.datasource.swift.jdbc-url")).username(env.getProperty("app.datasource.swift.username")).password(env.getProperty("app.datasource.swift.password")).build();
    }

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MAIN, mainDataSource());
        targetDataSources.put(DBTypeEnum.ESB, esbDataSource());
        targetDataSources.put(DBTypeEnum.CORE, coreDataSource());
        targetDataSources.put(DBTypeEnum.CORE2, coreDataSource2());
        targetDataSources.put(DBTypeEnum.CORE_LOS, LOSDataSource());
        targetDataSources.put(DBTypeEnum.PMDG, PMDGDataSource());
        targetDataSources.put(DBTypeEnum.WEBLPB, WEBLPBDataSource());
        targetDataSources.put(DBTypeEnum.DWH, DWHDataSource());
        targetDataSources.put(DBTypeEnum.LV24, LV24DataSource());
        targetDataSources.put(DBTypeEnum.SWIFT, swiftDataSource());

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(mainDataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(multiEntityManager().getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }
}
