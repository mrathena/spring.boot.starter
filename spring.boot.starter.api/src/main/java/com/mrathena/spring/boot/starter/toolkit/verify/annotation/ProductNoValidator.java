package com.mrathena.spring.boot.starter.toolkit.verify.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author mrathena on 2019/11/18 9:05
 */
public class ProductNoValidator implements ConstraintValidator<ProductNo, String> {

	private static Pattern PATTERN = Pattern.compile("^1\\d{10}$");

	@Override
	public void initialize(ProductNo constraintAnnotation) {
		// 初始化，得到注解数据
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext context) {
		return PATTERN.matcher(s).matches();
	}

}
