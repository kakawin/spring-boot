package com.kakawin.gis.springboot.config.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.kakawin.gis.springboot.modules.system.entity.SysUser;
import com.kakawin.gis.springboot.modules.system.service.SysPermissionService;
import com.kakawin.gis.springboot.modules.system.service.SysRoleService;
import com.kakawin.gis.springboot.modules.system.service.SysUserService;

public class SystemAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 权限认证，数据库获取角色和权限的字符串，与@RequiresPermissions所配置的字符串验证是否有访问权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) super.getAvailablePrincipal(principalCollection);
		SysUser sysUser = sysUserService.getByUsername(username);
		if (sysUser == null) {
			throw new UnknownAccountException("未找到该帐号");
		}
		if (SysUser.STATE_LOCKED.equals(sysUser.getState())) {
			throw new LockedAccountException("该账号已锁定");
		}
		if (sysUser.getState() == null || SysUser.STATE_UNACTIVATED.equals(sysUser.getState())) {
			// throw new DisabledAccountException("该账号未激活");
		}
		// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
		List<String> roleStrings = sysRoleService.listAvailableRoleStrings(username);
		List<String> permissionStrings = sysPermissionService.listAvailablePermissionStrings(username);
		SimpleAuthorizationInfo infoauthorizationInfo = new SimpleAuthorizationInfo();
		infoauthorizationInfo.addRoles(roleStrings);
		infoauthorizationInfo.addStringPermissions(permissionStrings);
		return infoauthorizationInfo;
	}

	/**
	 * 登录认证，认证用户的账户密码
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		SysUser sysUser = sysUserService.getByUsername(username);
		if (sysUser == null) {
			throw new UnknownAccountException("未找到该帐号");
		}
		if (SysUser.STATE_LOCKED.equals(sysUser.getState())) {
			throw new LockedAccountException("该账号已锁定");
		}
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), null, getName());
	}

}
