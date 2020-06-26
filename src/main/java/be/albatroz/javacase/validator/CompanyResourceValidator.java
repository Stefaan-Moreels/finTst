package be.albatroz.javacase.validator;

import be.albatroz.javacase.resource.CompanyAddressResource;
import be.albatroz.javacase.resource.CompanyResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CompanyResourceValidator implements Validator {

    public static final String ADDRESSES = "addresses";

    @Override
    public boolean supports(Class<?> clazz) {
        return CompanyResource.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CompanyResource companyResource = (CompanyResource) obj;

        if (companyResource.getAddresses() == null || companyResource.getAddresses().isEmpty()) {
            errors.rejectValue(ADDRESSES, "NotEmpty.adddresses");
        } else {
            // check als hq
            long hqs = companyResource.getAddresses() == null ? 0 :
                    companyResource.getAddresses().stream()
                            .map(CompanyAddressResource::isHq)
                            .filter((val) -> val).count();
            if (hqs == 0) {
                errors.rejectValue(ADDRESSES, "NoHQ.adddresses");
            } else if (hqs > 1) {
                errors.rejectValue(ADDRESSES, "ToManyHQ.adddresses");
            }
        }
    }

}
