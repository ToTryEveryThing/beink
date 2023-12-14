package com.example.aliossspringbootstarter.annotation;

import com.example.aliossspringbootstarter.config.AliConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ToTryEveryThing
 * @date 2023/12/14 9:42
 * @Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AliConfig.class)
public @interface EnableAliConfig {
}
