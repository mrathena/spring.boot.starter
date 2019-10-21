package com.mrathena.spring.boot.starter.api;

import com.mrathena.common.toolkit.IdKit;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author mrathena on 2019/10/18 9:04
 */
@Setter
@ToString(callSuper = true)
public class BaseReqDto implements Serializable {

	/**
	 * 透传追踪号
	 */
	private String traceNo;

	public String getTraceNo() {
		return StringUtils.isBlank(this.traceNo) ? IdKit.getSerialNo() : this.traceNo;
	}

}
