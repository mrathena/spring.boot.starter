package com.mrathena.common.toolkit;

import com.mrathena.common.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author com.mrathena on 2019/11/7 18:23
 */
public final class LogKit {

	private LogKit() {}

	private static final String TRACE = "TRACE";
	private static final String POSITION = "POSITION";

	public static void setClassNameAndMethodName(String className, String methodName) {
		MDC.put(POSITION, className + Constant.POINT + methodName);
	}

	public static void removeClassNameAndMethodName() {
		MDC.remove(POSITION);
	}

	public static void setTraceNo(String traceNo) {
		MDC.put(TRACE, traceNo);
	}

	public static String getTraceNo() {
		String traceNo = MDC.get(TRACE);
		return StringUtils.isBlank(traceNo) ? IdKit.getUuid() : traceNo;
	}

}
