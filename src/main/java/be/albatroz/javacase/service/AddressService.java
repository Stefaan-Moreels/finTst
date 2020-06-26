package be.albatroz.javacase.service;

import be.albatroz.javacase.entity.Address;
import be.albatroz.javacase.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService<Address, AddressRepository> {

    public AddressService(AddressRepository repository) {
        super(Address.class, repository);
    }

}
