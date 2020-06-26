package be.albatroz.javacase.resource.mapper;

import be.albatroz.javacase.entity.Company;
import be.albatroz.javacase.resource.CompanyResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends BaseResourceMapper<Company, CompanyResource> {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    @Override
    CompanyResource toResource(Company entity);

    @Override
    @Mapping(target = "people", ignore = true)
    Company fromResource(CompanyResource resource);

    @Override
    Company fromResource(CompanyResource resource, @MappingTarget Company entity);

}
