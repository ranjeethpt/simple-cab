package com.datarepublic.simplecab.configuration.annotation;

import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@PropertySource("classpath:configuration-default.properties")
@PropertySource(value = "classpath:configuration-${env}.properties", ignoreResourceNotFound = true)
public @interface AppProperties {
}
