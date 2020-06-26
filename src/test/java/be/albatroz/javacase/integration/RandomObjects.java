package be.albatroz.javacase.integration;

import be.albatroz.javacase.common.PersonType;
import be.albatroz.javacase.entity.*;
import be.albatroz.javacase.resource.CompanyAddressResource;
import be.albatroz.javacase.resource.CompanyResource;
import be.albatroz.javacase.resource.PersonAddressResource;
import be.albatroz.javacase.resource.PersonResource;
import be.albatroz.javacase.service.CompanyService;
import com.github.javafaker.Faker;
import lombok.Setter;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RandomObjects {

    public static final String VAT_REGEX = "BE0[0-9]{9}";
    Faker faker = new Faker();

    @Setter(onMethod_ = {@Autowired})
    private CompanyService companyService;

    public Person createPerson() {
        Person person = new Person();
        person.setName(faker.name().name());
        person.setFirstName(faker.name().firstName());
        person.setType(faker.bool().bool() ? PersonType.FREELANCER : PersonType.EMPLOYER);
        if (person.getType() == PersonType.FREELANCER) {
            person.setVat(faker.regexify(VAT_REGEX));
        }
        person.setAddress(createPersonAddress());
        person.setCompanies(getRandomCompanies(faker.number().numberBetween(1, 2)));
        return person;
    }

    public PersonResource createPersonResource() {
        PersonResource person = new PersonResource();
        person.setName(faker.name().name());
        person.setFirstName(faker.name().firstName());
        person.setType(faker.bool().bool() ? PersonType.FREELANCER : PersonType.EMPLOYER);
        if (person.getType() == PersonType.FREELANCER) {
            person.setVat(faker.regexify(VAT_REGEX));
        }
        person.setAddress(createPersonAddressResource());
        person.setCompanyIds(getRandomCompanyIds(faker.number().numberBetween(1, 2)));
        return person;
    }

    private List<Long> getRandomCompanyIds(int amount) {
        return getRandomCompanies(amount).stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    private Set<Company> getRandomCompanies(int amount) {
        final List<Company> collect = companyService.findAll();
        if (!collect.isEmpty() && amount > 0) {
            return new HashSet<>(collect.subList(0, Math.min(collect.size(), amount)));
        }
        return new HashSet<>();
    }

    public PersonAddress createPersonAddress() {
        PersonAddress personAddress = new PersonAddress();
        personAddress.setStreet(faker.address().streetName());
        personAddress.setNumber(faker.address().streetAddressNumber());
        personAddress.setZipCode(faker.address().zipCode());
        personAddress.setMunicipality(faker.address().cityName());
        personAddress.setCountry(faker.address().country());
        return personAddress;
    }

    public PersonAddressResource createPersonAddressResource() {
        PersonAddressResource personAddress = new PersonAddressResource();
        personAddress.setStreet(faker.address().streetName());
        personAddress.setNumber(faker.address().streetAddressNumber());
        personAddress.setZipCode(faker.address().zipCode());
        personAddress.setMunicipality(faker.address().cityName());
        personAddress.setCountry(faker.address().country());
        return personAddress;
    }

    public CompanyAddressResource createCompanyAddressResource(boolean hq) {
        CompanyAddressResource companyAddressResource = new CompanyAddressResource();
        companyAddressResource.setStreet(faker.address().streetName());
        companyAddressResource.setNumber(faker.address().streetAddressNumber());
        companyAddressResource.setZipCode(faker.address().zipCode());
        companyAddressResource.setMunicipality(faker.address().cityName());
        companyAddressResource.setCountry(faker.address().country());
        companyAddressResource.setHq(hq);
        return companyAddressResource;
    }

    public CompanyAddress createCompanyAddress(boolean hq) {
        CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setId(faker.number().numberBetween(1L, 500L));
        companyAddress.setStreet(faker.address().streetName());
        companyAddress.setNumber(faker.address().streetAddressNumber());
        companyAddress.setZipCode(faker.address().zipCode());
        companyAddress.setMunicipality(faker.address().cityName());
        companyAddress.setCountry(faker.address().country());
        companyAddress.setHq(hq);
        return companyAddress;
    }

    public CompanyResource createCompanyResource() {
        CompanyResource companyResource = new CompanyResource();

        companyResource.setName(faker.company().name());
        companyResource.setVat(faker.regexify(VAT_REGEX));
        companyResource.setAddresses(new ArrayList<>(Collections.singletonList(createCompanyAddressResource(true))));

        return companyResource;
    }

    public Company createCompany() {
        Company company = new Company();

        company.setId(faker.number().numberBetween(1L, 500L));
        company.setName(faker.company().name());
        company.setVat(faker.regexify(VAT_REGEX));
        company.setAddresses(new HashSet<>(Collections.singletonList(createCompanyAddress(true))));

        return company;
    }
}
