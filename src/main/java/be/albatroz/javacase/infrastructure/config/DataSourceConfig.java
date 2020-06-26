package be.albatroz.javacase.infrastructure.config;

import be.albatroz.javacase.repository.BaseRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")

@EnableJpaRepositories(basePackages = "be.albatroz.javacase.*"
        , entityManagerFactoryRef = "mainEntityManagerFactory"
        , transactionManagerRef = "mainTransactionManager"
        , repositoryBaseClass = BaseRepositoryImpl.class
)

@EntityScan("be.albatroz.javacase.*")

@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class DataSourceConfig extends HikariConfig {

    @Bean
    @Primary
    public PlatformTransactionManager mainTransactionManager(Environment env) {
        return new JpaTransactionManager(mainEntityManagerFactory(env).getObject());
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(Environment env) {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.HSQLDialect");
        properties.setProperty(AvailableSettings.SHOW_SQL, env.getRequiredProperty("spring.jpa.show-sql"));

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(mainDataSource());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("be.albatroz.javacase");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean(name = "mainDataSource")
    @Primary
    @SuppressWarnings("Duplicates")
    public MainDataSource mainDataSource() {
        return new MainDataSource(this);
    }

    public static class MainDataSource extends HikariDataSource implements DataSource {
        MainDataSource(DataSourceConfig configuration) {
            super(configuration);
        }
    }
}