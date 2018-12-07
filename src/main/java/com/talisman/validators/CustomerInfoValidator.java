package com.talisman.validators;

import com.talisman.model.CustomerInfo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerInfoValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();


    @Override
    public boolean supports(Class<?> aClass) {

        return aClass == CustomerInfo.class;
    }

    @Override
    public void validate(Object object, Errors errors) {

        CustomerInfo customerInformation = (CustomerInfo) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.customerInformation.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "empty.customerInformation.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address", "empty.customerInformation.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"phoneNumber", "empty.customerInformation.phoneNumber");

        if(!emailValidator.isValid(customerInformation.getEmail())) {
            errors.rejectValue("email", "pattern.customerInformation.email");
        }
    }
}
