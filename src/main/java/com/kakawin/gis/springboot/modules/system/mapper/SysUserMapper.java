package com.kakawin.gis.springboot.modules.system.mapper;

import java.util.List;
import java.util.Map;

import com.kakawin.gis.springboot.modules.system.entity.SysUser;
import com.kakawin.gis.springboot.utils.MyMapper;

public interface SysUserMapper extends MyMapper<SysUser> {

	SysUser getByUsername(String username);

	List<SysUser> listByParamMap(Map<String, Object> paramMap);

}
