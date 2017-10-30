package com.kakawin.gis.springboot.modules.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@TableName(value = "tb_sys_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = -4330623801498437761L;

	public static final String STATE_UNACTIVATED = "0";
	public static final String STATE_ENABLE = "1";
	public static final String STATE_LOCKED = "2";

	/** id: 用户id */
	@TableId
	private String id;

	/** username: 用户名称 */
	@Size(min = 3, max = 20, message = "账号长度只能在3-20之间")
	private String username;

	/** password: 密码 */
	@Size(min = 4, message = "密码长度不能小于4位")
	private String password;

	/** salt: 加密密码的盐 */
	@JsonIgnore
	private String salt;

	/** state: 用户状态,null或0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 ,1:正常状态,2：用户被锁定 */
	private String state;

	/** name: 用户名字 */
	@Size(min = 1, message = "名称不能为空")
	private String name;

	/** staff_id 员工工号 */
	@TableField(value = "staff_id")
	private String staffId;

	/** gender: 性别 */
	private String gender;

	/** email: 电子邮箱地址 */
	private String email;

	/** telPhone: 电话号码 */
	@TableField(value = "phone")
	private String phone;

	/** address: 住址 */
	private String address;

	/** organ: 所属机构 */
	@TableField(value = "organ_id")
	private String organId;

	/** createTime: 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;

	/** updateTime: 更新时间 */
	@TableField(value = "update_time")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonBackReference
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", state=" + state + ", name=" + name + ", staffId=" + staffId + ", gender=" + gender + ", email="
				+ email + ", phone=" + phone + ", address=" + address + ", organId=" + organId + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

}
