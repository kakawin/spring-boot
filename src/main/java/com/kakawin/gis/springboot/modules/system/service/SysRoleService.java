package com.kakawin.gis.springboot.modules.system.service;

import java.util.List;

import com.kakawin.gis.springboot.modules.system.entity.SysRole;

public interface SysRoleService {

	SysRole create(SysRole sysRole);

	void delete(String id);

	SysRole getOne(String id);

	List<SysRole> listAll();

	List<String> listAvailableRoleStrings(String username);

	List<SysRole> listByUserId(String userId);

	List<SysRole> listByUsername(String username);

	SysRole update(SysRole sysRole);

	void updateUserRoles(String userId, String[] roleIds);

}
