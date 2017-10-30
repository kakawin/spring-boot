package com.kakawin.gis.springboot.modules.system.service.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.kakawin.gis.springboot.modules.system.entity.SysPermission;
import com.kakawin.gis.springboot.modules.system.mapper.SysPermissionMapper;
import com.kakawin.gis.springboot.modules.system.service.SysPermissionService;
import com.kakawin.gis.springboot.utils.ReflectionUtil;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponsePage;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	private Set<String> permissionAnnotationValues;

	@PostConstruct
	@Override
	public void loadPermissionAnnotationValues() {
		this.permissionAnnotationValues = findPermissionAnnotationValues(
				"classpath*:/com/kakawin/gis/springboot/modules/**/*Controller.class");
	}

	@Override
	public SysPermission create(SysPermission sysPermission) {
		sysPermissionMapper.insert(sysPermission);
		return sysPermission;
	}

	@Override
	public SysPermission update(SysPermission sysPermission) {
		sysPermissionMapper.updateById(sysPermission);
		return sysPermission;
	}

	@Override
	public void delete(String id) {
		sysPermissionMapper.deleteById(id);
		sysPermissionMapper.deleteRolePermissions(id);
	}

	@Override
	public List<SysPermission> listAll() {
		return sysPermissionMapper.selectList(null);
	}

	@Override
	public List<String> listAllPermissionStrings() {
		return sysPermissionMapper.listAllPermissionStrings();
	}

	@Override
	public List<String> listAvailablePermissionStrings(String username) {
		return sysPermissionMapper.listAvailablePermissionStrings(username);
	}

	@Override
	public List<SysPermission> listByUsername(String username) {
		return sysPermissionMapper.listByUsername(username);
	}

	@Override
	public List<SysPermission> listBySysRoleId(String sysRoleId) {
		return sysPermissionMapper.listBySysRoleId(sysRoleId);
	}

	@Override
	public SysPermission getOne(String id) {
		return sysPermissionMapper.selectById(id);
	}

	@Override
	public void updateRolePermissions(String roleId, String[] permissionIds) {
		sysPermissionMapper.deleteRolePermissions(roleId);
		if (permissionIds != null && permissionIds.length > 0) {
			sysPermissionMapper.insertRolePermissions(roleId, permissionIds);
		}
	}

	@Override
	public Set<String> findPermissionAnnotationValues(String pattern) {
		Set<String> annotationValues = new HashSet<String>();
		List<Class<?>> classes = ReflectionUtil.findClasses(pattern);
		for (Class<?> clazz : classes) {
			RequiresPermissions classAnnotation = AnnotationUtils
					.findAnnotation(clazz, RequiresPermissions.class);
			if (classAnnotation != null) {
				annotationValues.addAll(Arrays.asList(classAnnotation.value()));
			}
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				RequiresPermissions methodAnnotation = AnnotationUtils
						.findAnnotation(method, RequiresPermissions.class);
				if (methodAnnotation != null) {
					annotationValues.addAll(Arrays.asList(methodAnnotation
							.value()));
				}
			}
		}
		return annotationValues;
	}

	@Override
	public Set<String> getPermissionAnnotationValues() {
		return permissionAnnotationValues;
	}

	@Override
	public Set<String> getUnregisteredPermissionValues() {
		if (permissionAnnotationValues == null) {
			return new HashSet<String>();
		}
		Set<String> unregisteredPermissionValues = new HashSet<String>();
		List<String> allSysPermissionStrings = listAllPermissionStrings();
		for (String value : permissionAnnotationValues) {
			if (!allSysPermissionStrings.contains(value)) {
				unregisteredPermissionValues.add(value);
			}
		}
		return unregisteredPermissionValues;
	}

	@Override
	public ResponsePage<SysPermission> getPage(RequestPage requestPage,
			Map<String, Object> paramMap) {
		PageHelper.startPage(requestPage.getPage(), requestPage.getSize());
		List<SysPermission> list = sysPermissionMapper
				.listChildrenPermission(paramMap);
		return new ResponsePage<SysPermission>(list);
	}

}
