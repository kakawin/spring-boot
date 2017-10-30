package com.kakawin.gis.springboot.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakawin.gis.springboot.modules.system.entity.SysRole;
import com.kakawin.gis.springboot.modules.system.mapper.SysPermissionMapper;
import com.kakawin.gis.springboot.modules.system.mapper.SysRoleMapper;
import com.kakawin.gis.springboot.modules.system.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Override
	public SysRole create(SysRole sysRole) {
		sysRoleMapper.insert(sysRole);
		return sysRole;
	}

	@Override
	public void delete(String id) {
		sysRoleMapper.deleteById(id);
		// 删除用户角色关系
		sysRoleMapper.deleteUserRoles(id);
		// 删除权限角色关系
		sysPermissionMapper.deleteRolePermissions(id);
	}

	@Override
	public SysRole getOne(String id) {
		return sysRoleMapper.selectById(id);
	}

	@Override
	public List<SysRole> listAll() {
		return sysRoleMapper.selectList(null);
	}

	@Override
	public List<String> listAvailableRoleStrings(String username) {
		return sysRoleMapper.listAvailableRoleStrings(username);
	}

	@Override
	public List<SysRole> listByUserId(String userId) {
		return sysRoleMapper.listByUserId(userId);
	}

	@Override
	public List<SysRole> listByUsername(String username) {
		return sysRoleMapper.listByUsername(username);
	}

	@Override
	public SysRole update(SysRole sysRole) {
		sysRoleMapper.updateById(sysRole);
		return sysRole;
	}

	@Override
	public void updateUserRoles(String userId, String[] roleIds) {
		sysRoleMapper.deleteUserRoles(userId);
		if (roleIds != null && roleIds.length > 0) {
			sysRoleMapper.insertUserRoles(userId, roleIds);
		}
	}

}
