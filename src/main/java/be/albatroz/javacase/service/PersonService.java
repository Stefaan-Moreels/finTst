package be.albatroz.javacase.service;

import be.albatroz.javacase.entity.Company;
import be.albatroz.javacase.entity.Person;
import be.albatroz.javacase.repository.PersonRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService extends BaseService<Person, PersonRepository> {

    @Setter(onMethod_ = {@Autowired})
    private CompanyService companyService;

    public PersonService(PersonRepository repository) {
        super(Person.class, repository);
    }

    public List<Person> findAllByCompanyId(Long id, Sort sort) {
        return repository.findAllByCompaniesContains(companyService.getOne(id), sort);
    }

    public Person linkPersonCompany(Long personId, Long companyId) {
        Person person = getOne(personId);
        Company company = companyService.getOne(companyId);
        person.addCompany(company);
        return save(person);
    }

    public Person unlinkPersonCompany(Long personId, Long companyId) {
        Person person = getOne(personId);
        Company company = companyService.getOne(companyId);
        person.removeCompany(company);
        save(person);
        return save(person);
    }

}
