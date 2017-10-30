package com.kakawin.gis.springboot.modules.system.web;

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

import com.kakawin.gis.springboot.modules.system.entity.SysUser;
import com.kakawin.gis.springboot.modules.system.service.SysUserService;
import com.kakawin.gis.springboot.utils.PasswordEncoder;
import com.kakawin.gis.springboot.utils.RequestPage;
import com.kakawin.gis.springboot.utils.ResponseCode;
import com.kakawin.gis.springboot.utils.SubjectUtil;
import com.kakawin.gis.springboot.utils.SysResponse;
import com.kakawin.gis.springboot.utils.WebUtil;

@RestController
@RequestMapping("/user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 注册新用户
	 * 
	 */
	@PostMapping("/register")
	public SysResponse register(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		SysUser user = sysUserService.getByUsername(sysUser.getUsername());
		if (user != null) {
			return SysResponse.fail(ResponseCode.ERROR, "该用户名已存在");
		}
		sysUser = sysUserService.register(sysUser);
		return SysResponse.ok(sysUser);
	}

	/**
	 * 当前用户更新信息
	 * 
	 */
	@RequiresAuthentication
	@PostMapping("/update")
	public SysResponse update(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		sysUser = sysUserService.update(sysUser);
		return SysResponse.ok(sysUser);
	}

	/**
	 * 更新密码,当前用户需要输入旧密码来更改
	 * 
	 */
	@RequiresAuthentication
	@PostMapping("/updatePassword")
	public SysResponse updatePassword(@RequestParam String password, @RequestParam String newPassword) {
		SysUser user = sysUserService.getByUsername(SubjectUtil.getUsername());
		if (!user.getPassword().equals(PasswordEncoder.encode(password))) {
			return SysResponse.fail(ResponseCode.ERROR, "原密码有误！");
		}
		sysUserService.updatePassword(user.getId(), newPassword);
		return SysResponse.ok();
	}

	/**
	 * 管理员创建新用户
	 * 
	 */
	@RequiresPermissions("system:user:edit")
	@PostMapping("/adminCreate")
	public SysResponse adminCreate(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		SysUser user = sysUserService.getByUsername(sysUser.getUsername());
		if (user != null) {
			return SysResponse.fail(ResponseCode.ERROR, "该用户名已存在");
		}
		sysUser = sysUserService.adminCreate(sysUser);
		return SysResponse.ok(sysUser);
	}


	/**
	 * 管理员更新用户
	 * 
	 */
	@RequiresPermissions("system:user:edit")
	@PostMapping("/adminUpdate")
	public SysResponse adminUpdate(@RequestBody @Validated SysUser sysUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return SysResponse.fail(ResponseCode.ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		sysUser = sysUserService.adminUpdate(sysUser);
		return SysResponse.ok(sysUser);
	}

	/**
	 * 管理员更新密码
	 * 
	 */
	@RequiresPermissions("system:user:edit")
	@PostMapping("/adminUpdatePassword")
	public SysResponse adminUpdatePassword(@RequestParam String id, @RequestParam String newPassword) {
		sysUserService.updatePassword(id, newPassword);
		return SysResponse.ok();
	}

	/**
	 * 重置密码
	 */
	@RequiresPermissions("system:user:edit")
	@PostMapping("/resetPassword")
	public SysResponse resetPassword(@RequestParam String[] id) {
		sysUserService.resetPassword(id);
		return SysResponse.ok();
	}

	/**
	 * 更新账号状态
	 * 
	 */
	@RequiresPermissions("system:user:edit")
	@PostMapping("/updateState")
	public SysResponse updateState(@RequestParam String[] id, @RequestParam String state) {
		sysUserService.updateState(id, state);
		return SysResponse.ok();
	}

	@RequiresPermissions("system:user:delete")
	@DeleteMapping("/delete/{id}")
	public SysResponse delete(@PathVariable String id) {
		sysUserService.delete(id);
		return SysResponse.ok();
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
		return SysResponse.ok(sysUserService.getPage(requestPage, WebUtil.getParamMap(request)));
	}

	@RequiresAuthentication
	@GetMapping("/currentUser")
	public SysResponse getCurrentUser() {
		return SysResponse.ok(sysUserService.getByUsername(SubjectUtil.getUsername()));
	}

	@RequiresPermissions("system:user:view")
	@GetMapping("/one/{id}")
	public SysResponse getOne(@PathVariable String id) {
		return SysResponse.ok(sysUserService.getOne(id));
	}


	@RequiresPermissions("system:user:view")
	@GetMapping("/oneByUsername/{username}")
	public SysResponse getByUsername(@PathVariable String username) {
		return SysResponse.ok(sysUserService.getByUsername(username));
	}

	@RequiresPermissions("system:user:view")
	@GetMapping("/listByOrganId/{organId}")
	public SysResponse listByOrganId(@PathVariable String organId) {
		return SysResponse.ok(sysUserService.listByOrganId(organId));
	}

}
