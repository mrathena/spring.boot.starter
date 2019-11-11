package com.mrathena.spring.boot.starter.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author mrathena on 2019/10/18 9:04
 */
@Getter
@Setter
@ToString(callSuper = true)
public class BaseReqDto implements Serializable {

	/**
	 * 透传追踪号
	 */
	private String traceNo;

	/**
	 * 通用分页参数
	 */
	private int pageSize;
	private int pageNo;
	private int offset;
	private int limit;

	public String getTraceNo() {
		return StringUtils.isBlank(this.traceNo) ? UUID.randomUUID().toString().replace("-", "") : this.traceNo;
	}

}
