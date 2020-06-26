package be.albatroz.javacase.resource.mapper;

import be.albatroz.javacase.entity.PersonAddress;
import be.albatroz.javacase.resource.PersonAddressResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonAddressMapper extends BaseResourceMapper<PersonAddress, PersonAddressResource> {

    PersonAddressMapper INSTANCE = Mappers.getMapper(PersonAddressMapper.class);

    @Override
    PersonAddressResource toResource(PersonAddress entity);

    @Override
    PersonAddress fromResource(PersonAddressResource resource);

    @Override
    PersonAddress fromResource(PersonAddressResource resource, @MappingTarget PersonAddress entity);

}
