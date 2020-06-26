package be.albatroz.javacase.infrastructure.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V0__Migration extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));

        jdbcTemplate.execute("create sequence hibernate_sequence start with 1 increment by 1;");

        jdbcTemplate.execute("create table address ( " +
                "type varchar(31) not null, " +
                "id bigint not null, " +
                "country varchar(255), " +
                "municipality varchar(255), " +
                "number varchar(255), " +
                "street varchar(255), " +
                "zipCode varchar(255), " +
                "hq boolean, " +
                "company_id bigint, " +
                "primary key (id) " +
                ");");

        jdbcTemplate.execute("create table company ( " +
                "id bigint not null, " +
                "name varchar(255), " +
                "vat varchar(255), " +
                "primary key (id) " +
                ");");

        jdbcTemplate.execute("create table person ( " +
                "id bigint not null, " +
                "first_name varchar(255), " +
                "name varchar(255), " +
                "type varchar(255), " +
                "vat varchar(255), " +
                "address_id bigint, " +
                "primary key (id) " +
                ");");

        jdbcTemplate.execute("create table person_company ( " +
                "person_id bigint not null, " +
                "company_id bigint not null, " +
                " primary key (person_id, company_id)" +
                ");");

        jdbcTemplate.execute("create index idx_company_name on company (name);");
        jdbcTemplate.execute("create index idx_person_name on person (first_name, name);");

        jdbcTemplate.execute("alter table address " +
                "add constraint FK_address_company " +
                "foreign key (company_id) " +
                "references company;");

        jdbcTemplate.execute("alter table person " +
                "add constraint FK_person_address " +
                "foreign key (address_id) " +
                "references address;");

        jdbcTemplate.execute("alter table person_company " +
                "add constraint FK_person_company_company " +
                "foreign key (company_id) " +
                "references company;");

        jdbcTemplate.execute("alter table person_company " +
                "add constraint FK_person_company_person " +
                "foreign key (person_id) " +
                "references person;");

    }
}
