package be.albatroz.javacase.resource.mapper;

import be.albatroz.javacase.entity.Person;
import be.albatroz.javacase.resource.PersonResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper extends BaseResourceMapper<Person, PersonResource> {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Override
    @Mapping(source = "companies", target = "companyIds")
    PersonResource toResource(Person entity);

    @Override
    @Mapping(target = "companies", source = "companyIds")
    Person fromResource(PersonResource resource);

    @Override
    Person fromResource(PersonResource resource, @MappingTarget Person entity);

}
