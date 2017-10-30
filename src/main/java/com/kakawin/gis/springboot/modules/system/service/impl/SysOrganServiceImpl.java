package com.kakawin.gis.springboot.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.kakawin.gis.springboot.modules.system.entity.SysOrgan;
import com.kakawin.gis.springboot.modules.system.mapper.SysOrganMapper;
import com.kakawin.gis.springboot.modules.system.service.SysOrganService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

@Service
public class SysOrganServiceImpl implements SysOrganService {

	@Autowired
	private SysOrganMapper sysOrganMapper;

	@Override
	public SysOrgan create(SysOrgan sysOrgan) {
		sysOrganMapper.insert(sysOrgan);
		return sysOrgan;
	}

	@Override
	public SysOrgan update(SysOrgan sysOrgan) {
		sysOrganMapper.updateById(sysOrgan);
		return sysOrgan;
	}

	@Override
	public void delete(String id) {
		sysOrganMapper.deleteById(id);
	}

	@Override
	public List<SysOrgan> listAll() {
		return sysOrganMapper.selectList(null);
	}

	@Override
	public SysOrgan getByUsername(String username) {
		return sysOrganMapper.getByUsername(username);
	}

	@Override
	public List<SysOrgan> listByParentId(String parentId) {
		return sysOrganMapper.listByParentId(parentId);
	}

	@Override
	public SysOrgan getOne(String id) {
		return sysOrganMapper.selectById(id);
	}

	@Override
	public String getChildIdsString(String id) {
		return sysOrganMapper.getChildIdsString(id);
	}

	@Override
	public String getParentIdsString(String id) {
		return sysOrganMapper.getParentIdsString(id);
	}

	@Override
	public ResponsePage<SysOrgan> getPage(RequestPage requestPage) {
		PageHelper.startPage(requestPage.getPage(), requestPage.getSize());
		List<SysOrgan> list = sysOrganMapper.selectList(null);
		return new ResponsePage<SysOrgan>(list);
	}

}
