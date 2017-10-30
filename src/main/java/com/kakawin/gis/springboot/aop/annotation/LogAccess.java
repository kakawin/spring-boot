package com.kakawin.gis.springboot.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kakawin.gis.springboot.aop.enumeration.AccessType;


@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAccess {

	String desc() default "";

	AccessType type();
}
