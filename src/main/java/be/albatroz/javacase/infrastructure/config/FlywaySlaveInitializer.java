package be.albatroz.javacase.infrastructure.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.flyway")
public class FlywaySlaveInitializer {

    private static final String MIGRATION_PATH = "be.albatroz.javacase.infrastructure.migrations";


    @Setter(onMethod_ = {@Autowired})
    DataSourceConfig.MainDataSource mainDataSource;

    private String baselineDescription;
    private String baselineVersion;
    private Boolean baselineOnMigrate;
    private String table;

    @PostConstruct
    public void postConstruct() {
        log.info("FlywaySlaveInitializer.postConstruct()");
    }

    public void migrate() {
        log.info("FlywaySlaveInitializer.migrate()");
        try {
            Flyway.configure()
                    .baselineDescription(baselineDescription)
                    .baselineVersion(MigrationVersion.fromVersion(baselineVersion))
                    .baselineOnMigrate(baselineOnMigrate)
                    .locations(MIGRATION_PATH)
                    .table(table)
                    .dataSource(mainDataSource)
                    .schemas("PUBLIC")
                    .load()
                    .migrate();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}