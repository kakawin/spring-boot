package com.kakawin.gis.springboot.modules.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakawin.gis.springboot.modules.system.entity.LoginLog;
import com.kakawin.gis.springboot.modules.system.service.LoginLogService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;
import com.kakawin.gis.springboot.utils.SysResponse;
import com.kakawin.gis.springboot.utils.WebUtil;


@RestController
@RequestMapping("/loginlog")
public class LoginLogController {

	@Autowired
	private LoginLogService loginLogService;

	@RequiresPermissions("system:loginlog:view")
	@GetMapping("/page")
	public SysResponse getPage(RequestPage requestPage, HttpServletRequest request) {
		ResponsePage<LoginLog> responsePage = loginLogService.getPage(requestPage, WebUtil.getParamMap(request));
		return SysResponse.ok(responsePage);
	}

}
