package com.kakawin.gis.springboot.config.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

public class TokenWebSessionManager extends DefaultWebSessionManager {

	private static final String DEFAULT_TOKEN_NAME = "_token";

	@Value("${system.constant.token-name}")
	private String tokenName;

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String token = getParamToken(request);
		if (StringUtils.isEmpty(token)) {
			token = getHeaderToken((HttpServletRequest) request);
		}
		if (StringUtils.isEmpty(token)) {
			return super.getSessionId(request, response);
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Cookie template = getSessionIdCookie();
		Cookie cookie = new SimpleCookie(template);
		cookie.setValue(token);
		cookie.saveTo(req, res);

		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "url");
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
		return token;
	}

	private String getTokenName() {
		return tokenName != null ? tokenName : DEFAULT_TOKEN_NAME;
	}

	private String getParamToken(ServletRequest request) {
		return request.getParameter(getTokenName());
	}

	private String getHeaderToken(HttpServletRequest request) {
		return request.getHeader(getTokenName());
	}
}
