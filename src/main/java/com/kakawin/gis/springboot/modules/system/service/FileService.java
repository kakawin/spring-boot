package com.kakawin.gis.springboot.modules.system.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kakawin.gis.springboot.modules.system.entity.FileInfo;

public interface FileService {
	public int delete(String id);

	/**
	 * 根据“文件名称.文件类型”形式查询到文件信息
	 * 
	 * @param filename
	 * @return
	 */
	public FileInfo findFileInfo(String filename);

	/**
	 * 根据文件ID获取文件信息
	 * 
	 * @param fileId
	 * @return
	 */
	public FileInfo getFileInfo(String fileId);

	/**
	 * 保存上传的多个文件
	 * 
	 * @param multipartFiles
	 * @return
	 */
	public FileInfo save(File file);

	/**
	 * 保存上传的多个文件
	 * 
	 * @param multipartFiles
	 * @return
	 */
	public List<FileInfo> save(MultipartFile[] multipartFiles);
}

