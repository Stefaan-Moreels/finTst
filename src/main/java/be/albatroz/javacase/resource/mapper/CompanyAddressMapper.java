package be.albatroz.javacase.resource.mapper;

import be.albatroz.javacase.entity.CompanyAddress;
import be.albatroz.javacase.resource.CompanyAddressResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyAddressMapper extends BaseResourceMapper<CompanyAddress, CompanyAddressResource> {

    CompanyAddressMapper INSTANCE = Mappers.getMapper(CompanyAddressMapper.class);

    @Override
    CompanyAddressResource toResource(CompanyAddress entity);

    @Override
    CompanyAddress fromResource(CompanyAddressResource resource);

    @Override
    CompanyAddress fromResource(CompanyAddressResource resource, @MappingTarget CompanyAddress entity);

}
