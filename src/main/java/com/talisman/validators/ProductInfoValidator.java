package com.talisman.validators;

import com.talisman.dao.ProductDAO;
import com.talisman.entity.Product;
import com.talisman.model.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductInfoValidator implements Validator {

    private ProductDAO productDAO;

    @Autowired
    public ProductInfoValidator(final ProductDAO productDAO) {

        this.productDAO = productDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == ProductInfo.class;
    }

    @Override
    public void validate(Object object, Errors errors) {

        ProductInfo productInformation = (ProductInfo) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "empty.productInformation.code");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.productInformation.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "empty.productInformation.price");

        String productCode = productInformation.getCode();
        if(productCode != null && productCode.length() > 0) {
            if(productCode.matches("\\s+")) {
                errors.rejectValue("code", "pattern.productInformation.code");
            } else if(productInformation.isNewProduct()) {
                Product product = this.productDAO.findProduct(productCode);
                if(product != null) {
                    errors.rejectValue("code","duplicate.productInformation.code");
                }
            }
        }
    }
}
