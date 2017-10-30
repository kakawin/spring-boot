package com.kakawin.gis.springboot.modules.system.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

@TableName(value = "tb_sys_file_info")
public class FileInfo implements Serializable {

	private static final long serialVersionUID = -8756106587961682525L;

	/** fileId: 文件id，作为保存在文件系统下的名称 */
	@TableId(value = "file_id")
	private String fileId;
	/** fileName: 原始文件名，原上传的文件名 */
	@TableField(value = "file_name")
	private String fileName;
	/** fileDir: 文件父目录 */
	@JsonIgnore
	@TableField(value = "file_dir")
	private String fileDir;
	/** fileType: 文件类型 */
	@TableField(value = "file_type")
	private String fileType;
	/** fileContentType: 文件传输类型 */
	@TableField(value = "file_content_type")
	private String fileContentType;
	/** fileSize: 文件大小，单位为Byte */
	@TableField(value = "file_size")
	private long fileSize;
	/** MD5: MD5码 */
	@TableField(value = "md5")
	private String md5;
	/** creator: 创建人 */
	private String creator;
	/** createTime: 创建时间 */
	@TableField(value = "create_time")
	private Date createTime; // 创建时间

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@JsonIgnore
	public String getFullPath() {
		if (StringUtils.isEmpty(this.fileId)) {
			return "";
		}
		if (StringUtils.isEmpty(this.fileType)) {
			return this.fileDir + File.separator + this.fileId;
		}
		return this.fileDir + File.separator + this.fileId + "." + this.fileType;
	}

	@Override
	public String toString() {
		return "FileInfo [fileId=" + fileId + ", fileName=" + fileName + ", fileDir=" + fileDir + ", fileType="
				+ fileType + ", fileContentType=" + fileContentType + ", fileSize=" + fileSize + ", md5=" + md5
				+ ", creator=" + creator + ", createTime=" + createTime + "]";
	}

}
