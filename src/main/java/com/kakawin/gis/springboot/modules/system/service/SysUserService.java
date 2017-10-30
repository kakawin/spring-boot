package com.kakawin.gis.springboot.modules.system.service;

import java.util.List;
import java.util.Map;

import com.kakawin.gis.springboot.modules.system.entity.SysUser;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

public interface SysUserService {

	SysUser adminCreate(SysUser sysUser);

	SysUser adminUpdate(SysUser sysUser);

	void updatePassword(String id, String newPassword);

	void resetPassword(String[] ids);

	void updateState(String[] ids, String state);

	SysUser getOne(String id);

	SysUser getByUsername(String username);

	List<SysUser> listByOrganId(String organId);

	void delete(String id);

	ResponsePage<SysUser> getPage(RequestPage requestPage, Map<String, Object> paramMap);

	SysUser register(SysUser newSysUser);

	SysUser update(SysUser newSysUser);

}
