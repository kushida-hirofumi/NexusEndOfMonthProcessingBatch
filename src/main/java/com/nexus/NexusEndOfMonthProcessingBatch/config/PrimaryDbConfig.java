package com.nexus.NexusEndOfMonthProcessingBatch.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:properties/nexus_db.properties", encoding = "utf-8")
@MapperScan(
        basePackages = PrimaryDbConfig.MAPPER_PACKAGES,
        sqlSessionFactoryRef = PrimaryDbConfig.SQL_SESSION_FACTORY
)
public class PrimaryDbConfig {
    public static final String DATA_SOURCE_BEAN = "primaryDataSource";
    public static final String DATA_SOURCE = "datasource.primary";
    public static final String TRANSACTION_MANAGER_BEAN = "primaryTransactionManager";

    public static final String MAPPER_PACKAGES = "com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper";
    public static final String SQL_SESSION_FACTORY = "primarySqlSessionFactory";
    public static final String SQL_RESOURCE = "classpath:db/primary/**/*.xml";

    @Primary
    @Bean(name = DATA_SOURCE_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE)
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier(DATA_SOURCE_BEAN) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SQL_RESOURCE));
        return factory;
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER_BEAN)
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}