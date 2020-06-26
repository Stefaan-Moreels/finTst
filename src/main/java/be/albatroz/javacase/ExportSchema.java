package be.albatroz.javacase;

import be.albatroz.javacase.entity.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;

public final class ExportSchema {

    public static void main(String[] args) {

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", org.hibernate.dialect.HSQLDialect.class)
                .build();

        MetadataSources metadata = new MetadataSources(registry)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(CompanyAddress.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(PersonAddress.class);

        EnumSet<TargetType> targetTypes = EnumSet.noneOf(TargetType.class);
        targetTypes.add(TargetType.STDOUT);

        new SchemaExport()
                .setDelimiter(";")
                .setFormat(true)
                .create(targetTypes, metadata.buildMetadata());
    }
}
