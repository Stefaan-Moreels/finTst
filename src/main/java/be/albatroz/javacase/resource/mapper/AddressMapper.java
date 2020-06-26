package be.albatroz.javacase.resource.mapper;

import be.albatroz.javacase.entity.Address;
import be.albatroz.javacase.resource.AddressResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseResourceMapper<Address, AddressResource> {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Override
    AddressResource toResource(Address entity);

    @Override
    Address fromResource(AddressResource resource);

    @Override
    Address fromResource(AddressResource resource, @MappingTarget Address entity);

}
