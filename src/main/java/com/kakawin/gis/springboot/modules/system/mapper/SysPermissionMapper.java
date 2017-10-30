package com.kakawin.gis.springboot.modules.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kakawin.gis.springboot.modules.system.entity.SysPermission;
import com.kakawin.gis.springboot.utils.MyMapper;

public interface SysPermissionMapper extends MyMapper<SysPermission> {

	List<String> listAvailablePermissionStrings(String username);

	List<SysPermission> listByUsername(String username);

	List<SysPermission> listBySysRoleId(String sysRoleId);

	List<SysPermission> listChildrenPermission(Map<String, Object> paramMap);

	void deleteRolePermissions(String roleId);

	void insertRolePermissions(@Param("roleId") String roleId,
			@Param("permissionIds") String[] permissionIds);

	List<String> listAllPermissionStrings();

}
