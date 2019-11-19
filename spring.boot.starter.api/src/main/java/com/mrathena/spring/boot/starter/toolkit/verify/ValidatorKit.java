package com.mrathena.spring.boot.starter.toolkit.verify;

import com.mrathena.common.toolkit.IdKit;
import com.mrathena.spring.boot.starter.api.y2019.demo.CreateDemoReqDTO;
import com.mrathena.spring.boot.starter.api.y2019.demo.DemoParentDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mrathena on 2019/11/18 16:37
 */
public class ValidatorKit {

	public static void main(String[] args) {
		CreateDemoReqDTO request = new CreateDemoReqDTO();
		request.setTraceNo(IdKit.getSerialNo());
		request.setDemo("this is a demo for javax validation");
		request.setProductNo("18234089811");
		request.setChannel("APP");
		request.setToken("this is a token");
		request.setParent(new DemoParentDTO("father", "mather"));
		request.setChannel2("APP");
		request.verify();
	}

	private ValidatorKit() {}

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static <T> void validate(T obj) {
		// 验证结果
		Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(obj, Default.class);
		if (!constraintViolationSet.isEmpty()) {
			// 整理验证结果
			Map<String, Set<String>> map = new HashMap<>(8);
			for (ConstraintViolation<T> cv : constraintViolationSet) {
				String key = cv.getPropertyPath().toString() + ":" + cv.getInvalidValue();
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
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
					Set<String> set = entry.getValue();
					sb.append(entry.getKey()).append(":").append(String.join("&", set)).append("; ");
				}
				throw new IllegalArgumentException(sb.toString());
			}
		}
	}

}
