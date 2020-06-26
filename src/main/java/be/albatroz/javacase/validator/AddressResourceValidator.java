package be.albatroz.javacase.validator;

import be.albatroz.javacase.resource.AddressResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddressResourceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AddressResource.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        AddressResource addressResource = (AddressResource) obj;

        if (StringUtils.isEmpty(addressResource.getStreet())) {
            errors.rejectValue("street", "NotEmpty.street");
        }
        if (StringUtils.isEmpty(addressResource.getStreet())) {
            errors.rejectValue("number", "NotEmpty.number");
        }
        if (StringUtils.isEmpty(addressResource.getStreet())) {
            errors.rejectValue("zipCode", "NotEmpty.zipCode");
        }
        if (StringUtils.isEmpty(addressResource.getStreet())) {
            errors.rejectValue("municipality", "NotEmpty.municipality");
        }
        if (StringUtils.isEmpty(addressResource.getStreet())) {
            errors.rejectValue("country", "NotEmpty.country");
        }

    }

}
