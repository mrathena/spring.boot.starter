package com.mrathena.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/11/25 10:18
 */
@Aspect
@Component
public class IntegrationAspect extends AbstractIntegrationAspect {

	@Override
	@Around("execution (* com.mrathena.remote..*Integration.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		return super.around(point);
	}

}
