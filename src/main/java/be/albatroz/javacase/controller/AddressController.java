package be.albatroz.javacase.controller;

import be.albatroz.javacase.resource.AddressResource;
import be.albatroz.javacase.resource.mapper.AddressMapper;
import be.albatroz.javacase.service.AddressService;
import be.albatroz.javacase.validator.AddressResourceValidator;
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
@RequestMapping(value = "address")
public class AddressController {

    @Setter(onMethod_ = {@Autowired})
    AddressService service;

    @Setter(onMethod_ = {@Autowired})
    AddressResourceValidator addressResourceValidator;

    @InitBinder({"addressResource"})
    public void initAddressBinder(WebDataBinder binder) {
        binder.addValidators(addressResourceValidator);
    }

    @PostMapping(value = "")
    public ResponseEntity<AddressResource> postAddress(@Valid @RequestBody AddressResource resource) {
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResource(
                service.save(AddressMapper.INSTANCE.fromResource(resource))));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<AddressResource>> getAll(@PageableDefault(sort = {"name"}) Sort sort) {
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResources(service.findAll(sort)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressResource> getOneperson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResource(service.getOne(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressResource> putperson(@PathVariable("id") Long id,
                                                     @Valid @RequestBody AddressResource resource) {
        Assert.isTrue(id.equals(resource.getId()), "Id mismatch");
        return ResponseEntity.ok(AddressMapper.INSTANCE.toResource(
                service.save(AddressMapper.INSTANCE.fromResource(resource, service.getOne(id)))));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteperson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

}
