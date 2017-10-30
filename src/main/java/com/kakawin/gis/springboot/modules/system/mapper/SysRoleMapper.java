package com.kakawin.gis.springboot.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kakawin.gis.springboot.modules.system.entity.SysRole;
import com.kakawin.gis.springboot.utils.MyMapper;

public interface SysRoleMapper extends MyMapper<SysRole> {

	List<String> listAvailableRoleStrings(String username);

	List<SysRole> listByUsername(String username);

	void deleteUserRoles(String userId);

	void insertUserRoles(@Param("userId") String userId, @Param("roleIds") String[] roleIds);

	List<SysRole> listByUserId(String userId);

}
