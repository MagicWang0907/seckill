package com.wang.seckill.validator;

import com.wang.seckill.utils.ValidationUtils;
import org.apache.el.util.Validation;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private  boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidationUtils.isMobile(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;
            }
            else {
                return ValidationUtils.isMobile(value);
            }
        }
    }
}
