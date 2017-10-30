package com.kakawin.gis.springboot.modules.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("tb_sys_access_log")
public class AccessLog {
	/** ID */
	@TableId
	private String id;
	/** 访问描述 */
	private String accessDescription;
	/**
	 * 访问类型
	 * 
	 * @see com.kakawin.gis.springboot.modules.system.enumeration.AccessType
	 */
	private Integer accessType;
	/** 访问类 */
	private String accessClass;
	/** 访问方法 */
	private String accessMethod;
	/** 访问参数 */
	private String accessParams;
	/** 访问时间 */
	private Date accessTime;
	/** 访问用户名称 */
	private String accessUsername;
	/** 是否有异常 */
	private Boolean exceptionFlag;
	/** 异常内容 */
	private String exceptionInfo;

	public AccessLog() {

	}

	public AccessLog(String accessDescription, Integer accessType, String accessClass, String accessMethod,
			String accessParams, Date accessTime, String accessUsername, Boolean exceptionFlag, String exceptionInfo) {
		this.accessDescription = accessDescription;
		this.accessType = accessType;
		this.accessClass = accessClass;
		this.accessMethod = accessMethod;
		this.accessParams = accessParams;
		this.accessTime = accessTime;
		this.accessUsername = accessUsername;
		this.exceptionFlag = exceptionFlag;
		this.exceptionInfo = exceptionInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccessDescription() {
		return accessDescription;
	}

	public void setAccessDescription(String accessDescription) {
		this.accessDescription = accessDescription;
	}

	public Integer getAccessType() {
		return accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	public String getAccessClass() {
		return accessClass;
	}

	public void setAccessClass(String accessClass) {
		this.accessClass = accessClass;
	}

	public String getAccessMethod() {
		return accessMethod;
	}

	public void setAccessMethod(String accessMethod) {
		this.accessMethod = accessMethod;
	}

	public String getAccessParams() {
		return accessParams;
	}

	public void setAccessParams(String accessParams) {
		this.accessParams = accessParams;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessUsername() {
		return accessUsername;
	}

	public void setAccessUsername(String accessUsername) {
		this.accessUsername = accessUsername;
	}

	public Boolean getExceptionFlag() {
		return exceptionFlag;
	}

	public void setExceptionFlag(Boolean exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

}
