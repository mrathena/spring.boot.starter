package com.mrathena.spring.boot.starter.toolkit.verify.annotation;

import java.lang.annotation.*;

/**
 * @author mrathena on 2019/11/14 14:04
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ProductNo {

	String message() default "must be correct telephone format";

}