package be.albatroz.javacase.controller;

import be.albatroz.javacase.resource.PersonResource;
import be.albatroz.javacase.resource.mapper.PersonMapper;
import be.albatroz.javacase.service.PersonService;
import be.albatroz.javacase.validator.AddressResourceValidator;
import be.albatroz.javacase.validator.PersonResourceValidator;
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
@RequestMapping(value = "person")
public class PersonController {

    @Setter(onMethod_ = {@Autowired})
    PersonService service;

    @Setter(onMethod_ = {@Autowired})
    PersonResourceValidator personResourceValidator;

    @Setter(onMethod_ = {@Autowired})
    AddressResourceValidator addressResourceValidator;

    @InitBinder({"personResource"})
    public void initPersonBinder(WebDataBinder binder) {
        binder.addValidators(personResourceValidator);
    }

    @InitBinder({"addressResource"})
    public void initAddressBinder(WebDataBinder binder) {
        binder.addValidators(addressResourceValidator);
    }

    @PostMapping(value = "")
    public ResponseEntity<PersonResource> postperson(@Valid @RequestBody PersonResource resource) {
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResource(
                service.save(PersonMapper.INSTANCE.fromResource(resource))));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<PersonResource>> getAll(@PageableDefault(sort = {"name"}) Sort sort) {
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResources(service.findAll(sort)));
    }

    @GetMapping(value = "company/{id}")
    public ResponseEntity<List<PersonResource>> getAllByCompanyId(@PathVariable("id") Long id, @PageableDefault(sort = {"name"}) Sort sort) {
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResources(service.findAllByCompanyId(id, sort)));
    }

    @PostMapping(value = "{personId}/company/{companyId}")
    public ResponseEntity<PersonResource> linkPersonCompany(
            @PathVariable("personId") Long personId,
            @PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResource(service.linkPersonCompany(personId, companyId)));
    }

    @DeleteMapping(value = "{personId}/company/{companyId}")
    public ResponseEntity<PersonResource> unlinkPersonCompany(
            @PathVariable("personId") Long personId,
            @PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResource(service.unlinkPersonCompany(personId, companyId)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonResource> getOneperson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResource(service.getOne(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonResource> putperson(@PathVariable("id") Long id,
                                                    @Valid @RequestBody PersonResource resource) {
        Assert.isTrue(id.equals(resource.getId()), "Id mismatch");
        return ResponseEntity.ok(PersonMapper.INSTANCE.toResource(
                service.save(PersonMapper.INSTANCE.fromResource(resource, service.getOne(id)))));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteperson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

}
