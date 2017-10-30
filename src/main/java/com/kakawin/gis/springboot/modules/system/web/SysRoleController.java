package com.kakawin.gis.springboot.modules.system.web;

import java.util.List;

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

import com.kakawin.gis.springboot.modules.system.entity.SysRole;
import com.kakawin.gis.springboot.modules.system.service.SysRoleService;
import com.kakawin.gis.springboot.utils.ResponseCode;
import com.kakawin.gis.springboot.utils.SubjectUtil;
import com.kakawin.gis.springboot.utils.SysResponse;

@RestController
@RequestMapping("/role")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@RequiresAuthentication
	@GetMapping("/all")
	public SysResponse listAll() {
		List<SysRole> all = sysRoleService.listAll();
		return SysResponse.ok(all);
	}

	@RequiresAuthentication
	@GetMapping("/one/{id}")
	public SysResponse getOne(@PathVariable String id) {
		SysRole one = sysRoleService.getOne(id);
		return SysResponse.ok(one);
	}

	@RequiresPermissions("system:role:view")
	@GetMapping("/listByUserId/{userId}")
	public SysResponse listByUserId(@PathVariable String userId) {
		List<SysRole> sysRoles = sysRoleService.listByUserId(userId);
		return SysResponse.ok(sysRoles);
	}

	@RequiresAuthentication
	@GetMapping("/listByCurrentUser")
	public SysResponse listByCurrentUser() {
		List<SysRole> sysRoles = sysRoleService.listByUsername(SubjectUtil.getUsername());
		return SysResponse.ok(sysRoles);
	}

	@RequiresPermissions("system:role:edit")
	@PostMapping("/create")
	public SysResponse create(@RequestBody @Validated SysRole sysRole, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		SysRole data = sysRoleService.create(sysRole);
		return SysResponse.ok(data);
	}

	@RequiresPermissions("system:role:edit")
	@PostMapping("/update")
	public SysResponse update(@RequestBody @Validated SysRole sysRole, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		SysRole data = sysRoleService.update(sysRole);
		return SysResponse.ok(data);
	}

	@RequiresPermissions("system:role:edit")
	@PostMapping("/updateUserRoles")
	public SysResponse updateUserRoles(String userId,
			@RequestParam(value = "roleIds[]", required = false) String[] roleIds) {
		sysRoleService.updateUserRoles(userId, roleIds);
		return SysResponse.ok();
	}

	@RequiresPermissions("system:role:delete")
	@DeleteMapping("/delete/{id}")
	public SysResponse delete(@PathVariable String id) {
		sysRoleService.delete(id);
		return SysResponse.ok();
	}

}