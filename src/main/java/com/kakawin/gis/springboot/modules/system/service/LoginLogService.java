package com.kakawin.gis.springboot.modules.system.service;

import java.util.Date;
import java.util.Map;

import com.kakawin.gis.springboot.modules.system.entity.LoginLog;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

public interface LoginLogService {

	LoginLog create(LoginLog loginLog);

	LoginLog getOne(String id);

	ResponsePage<LoginLog> getPage(RequestPage requestPage, Map<String, Object> paramMap);

	LoginLog logCurrentUserLogin(String userAgent, String host);

	LoginLog logCurrentUserLogout(String id, String type, Date lastAccessTime);

	LoginLog update(LoginLog loginLog);

}

