package be.albatroz.javacase.validator;

import be.albatroz.javacase.common.PersonType;
import be.albatroz.javacase.resource.PersonResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonResourceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonResource.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        PersonResource personResource = (PersonResource) obj;

        if (personResource.getType() == PersonType.FREELANCER && StringUtils.isEmpty(personResource.getVat())) {
            errors.rejectValue("vat", "NotEmpty.vat");
        }

        if (personResource.getAddress() == null) {
            errors.rejectValue("address", "NotEmpty.address");
        }

        if (personResource.getCompanyIds() == null || personResource.getCompanyIds().isEmpty()) {
            errors.rejectValue("companyIds", "NotEmpty.companies");
        }

    }

}
