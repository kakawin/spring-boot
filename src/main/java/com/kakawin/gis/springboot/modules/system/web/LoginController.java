package com.kakawin.gis.springboot.modules.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kakawin.gis.springboot.modules.system.entity.LoginLog;
import com.kakawin.gis.springboot.modules.system.service.LoginLogService;
import com.kakawin.gis.springboot.utils.ResponseCode;
import com.kakawin.gis.springboot.utils.SubjectUtil;
import com.kakawin.gis.springboot.utils.SysResponse;
import com.kakawin.gis.springboot.utils.WebUtil;


@Controller
public class LoginController {

	@Autowired
	private LoginLogService loginLogService;

	/**
	 * WEB端登陆成功返回主页
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		if (SubjectUtil.isAuthenticated()) {
			return "redirect:index.html";
		} else {
			return "redirect:login.html";
		}
	}

	/**
	 * 登录，只能表单POST提交。如果已经登陆另一个帐号，原帐号会先退出，并返回错误信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
	@ResponseBody
	public SysResponse login(@RequestParam String username, String password, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		String host = WebUtil.getRemoteHost(request);
		if (subject.isAuthenticated()) {
			String principal = (String) subject.getPrincipal();
			if (!username.equals(principal)) {
				subject.logout();
				try {
					subject.login(new UsernamePasswordToken(username, password, host));
				} catch (AuthenticationException e) {
					return SysResponse.fail(ResponseCode.ERROR, "登录失败，帐号或密码错误！");
				}
			}
		}
		if (subject.isAuthenticated()) {
			if (subject.getSession().getAttribute(LoginLog.SESSION_USER_LOG_ID) == null) {
				String userAgent = request.getHeader("User-Agent");
				loginLogService.logCurrentUserLogin(userAgent, host);
			}
			return SysResponse.ok(subject.getSession().getId());
		} else {
			return SysResponse.fail(ResponseCode.ERROR, "登录失败，帐号或密码错误！");
		}
	}

	/**
	 * 没有登录或者需要权限会跳转到该方法
	 * 
	 * @return
	 */
	@GetMapping("/login")
	@ResponseBody
	public SysResponse login() {
		return SysResponse.fail(ResponseCode.ERROR, "访问受限或会话超时！");
	}

	/**
	 * shiro配置无权限跳转的路径
	 * 
	 * @return
	 */
	@GetMapping("/unauthorized")
	@ResponseBody
	public SysResponse unauthorized() {
		return SysResponse.fail(ResponseCode.ERROR, "访问受限！");
	}

	/**
	 * 登出路径
	 * 
	 * @return
	 */
	@GetMapping("/logout")
	@ResponseBody
	public SysResponse logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		return SysResponse.ok();
	}
}
