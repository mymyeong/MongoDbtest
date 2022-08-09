package com.example.mongodbtest.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = [
        "com.example.mongodbtest.user"
    ]
)
class UserJpaConfig {

    @Value("\${spring.datasource.driver-class-name}")
    private val DRIVER_CLASS_NAME: String? = null

    @Value("\${spring.datasource.url.read-write}")
    private val READ_WRITE_URL: String? = null

    @Value("\${spring.datasource.url.read-only}")
    private val READ_ONLY_URL: String? = null

    @Value("\${spring.datasource.username}")
    private val USERNAME: String? = null

    @Value("\${spring.datasource.password}")
    private val PASSWORD: String? = null

//    @Value("\${spring.datasource.hikari.connection-init-sql}")
    private val CONNECTION_INIT_SQL: String = "SET NAMES utf8mb4"

    @Bean
    fun readWriteDataSource(): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = READ_WRITE_URL
        hikariConfig.driverClassName = DRIVER_CLASS_NAME
        hikariConfig.username = USERNAME
        hikariConfig.password = PASSWORD
        hikariConfig.connectionInitSql = CONNECTION_INIT_SQL
        hikariConfig.poolName = "readWrite"

        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun readOnlyDataSource(): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = READ_WRITE_URL
        hikariConfig.driverClassName = DRIVER_CLASS_NAME
        hikariConfig.username = USERNAME
        hikariConfig.password = PASSWORD
        hikariConfig.connectionInitSql = CONNECTION_INIT_SQL
        hikariConfig.poolName = "readOnly"

        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun dataSource(
        @Qualifier("readWriteDataSource") readWriteDataSource: DataSource,
        @Qualifier("readOnlyDataSource") readOnlyDataSource: DataSource
    ): DataSource {
        return LazyReplicationConnectionDataSourceProxy(
            readWriteDataSource,
            readOnlyDataSource
        )
    }

    @Bean
    fun entityManagerFactoryBuilder(): EntityManagerFactoryBuilder {
        return EntityManagerFactoryBuilder(HibernateJpaVendorAdapter(), HashMap<String, Any?>(), null)
    }

    @Primary
    @Bean
    fun entityManagerFactory(
        @Qualifier("entityManagerFactoryBuilder") builder: EntityManagerFactoryBuilder,
        @Qualifier("dataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val properties = mutableMapOf<String, Any?>()
        properties["hibernate.format_sql"] = false
        properties["hibernate.show_sql"] = false
        properties["hibernate.dialect"] = "org.hibernate.dialect.MySQLDialect"

        return builder
            .dataSource(dataSource)
            .packages(
                "com.example.mongodbtest.user"
            )
            .persistenceUnit("user")
            .properties(properties)
            .build()
    }

    @Primary
    @Bean
    fun transactionManager(
        @Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        val jpaTransactionManager = JpaTransactionManager()
        jpaTransactionManager.entityManagerFactory = entityManagerFactory

        return jpaTransactionManager
    }
}