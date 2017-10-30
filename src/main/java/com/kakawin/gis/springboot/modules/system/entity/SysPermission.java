package com.kakawin.gis.springboot.modules.system.entity;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value = "tb_sys_permission")
public class SysPermission implements Serializable {

	private static final long serialVersionUID = -7071069284582582311L;

	public static final String STATE_ENABLE = "1";
	public static final String STATE_DISABLE = "0";

	/** id: 主键 */
	@TableId
	private String id;

	/** name: 权限名称 */
	@Size(min = 1, message = "权限名称不能为空")
	private String name;

	/** url: 权限路径 */
	private String url;

	/** description: 权限说明 */
	private String description;

	/**
	 * permission:
	 * 权限字符串,父权限例子：role:*，子权限例子：role:create,role:update,role:delete,role:view
	 */
	@Size(min = 1, message = "权限字符串不能为空")
	private String permission;

	/** state: 权限状态 */
	private String state;

	/** sort: 排序 */
	private String sort;

	@TableField(value = "parent_id")
	private String parentId;

	/** type: 权限类型。功能权限【function】或数据权限【data】 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", name=" + name + ", url=" + url + ", description=" + description
				+ ", permission=" + permission + ", state=" + state + ", sort=" + sort + ", parentId=" + parentId
				+ ", type=" + type + "]";
	}

}
