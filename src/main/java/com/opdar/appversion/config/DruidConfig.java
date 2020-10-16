package com.opdar.appversion.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.opdar.appversion.base.PageInterceptor;
import com.opdar.plugins.mybatis.core.GuSqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config.properties")
class DruidConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext ;

    @Bean(name = "dataSource",initMethod = "init",destroyMethod = "close")
    public DruidDataSource getDataSource() {
        Environment env = applicationContext.getEnvironment();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("druid.url"));
        dataSource.setUsername(env.getProperty("druid.username"));
        dataSource.setPassword(env.getProperty("druid.password"));
        dataSource.setDriverClassName(env.getProperty("druid.driverClassName"));
        dataSource.setInitialSize(100);
        dataSource.setMinIdle(100);
        dataSource.setMaxActive(200);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        dataSource.setValidationQuery("SELECT x");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        Properties prop = new Properties();
        prop.setProperty("config.decrypt","false");
        dataSource.setConnectProperties(prop);
        dataSource.setTimeBetweenLogStatsMillis(3600000);
        return dataSource;
    }

    @Bean("sqlSessionFactoryBean")
    public GuSqlSessionFactoryBean getLocalSessionFactoryBean(DataSource dataSource) {
        Environment env = applicationContext.getEnvironment();
        GuSqlSessionFactoryBean sqlSessionFactoryBean = new GuSqlSessionFactoryBean();
        sqlSessionFactoryBean.setPlugins(new PageInterceptor());
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTablePrefix("t_");
        sqlSessionFactoryBean.setBasePackage(env.getProperty("mapper.entity.scanBase"));
        try {
            sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(env.getProperty("mapper.location")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        Environment env = applicationContext.getEnvironment();
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(env.getProperty("mapper.mapper.scanBase"));
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
