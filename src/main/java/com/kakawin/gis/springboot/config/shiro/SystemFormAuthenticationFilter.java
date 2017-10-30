package com.kakawin.gis.springboot.config.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class SystemFormAuthenticationFilter extends FormAuthenticationFilter {

	/**
	 * 登陆成功，不跳转至原请求页面，只返回登陆成功信息
	 * 
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		return true;
	}
}
