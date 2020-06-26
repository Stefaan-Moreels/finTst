package be.albatroz.javacase.validation;

import be.albatroz.javacase.integration.RandomObjects;
import be.albatroz.javacase.resource.AddressResource;
import be.albatroz.javacase.resource.CompanyResource;
import be.albatroz.javacase.service.CompanyService;
import be.albatroz.javacase.validator.AddressResourceValidator;
import be.albatroz.javacase.validator.CompanyResourceValidator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({CompanyService.class, RandomObjects.class, AddressResourceValidator.class})

public class AddressValidationTests {

    @Setter(onMethod_ = {@Autowired})
    private RandomObjects randomObjects;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateInvalidCompanyEmptyNameTest() throws Exception {

        AddressResource address = randomObjects.createPersonAddressResource();
        address.setNumber(null);

        final Set<ConstraintViolation<AddressResource>> violationSet = validator.validate(address);
        assertEquals(1,violationSet.size());
    }


}
