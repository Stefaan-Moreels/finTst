package be.albatroz.javacase.repository;

import be.albatroz.javacase.entity.Company;
import be.albatroz.javacase.entity.Person;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PersonRepository extends BaseRepository<Person> {

    List<Person> findAllByCompaniesContains(Company company, Sort sort);

}
