package com.example.demo.config.aop.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ToTryEveryThing
 * @date 2023/8/13 17:30
 */
@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MyValidator.class)
public @interface MyValid {
    DataType type();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
