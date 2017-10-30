package com.kakawin.gis.springboot.modules.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value = "tb_sys_login_log")
public class LoginLog {

	public final static String SESSION_USER_LOG_ID = "USER_LOG_ID";
	/** EXPIRATION: 会话超时 */
	public final static String EXPIRATION = "EXPIRATION";
	/** STOP: 主动登出 */
	public final static String STOP = "STOP";

	/** id: 主键 */
	@TableId
	private String id;

	/** host: ip地址 */
	private String host;

	/** userAgent: 客户端类型 */
	@TableField(value = "user_agent")
	private String userAgent;

	/** userInfo: 用户名 */
	private String username;

	/** loginTime: 登录时间 */
	@TableField(value = "login_time")
	private Date loginTime;

	/** loginTime: 登出时间 */
	@TableField(value = "logout_time")
	private Date logoutTime;

	/** logoutType: 登出类型 */
	@TableField(value = "logout_type")
	private String logoutType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLogoutType() {
		return logoutType;
	}

	public void setLogoutType(String logoutType) {
		this.logoutType = logoutType;
	}

	@Override
	public String toString() {
		return "LoginLog [id=" + id + ", host=" + host + ", userAgent=" + userAgent + ", username=" + username
				+ ", loginTime=" + loginTime + ", logoutTime=" + logoutTime + ", logoutType=" + logoutType + "]";
	}

}
