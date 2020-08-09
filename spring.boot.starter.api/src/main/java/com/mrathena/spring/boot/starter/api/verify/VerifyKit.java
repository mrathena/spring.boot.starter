package com.mrathena.spring.boot.starter.api.verify;

import com.mrathena.common.constant.Constant;
import com.mrathena.common.exception.BusinessErrorCodeEnum;
import com.mrathena.common.exception.BusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author com.mrathena on 2020-03-29 02:37
 */
public class VerifyKit {

	private VerifyKit() {}

	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static <T> void validate(T request) {
		// 验证结果
		Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(request, Default.class);
		if (!constraintViolationSet.isEmpty()) {
			// 整理验证结果
			Map<String, Set<String>> map = new HashMap<>(8);
			for (ConstraintViolation<T> cv : constraintViolationSet) {
				String key = cv.getPropertyPath().toString() + Constant.COLON + cv.getInvalidValue();
				Set<String> set = map.get(key);
				if (null == set) {
					Set<String> newSet = new HashSet<>(4);
					newSet.add(cv.getMessage());
					map.put(key, newSet);
				} else {
					set.add(cv.getMessage());
				}
			}
			if (!map.isEmpty()) {
				String collect = map.entrySet().stream().map(entry -> entry.getKey() + Constant.COLON + String.join(Constant.AND, entry.getValue())).collect(Collectors.joining(Constant.SEMICOLON));
				throw new BusinessException(BusinessErrorCodeEnum.ILLEGAL_ARGUMENT, collect);
			}
		}
	}

}
