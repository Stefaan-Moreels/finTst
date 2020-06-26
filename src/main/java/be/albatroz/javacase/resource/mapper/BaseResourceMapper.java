package be.albatroz.javacase.resource.mapper;

import be.albatroz.javacase.JavaCaseApplication;
import be.albatroz.javacase.entity.*;
import be.albatroz.javacase.resource.*;
import be.albatroz.javacase.service.AddressService;
import be.albatroz.javacase.service.CompanyService;
import be.albatroz.javacase.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface BaseResourceMapper<E, R extends BaseResource<?>> {

    default <O extends BaseEntity> Long entityToId(O entity) {
        return entity != null ? entity.getId() : null;
    }

    default <O extends BaseEntity> List<Long> entitiesToIds(Set<O> entities) {
        return entities != null ? entities.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    R toResource(E entity);

    E fromResource(R resource);

    E fromResource(R resource, E entity);

    default List<R> toResources(Set<E> entities) {
        return toResources(entities.stream());
    }

    default List<R> toResources(List<E> entities) {
        return toResources(entities.stream());
    }

    default List<R> toResources(Stream<E> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    default <T> T getBean(Class<T> clazz) {
        return JavaCaseApplication.getApplicationContext().getBean(clazz);
    }

    default Company getCompanyById(Long id) {
        return id != null ? getBean(CompanyService.class).getOne(id) : null;
    }

    default Person getPersonById(Long id) {
        return id != null ? getBean(PersonService.class).getOne(id) : null;
    }

    default Address getAddressById(Long id) {
        return id != null ? getBean(AddressService.class).getOne(id) : null;
    }

    default CompanyResource companyToCompanyResource(Company company) {
        return CompanyMapper.INSTANCE.toResource(company);
    }

    default PersonResource personToPersonResource(Person person) {
        return PersonMapper.INSTANCE.toResource(person);
    }

    default PersonAddressResource personAddressToPersonAddressResource(PersonAddress address) {
        return PersonAddressMapper.INSTANCE.toResource(address);
    }

    default CompanyAddressResource companyAddressToCompanyAddressResource(CompanyAddress address) {
        return CompanyAddressMapper.INSTANCE.toResource(address);
    }

    default List<CompanyAddressResource> resourcesToResourceResources(List<CompanyAddress> entities) {
        return CompanyAddressMapper.INSTANCE.toResources(entities);
    }

}
