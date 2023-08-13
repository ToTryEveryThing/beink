package com.example.demo.config.aop.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ToTryEveryThing
 * @date 2023/8/13 17:31
 */
public class MyValidator implements ConstraintValidator<MyValid, String> {

    private DataType type;

    @Override
    public void initialize(MyValid constraintAnnotation) {
        // Initialization if needed
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(value);
        System.out.println(type);
        switch(type){
            case PHONE_NUMBER:
                return isValidPhoneNumber(value, context);
            case NOT_NULL:
                return isNotNull(value, context);
            default:
                return false;
        }
    }

    private boolean isNotNull(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()){
            String errorMessage = type.getErrorMessage(); // Get error message from DataType enum
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber, ConstraintValidatorContext context) {
        // Validate phone number format using a regular expression
        // The regular expression \d{11} matches 11 digits exactly
        if (!phoneNumber.matches("\\d{11}")) {
            String errorMessage = type.getErrorMessage(); // Get error message from DataType enum
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
