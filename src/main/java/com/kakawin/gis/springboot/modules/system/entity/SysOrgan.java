package com.kakawin.gis.springboot.modules.system.entity;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value = "tb_sys_organ")
public class SysOrgan implements Serializable {

	private static final long serialVersionUID = -6494739117088300789L;

	public static final String STATE_ENABLE = "1";
	public static final String STATE_DISABLE = "0";

	/** id: 机构编码作为主键 */
	@TableId
	@Size(min = 1, message = "机构编码不能为空")
	private String id;

	/** level: 原机构编码，现在改为机构等级，如：惠州分公司等级为3 */
	private String level;

	/** name: 机构名称 */
	@Size(min = 1, message = "机构名称不能为空")
	private String name;

	/** description: 机构描述 */
	private String description;

	/** state: 机构状态 */
	private String state;

	/** parentId: 上级机构 */
	@TableField(value = "parent_id")
	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "SysOrgan [id=" + id + ", level=" + level + ", name=" + name + ", description=" + description
				+ ", state=" + state + ", parentId=" + parentId + "]";
	}

}