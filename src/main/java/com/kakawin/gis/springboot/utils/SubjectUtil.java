package com.kakawin.gis.springboot.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.kakawin.gis.springboot.modules.system.entity.SysRole;

public class SubjectUtil {

	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public static boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject.isPermitted(permission);
	}

	public static boolean isCurrentUser(String username) {
		return getUsername().equals(username);
	}

	public static String getUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return (String) subject.getPrincipal();
		} else {
			return "";
		}
	}

	public static boolean isAdmin() {
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole(SysRole.ADMIN);
	}

}
