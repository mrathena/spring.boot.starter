package com.mrathena.common.toolkit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author mrathena on 2019/11/7 18:23
 */
public final class LogKit {

	private LogKit() {}

	private static final String TRACE = "TRACE";
	private static final String CLASS = "CLASS";
	private static final String METHOD = "METHOD";

	public static void setTraceNo(String traceNo) {
		MDC.put(TRACE, traceNo);
	}

	public static void setClassName(String className) {
		MDC.put(CLASS, className);
	}

	public static void setMethodName(String methodName) {
		MDC.put(METHOD, methodName);
	}

	public static String getTraceNo() {
		String traceNo = MDC.get(TRACE);
		return StringUtils.isBlank(traceNo) ? IdKit.getUuid() : traceNo;
	}

}
