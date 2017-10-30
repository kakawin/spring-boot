package com.kakawin.gis.springboot.modules.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.kakawin.gis.springboot.modules.system.entity.LoginLog;
import com.kakawin.gis.springboot.modules.system.mapper.LoginLogMapper;
import com.kakawin.gis.springboot.modules.system.service.LoginLogService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;
import com.kakawin.gis.springboot.utils.SubjectUtil;

@Service
public class LoginLogServiceImpl implements LoginLogService {

	@Autowired
	private LoginLogMapper loginLogMapper;

	@Override
	@Transactional
	public LoginLog logCurrentUserLogin(String userAgent, String host) {
		Subject subject = SecurityUtils.getSubject();
		LoginLog loginLog = new LoginLog();
		loginLog.setHost(host);
		loginLog.setLoginTime(subject.getSession().getStartTimestamp());
		loginLog.setUsername(SubjectUtil.getUsername());
		loginLog.setUserAgent(userAgent);
		create(loginLog);
		subject.getSession().setAttribute(LoginLog.SESSION_USER_LOG_ID, loginLog.getId());
		return loginLog;
	}

	@Override
	@Transactional
	public LoginLog logCurrentUserLogout(String id, String type, Date lastAccessTime) {
		if (id == null) {
			return null;
		}
		LoginLog loginLog = loginLogMapper.selectById(id);
		loginLog.setLogoutTime(lastAccessTime);
		loginLog.setLogoutType(type);
		update(loginLog);
		return loginLog;
	}

	@Override
	public LoginLog getOne(String id) {
		return loginLogMapper.selectById(id);
	}

	@Override
	public LoginLog create(LoginLog loginLog) {
		loginLogMapper.insert(loginLog);
		return loginLog;
	}

	@Override
	public LoginLog update(LoginLog loginLog) {
		loginLogMapper.updateById(loginLog);
		return loginLog;
	}

	@Override
	public ResponsePage<LoginLog> getPage(RequestPage requestPage, Map<String, Object> paramMap) {
		PageHelper.startPage(requestPage.getPage(), requestPage.getSize());
		List<LoginLog> list = loginLogMapper.listByParamMap(paramMap);
		return new ResponsePage<LoginLog>(list);
	}

}
