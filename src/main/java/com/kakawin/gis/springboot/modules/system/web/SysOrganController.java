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
import org.springframework.web.bind.annotation.RestController;

import com.kakawin.gis.springboot.modules.system.entity.SysOrgan;
import com.kakawin.gis.springboot.modules.system.service.SysOrganService;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponseCode;
import com.kakawin.gis.springboot.utils.SubjectUtil;
import com.kakawin.gis.springboot.utils.SysResponse;

@RestController
@RequestMapping("/organ")
public class SysOrganController {

	@Autowired
	private SysOrganService sysOrganService;

	@RequiresPermissions("system:organ:view")
	@GetMapping("/page")
	public SysResponse getPage(RequestPage requestPage) {
		return SysResponse.ok(sysOrganService.getPage(requestPage));
	}

	@RequiresAuthentication
	@GetMapping("/all")
	public SysResponse listAll() {
		List<SysOrgan> all = sysOrganService.listAll();
		return SysResponse.ok(all);
	}

	@RequiresAuthentication
	@GetMapping("/one/{id}")
	public SysResponse getOne(@PathVariable String id) {
		SysOrgan sysOrgan = sysOrganService.getOne(id);
		return SysResponse.ok(sysOrgan);
	}

	@RequiresPermissions("system:organ:view")
	@GetMapping("/oneByUsername/{username}")
	public SysResponse getByUsername(@PathVariable String username) {
		SysOrgan sysOrgan = sysOrganService.getByUsername(username);
		return SysResponse.ok(sysOrgan);
	}

	@RequiresAuthentication
	@GetMapping("/listByParentId/{parentId}")
	public SysResponse listByParentId(@PathVariable String parentId) {
		List<SysOrgan> sysOrgans = sysOrganService.listByParentId(parentId);
		return SysResponse.ok(sysOrgans);
	}

	@RequiresAuthentication
	@GetMapping("/oneByCurrentUser")
	public SysResponse getByCurrentUser() {
		SysOrgan sysOrgan = sysOrganService.getByUsername(SubjectUtil
				.getUsername());
		return SysResponse.ok(sysOrgan);
	}

	@RequiresPermissions("system:organ:edit")
	@PostMapping("/create")
	public SysResponse create(@RequestBody @Validated SysOrgan sysOrgan,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult
					.getAllErrors().get(0).getDefaultMessage());
		}
		SysOrgan one = sysOrganService.getOne(sysOrgan.getId());
		if (one != null) {
			SysResponse.fail(ResponseCode.ERROR, "该机构编码已存在！");
		}
		SysOrgan data = sysOrganService.create(sysOrgan);
		return SysResponse.ok(data);
	}

	@RequiresPermissions("system:organ:edit")
	@PostMapping("/update")
	public SysResponse update(@RequestBody @Validated SysOrgan sysOrgan,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult
					.getAllErrors().get(0).getDefaultMessage());
		}
		SysOrgan data = sysOrganService.update(sysOrgan);
		return SysResponse.ok(data);
	}

	@RequiresPermissions("system:organ:delete")
	@DeleteMapping("/delete/{id}")
	public SysResponse delete(@PathVariable String id) {
		sysOrganService.delete(id);
		return SysResponse.ok();
	}

}
