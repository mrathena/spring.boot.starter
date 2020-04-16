package com.mrathena.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/22 14:26
 */
@Aspect
@Component
public class ServiceHandleAspect extends AbstractServiceAspect {

	@Around("execution (* com.mrathena.service..*Impl.*(..))")
	public Object around(ProceedingJoinPoint point) {
		return super.around(point);
	}

}
