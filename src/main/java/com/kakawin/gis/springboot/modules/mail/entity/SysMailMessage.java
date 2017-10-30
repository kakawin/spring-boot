package com.kakawin.gis.springboot.modules.mail.entity;

import java.util.ArrayList;
import java.util.List;

import com.kakawin.gis.springboot.modules.system.entity.FileInfo;

public class SysMailMessage {

	/**
	 * @Fields target : 邮件目标人
	 */
	private String target;

	/**
	 * @Fields carbonCopys : 邮件抄送人列表
	 */
	private List<String> carbonCopys = new ArrayList<String>();

	/**
	 * @Fields subject : 邮件主题
	 */
	private String subject;

	/**
	 * @Fields text : 邮件内容
	 */
	private String text;

	/**
	 * @Fields files : 邮件附件
	 */
	private List<FileInfo> fileInfos = new ArrayList<FileInfo>();

	public SysMailMessage(String target, String subject, String text) {
		super();
		this.target = target;
		this.subject = subject;
		this.text = text;
	}

	public SysMailMessage(String target, String subject, String text, List<FileInfo> fileInfos) {
		super();
		this.target = target;
		this.subject = subject;
		this.text = text;
		this.fileInfos = fileInfos;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<String> getCarbonCopys() {
		return carbonCopys;
	}

	public void setCarbonCopys(List<String> carbonCopys) {
		this.carbonCopys = carbonCopys;
	}

	public void addCarbonCopy(String carbonCopy) {
		this.carbonCopys.add(carbonCopy);
	}

	public void addCarbonCopys(List<String> carbonCopys) {
		this.carbonCopys.addAll(carbonCopys);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}

	public void addFileInfo(FileInfo fileInfo) {
		this.fileInfos.add(fileInfo);
	}

	public void addFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos.addAll(fileInfos);
	}

}
