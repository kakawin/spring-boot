package com.kakawin.gis.springboot.modules.system.service;

import java.util.List;

import com.kakawin.gis.springboot.modules.system.entity.SysOrgan;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

public interface SysOrganService {

	SysOrgan create(SysOrgan sysOrgan);

	void delete(String id);

	SysOrgan getByUsername(String username);

	String getChildIdsString(String id);

	SysOrgan getOne(String id);

	ResponsePage<SysOrgan> getPage(RequestPage requestPage);

	String getParentIdsString(String id);

	List<SysOrgan> listAll();

	List<SysOrgan> listByParentId(String parentId);

	SysOrgan update(SysOrgan sysOrgan);

}
