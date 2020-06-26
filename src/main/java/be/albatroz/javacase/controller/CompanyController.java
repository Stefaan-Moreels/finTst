package be.albatroz.javacase.controller;

import be.albatroz.javacase.resource.CompanyResource;
import be.albatroz.javacase.resource.mapper.CompanyMapper;
import be.albatroz.javacase.service.CompanyService;
import be.albatroz.javacase.validator.CompanyResourceValidator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "company")
public class CompanyController {

    @Setter(onMethod_ = {@Autowired})
    CompanyService service;

    @Setter(onMethod_ = {@Autowired})
    CompanyResourceValidator companyResourceValidator;

    @InitBinder({"companyResource"})
    public void initPersonBinder(WebDataBinder binder) {
        binder.addValidators(companyResourceValidator);
    }

    @PostMapping(value = "")
    public ResponseEntity<CompanyResource> postCompany(@Valid @RequestBody CompanyResource resource) {
        return ResponseEntity.ok(CompanyMapper.INSTANCE.toResource(
                service.save(CompanyMapper.INSTANCE.fromResource(resource))));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CompanyResource>> getAll(@PageableDefault(sort = {"name"}) Sort sort) {
        return ResponseEntity.ok(CompanyMapper.INSTANCE.toResources(service.findAll(sort)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyResource> getOneCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(CompanyMapper.INSTANCE.toResource(service.getOne(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CompanyResource> putCompany(@PathVariable("id") Long id,
                                                      @Valid @RequestBody CompanyResource resource) {
        Assert.isTrue(id.equals(resource.getId()), "Id mismatch");
        return ResponseEntity.ok(CompanyMapper.INSTANCE.toResource(
                service.save(CompanyMapper.INSTANCE.fromResource(resource, service.getOne(id)))));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

}
