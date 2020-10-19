package com.opdar.appversion.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.opdar.appversion.base.PageInterceptor;
import com.opdar.plugins.mybatis.core.GuSqlSessionFactoryBean;
import org.apache.commons.io.FileUtils;
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
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("file:./config/config.properties")
class DruidConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext ;

    @Bean(name = "dataSource",initMethod = "init",destroyMethod = "close")
    public DruidDataSource getDataSource() {
        Environment env = applicationContext.getEnvironment();
        String sqlType = System.getenv("SQL_TYPE");
        if(StringUtils.isEmpty(sqlType)){
            String dbType = env.getProperty("db.type");
            if(StringUtils.isEmpty(dbType)){
                sqlType = "MYSQL";
            }else{
                sqlType = dbType;
            }
        }
        DruidDataSource dataSource = new DruidDataSource();
        if(sqlType.equalsIgnoreCase("H2")){
            dataSource.setUrl("jdbc:h2:file:./db/appversion;AUTO_RECONNECT=TRUE;MODE=MYSQL;AUTO_SERVER=TRUE");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            dataSource.setDriverClassName("org.h2.Driver");
            try {
                List<String> sqls = FileUtils.readLines(new File("./sqls/h2.sql"));
                if(sqls != null){
                    dataSource.setConnectionInitSqls(sqls);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String jdbcUrl = System.getenv("JDBC_URL");
            String jdbcUserName = System.getenv("JDBC_USERNAME");
            String jdbcPassword = System.getenv("JDBC_PASSWORD");
            if(StringUtils.isEmpty(jdbcUrl)){
                jdbcUrl = env.getProperty("druid.url");
            }
            if(StringUtils.isEmpty(jdbcUserName)){
                jdbcUserName = env.getProperty("druid.username");
            }
            if(StringUtils.isEmpty(jdbcPassword)){
                jdbcPassword = env.getProperty("druid.password");
            }
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(jdbcUserName);
            dataSource.setPassword(jdbcPassword);
            dataSource.setDriverClassName(env.getProperty("druid.driverClassName"));
            dataSource.setConnectionInitSqls(new LinkedList<String>(){{add("set names utf8mb4;");}});
            dataSource.setValidationQuery("SELECT x");
        }
        dataSource.setInitialSize(100);
        dataSource.setMinIdle(100);
        dataSource.setMaxActive(200);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
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
        sqlSessionFactoryBean.setBasePackage("com.opdar.appversion.entity");
        try {
            sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:com/opdar/appversion/mapper/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        Environment env = applicationContext.getEnvironment();
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.opdar.appversion.mapper");
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
