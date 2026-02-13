package com.nexus.NexusEndOfMonthProcessingBatch.config;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.security.Security;
import java.util.Arrays;
import java.util.Iterator;

/**
 * TKSのDBの構成クラス
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:properties/tks_server.properties", encoding = "utf-8")
@MapperScan(
        basePackages = SecondaryDbConfig.MAPPER_PACKAGES,
        sqlSessionFactoryRef = SecondaryDbConfig.SQL_SESSION_FACTORY
)
public class SecondaryDbConfig {
    public static final String MAPPER_PACKAGES = "com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.mapper";
    public static final String SQL_SESSION_FACTORY = "secondarySqlSessionFactory";
    public static final String DATA_SOURCE_BEAN = "secondaryDataSource";
    public static final String DATA_SOURCE = "datasource.tks-db";
    public static final String SQL_RESOURCE = "classpath:db/secondary/**/*.xml";
    public static final String TRANSACTION_MANAGER_BEAN = "secondaryTransactionManager";

    @PostConstruct
    public void postConstruct() {
        disabledAlgorithms();
    }

    @Bean(name = DATA_SOURCE_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE)
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier(DATA_SOURCE_BEAN) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SQL_RESOURCE));
        return factory;
    }

    @Bean(name = TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager createTransactionManager(@Qualifier(DATA_SOURCE_BEAN) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    void disabledAlgorithms() {
        String property = Security.getProperty("jdk.tls.disabledAlgorithms");
        Iterator<String> iterator = Arrays.stream(property.split(", ")).iterator();
        StringBuilder stringBuilder = new StringBuilder();
        do {
            String str = iterator.next();
            if(str.equals("TLSv1")) continue;
            if(!stringBuilder.isEmpty()) stringBuilder.append(", ");
            stringBuilder.append(str);
        } while (iterator.hasNext());
        Security.setProperty("jdk.tls.disabledAlgorithms", stringBuilder.toString());
    }

}