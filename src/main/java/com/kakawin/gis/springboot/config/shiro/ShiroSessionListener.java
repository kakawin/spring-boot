package com.kakawin.gis.springboot.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kakawin.gis.springboot.modules.system.entity.LoginLog;
import com.kakawin.gis.springboot.modules.system.service.impl.LoginLogServiceImpl;

@Component
public class ShiroSessionListener implements SessionListener {

	@Autowired
	private LoginLogServiceImpl loginLogService;

	@Override
	public void onExpiration(Session session) {
		String id = (String) session.getAttribute(LoginLog.SESSION_USER_LOG_ID);
		loginLogService.logCurrentUserLogout(id, LoginLog.EXPIRATION, session.getLastAccessTime());
	}

	@Override
	public void onStart(Session session) {
	}

	@Override
	public void onStop(Session session) {
		String id = (String) session.getAttribute(LoginLog.SESSION_USER_LOG_ID);
		loginLogService.logCurrentUserLogout(id, LoginLog.STOP, session.getLastAccessTime());
	}

}
