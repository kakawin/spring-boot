package com.kakawin.gis.springboot.modules.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kakawin.gis.springboot.modules.system.entity.SysPermission;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

public interface SysPermissionService {

	SysPermission create(SysPermission sysPermission);

	void delete(String id);

	Set<String> findPermissionAnnotationValues(String pattern);

	SysPermission getOne(String id);

	Set<String> getPermissionAnnotationValues();

	Set<String> getUnregisteredPermissionValues();

	List<SysPermission> listAll();

	List<String> listAllPermissionStrings();

	List<String> listAvailablePermissionStrings(String username);

	List<SysPermission> listBySysRoleId(String sysRoleId);

	ResponsePage<SysPermission> getPage(RequestPage requestPage, Map<String, Object> paramMap);

	List<SysPermission> listByUsername(String username);

	void loadPermissionAnnotationValues();

	SysPermission update(SysPermission sysPermission);

	void updateRolePermissions(String roleId, String[] permissionIds);

}
