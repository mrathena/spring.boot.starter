package com.mrathena.spring.boot.starter.api.business.y2019.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author mrathena on 2019/11/19 9:51
 */
@Getter
@AllArgsConstructor
public enum DemoChannelEnum {

	/**
	 * channel
	 */
	APP, H5;

	public static boolean isValid(String channel) {
		if (StringUtils.isEmpty(channel)) {
			return false;
		}
		return Arrays.stream(DemoChannelEnum.values()).map(Enum::name).collect(Collectors.toSet()).contains(channel);
	}

}
