package be.albatroz.javacase.service;

import be.albatroz.javacase.entity.Company;
import be.albatroz.javacase.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends BaseService<Company, CompanyRepository> {

    public CompanyService(CompanyRepository repository) {
        super(Company.class, repository);
    }

    @Override
    public Company save(Company company) {
        company.getAddresses().forEach((address) -> address.setCompany(company));
        return super.save(company);
    }
}
