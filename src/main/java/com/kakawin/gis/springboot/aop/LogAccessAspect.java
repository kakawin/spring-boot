package com.kakawin.gis.springboot.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kakawin.gis.springboot.aop.annotation.LogAccess;
import com.kakawin.gis.springboot.aop.enumeration.AccessType;
import com.kakawin.gis.springboot.modules.system.entity.AccessLog;
import com.kakawin.gis.springboot.modules.system.mapper.AccessLogMapper;
import com.kakawin.gis.springboot.utils.SubjectUtil;
import com.kakawin.gis.springboot.utils.WebUtil;

@Aspect
@Component
public class LogAccessAspect {

	@Value("${system.constant.access-log-enable}")
	private boolean accessLogEnable;

	@Autowired
	AccessLogMapper accessLogMapper;

	@Pointcut("@annotation(com.kakawin.gis.springboot.aop.annotation.LogAccess)")
	@Order(-1)
	public void logAccess() {

	}

	@AfterReturning("logAccess()")
	public void doAfterReturning(JoinPoint joinPoint) {
		if (!accessLogEnable) {
			return;
		}
		AccessLog log = getFromJoinPoint(joinPoint);
		log.setExceptionFlag(Boolean.FALSE);
		accessLogMapper.insert(log);
	}

	@AfterThrowing(throwing = "ex", pointcut = "logAccess()")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		if (!accessLogEnable) {
			return;
		}
		AccessLog log = getFromJoinPoint(joinPoint);
		log.setExceptionFlag(Boolean.TRUE);
		log.setExceptionInfo(ex.toString());
		accessLogMapper.insert(log);
	}

	private AccessLog getFromJoinPoint(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Class<? extends Object> cls = joinPoint.getTarget().getClass();
		String accessDescription = null;
		Integer accessType = AccessType.UNKNOW.value();
		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature ms = (MethodSignature) joinPoint.getSignature();
			try {
				Method method = cls.getMethod(ms.getName(), ms.getParameterTypes());
				LogAccess annotation = AnnotationUtils.findAnnotation(method, LogAccess.class);
				accessDescription = annotation.desc();
				accessType = annotation.type().value();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String accessClass = cls.getName();
		String accessMethod = joinPoint.getSignature().getName();
		String accessParams = request.getMethod() + WebUtil.getParamMap(request).toString()
				+ Arrays.toString(joinPoint.getArgs());
		Date accessTime = new Date();
		String accessUsername = SubjectUtil.getUsername() + "|" + WebUtil.getRemoteHost(request);
		return new AccessLog(accessDescription, accessType, accessClass, accessMethod, accessParams,
				accessTime, accessUsername, null, null);
	}
}
