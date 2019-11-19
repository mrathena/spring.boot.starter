package com.mrathena.spring.boot.starter.api.y2019.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
		if (null == channel || channel.isEmpty()) {
			return false;
		}
		return Arrays.stream(DemoChannelEnum.values()).map(item -> item.name().toUpperCase()).collect(Collectors.toSet()).contains(channel.toUpperCase());
	}

}
