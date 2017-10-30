package com.kakawin.gis.springboot.modules.system.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakawin.gis.springboot.modules.system.entity.SysPermission;
import com.kakawin.gis.springboot.modules.system.service.SysPermissionService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponseCode;
import com.kakawin.gis.springboot.utils.SubjectUtil;
import com.kakawin.gis.springboot.utils.SysResponse;
import com.kakawin.gis.springboot.utils.WebUtil;

@RestController
@RequestMapping("/permission")
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;

	@RequiresAuthentication
	@GetMapping("/all")
	public SysResponse listAll() {
		List<SysPermission> list = sysPermissionService.listAll();
		return SysResponse.ok(list);
	}

	@RequiresAuthentication
	@GetMapping("/allStrings")
	public SysResponse listAllStrings() {
		List<String> list = sysPermissionService.listAllPermissionStrings();
		return SysResponse.ok(list);
	}

	@RequiresAuthentication
	@GetMapping("/unregStrings")
	public SysResponse listUnregisteredStrings() {
		Set<String> list = sysPermissionService.getUnregisteredPermissionValues();
		return SysResponse.ok(list);
	}

	@RequiresAuthentication
	@GetMapping("/one/{id}")
	public SysResponse getOne(@PathVariable String id) {
		SysPermission one = sysPermissionService.getOne(id);
		return SysResponse.ok(one);
	}

	/**
	 * 分页查询
	 * 
	 * @param sysPage
	 * @return
	 */
	@RequiresPermissions("system:user:view")
	@GetMapping("/page")
	public SysResponse getPage(RequestPage requestPage, HttpServletRequest request) {
		return SysResponse.ok(sysPermissionService.getPage(requestPage, WebUtil.getParamMap(request)));
	}

	@RequiresPermissions("system:permission:view")
	@GetMapping("/listByUsername/{username}")
	public SysResponse listByUsername(@PathVariable String username) {
		List<SysPermission> sysPermissions = sysPermissionService.listByUsername(username);
		return SysResponse.ok(sysPermissions);
	}

	@RequiresAuthentication
	@GetMapping("/listBySysRoleId/{sysRoleId}")
	public SysResponse listBySysRoleId(@PathVariable String sysRoleId) {
		List<SysPermission> sysPermissions = sysPermissionService.listBySysRoleId(sysRoleId);
		return SysResponse.ok(sysPermissions);
	}

	@RequiresAuthentication
	@GetMapping("/listByCurrentUser")
	public SysResponse listByCurrentUser() {
		List<SysPermission> sysPermissions = sysPermissionService.listByUsername(SubjectUtil.getUsername());
		return SysResponse.ok(sysPermissions);
	}

	@RequiresAuthentication
	@GetMapping("/listStringByCurrentUser")
	public SysResponse listStringByCurrentUser() {
		List<String> sysPermissions = sysPermissionService.listAvailablePermissionStrings(SubjectUtil.getUsername());
		return SysResponse.ok(sysPermissions);
	}

	@RequiresPermissions("system:permission:edit")
	@PostMapping("/create")
	public SysResponse create(@RequestBody @Validated SysPermission sysPermission, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		SysPermission data = sysPermissionService.create(sysPermission);
		return SysResponse.ok(data);
	}

	@RequiresPermissions("system:permission:edit")
	@PostMapping("/update")
	public SysResponse update(@RequestBody @Validated SysPermission sysPermission, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		SysPermission data = sysPermissionService.update(sysPermission);
		return SysResponse.ok(data);
	}

	@RequiresPermissions("system:permission:edit")
	@PostMapping("/updateRolePermissions")
	public SysResponse updateRolePermissions(String roleId,
			@RequestParam(value = "permissionIds[]", required = false) String[] permissionIds) {
		sysPermissionService.updateRolePermissions(roleId, permissionIds);
		return SysResponse.ok();
	}

	@RequiresPermissions("system:permission:delete")
	@DeleteMapping("/delete/{id}")
	public SysResponse delete(@PathVariable String id) {
		sysPermissionService.delete(id);
		return SysResponse.ok();
	}
}
