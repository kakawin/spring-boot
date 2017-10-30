package com.kakawin.gis.springboot.modules.system.entity;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value = "tb_sys_role")
public class SysRole implements Serializable {

	private static final long serialVersionUID = 6041906160174478187L;

	public static final String ADMIN = "admin";

	public static final String STATE_ENABLE = "1";
	public static final String STATE_DISABLE = "0";

	/** id: 主键 */
	@TableId
	private String id;

	/** name: 角色名称 */
	@Size(min = 1, message = "角色名称不能为空")
	private String name;

	/** description: 角色说明 */
	private String description;

	/** role: 角色标识程序中判断使用,如："admin",这个是唯一的 */
	@Size(min = 1, message = "角色字符串不能为空")
	private String role;

	/** state: 该角色的权限状态 */
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", description=" + description + ", role=" + role + ", state="
				+ state + "]";
	}

}
