package com.kakawin.gis.springboot.modules.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.kakawin.gis.springboot.config.Constant;
import com.kakawin.gis.springboot.modules.system.entity.SysUser;
import com.kakawin.gis.springboot.modules.system.mapper.SysRoleMapper;
import com.kakawin.gis.springboot.modules.system.mapper.SysUserMapper;
import com.kakawin.gis.springboot.modules.system.service.SysUserService;
import com.kakawin.gis.springboot.utils.PasswordEncoder;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public SysUser adminCreate(SysUser sysUser) {
		String salt = UUID.randomUUID().toString(); // 使用UUID生成盐
		String password = PasswordEncoder.encode(Constant.defaultPassword);
		sysUser.setSalt(salt);
		sysUser.setPassword(password);
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateTime(new Date());
		sysUserMapper.insert(sysUser);
		return sysUser;
	}

	@Override
	public SysUser adminUpdate(SysUser sysUser) {
		sysUser.setUpdateTime(new Date());
		sysUserMapper.updateById(sysUser);
		return sysUser;
	}

	@Override
	public SysUser update(SysUser newSysUser) {
		SysUser sysUser = sysUserMapper.selectById(newSysUser.getId());

		// 普通用户可更改的信息
		sysUser.setAddress(newSysUser.getAddress());
		sysUser.setPhone(newSysUser.getPhone());
		sysUser.setEmail(newSysUser.getEmail());
		sysUser.setGender(newSysUser.getGender());
		sysUser.setName(newSysUser.getName());
		sysUser.setUpdateTime(new Date());

		sysUserMapper.updateById(sysUser);
		return sysUser;
	}

	@Override
	public SysUser register(SysUser newSysUser) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(newSysUser.getUsername());
		String salt = UUID.randomUUID().toString(); // 使用UUID生成盐
		String password = PasswordEncoder.encode(newSysUser.getPassword()); // 加密密码
		sysUser.setSalt(salt);
		sysUser.setPassword(password);
		sysUser.setState(SysUser.STATE_UNACTIVATED); // 默认未激活
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateTime(new Date());
		sysUserMapper.insert(sysUser);
		return sysUser;
	}

	@Override
	public void updatePassword(String id, String newPassword) {
		SysUser sysUser = sysUserMapper.selectById(id);
		String salt = UUID.randomUUID().toString();
		newPassword = PasswordEncoder.encode(newPassword);
		sysUser.setSalt(salt);
		sysUser.setPassword(newPassword);
		sysUser.setUpdateTime(new Date());
		sysUserMapper.updateById(sysUser);
		SecurityUtils.getSubject().logout();
	}

	@Override
	public void resetPassword(String[] ids) {
		for (String id : ids) {
			updatePassword(id, Constant.defaultPassword);
		}
	}

	@Override
	public void updateState(String[] ids, String state) {
		for (String id : ids) {
			SysUser sysUser = sysUserMapper.selectById(id);
			sysUser.setState(state);
			sysUser.setUpdateTime(new Date());
			sysUserMapper.updateById(sysUser);
		}
	}

	@Override
	public SysUser getOne(String id) {
		return sysUserMapper.selectById(id);
	}

	@Override
	public SysUser getByUsername(String username) {
		return sysUserMapper.getByUsername(username);
	}

	@Override
	public List<SysUser> listByOrganId(String organId) {
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("organId", organId);
		return sysUserMapper.selectByMap(columnMap);
	}

	@Override
	@Transactional
	public void delete(String id) {
		sysUserMapper.deleteById(id);
		sysRoleMapper.deleteUserRoles(id);
	}

	@Override
	public ResponsePage<SysUser> getPage(RequestPage requestPage, Map<String, Object> paramMap) {
		PageHelper.startPage(requestPage.getPage(), requestPage.getSize());
		List<SysUser> list = sysUserMapper.listByParamMap(paramMap);
		return new ResponsePage<SysUser>(list);
	}

}
