package com.kakawin.gis.springboot.modules.system.mapper;

import java.util.List;

import com.kakawin.gis.springboot.modules.system.entity.SysOrgan;
import com.kakawin.gis.springboot.utils.MyMapper;


public interface SysOrganMapper extends MyMapper<SysOrgan>{

	SysOrgan getByUsername(String username);

	List<SysOrgan> listByParentId(String parentId);

	String getChildIdsString(String organId);

	String getParentIdsString(String organId);
}
