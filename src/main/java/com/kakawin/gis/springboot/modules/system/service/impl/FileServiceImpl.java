package com.kakawin.gis.springboot.modules.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kakawin.gis.springboot.config.Constant;
import com.kakawin.gis.springboot.modules.system.entity.FileInfo;
import com.kakawin.gis.springboot.modules.system.mapper.FileInfoMapper;
import com.kakawin.gis.springboot.modules.system.service.FileService;
import com.kakawin.gis.springboot.utils.SubjectUtil;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Override
	public FileInfo getFileInfo(String fileId) {
		return fileInfoMapper.selectById(fileId);
	}

	@Override
	public List<FileInfo> save(MultipartFile[] multipartFiles) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				try {
					FileInfo fileInfo = getInfoFrom(multipartFile);
					multipartFile.transferTo(new File(fileInfo.getFullPath()));
					fileInfoMapper.insert(fileInfo);
					fileInfos.add(fileInfo);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileInfos;
	}

	@Override
	public FileInfo save(File file) {
		FileInfo fileInfo = getInfoFrom(file);
		fileInfoMapper.insert(fileInfo);
		return fileInfo;
	}

	private FileInfo getInfoFrom(File file) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileId(UUID.randomUUID().toString());
		fileInfo.setCreator(SubjectUtil.getUsername());
		fileInfo.setCreateTime(new Date());

		// 文件名称
		String originalFilename = file.getName();
		String filename;
		int lastUnixIndex = originalFilename.lastIndexOf("/");
		int lastWinIndex = originalFilename.lastIndexOf("\\");
		if (lastUnixIndex > lastWinIndex) {
			filename = originalFilename.substring(lastUnixIndex + 1);
		} else {
			filename = originalFilename.substring(lastWinIndex + 1);
		}

		fileInfo.setFileName(filename);
		// 文件类型
		int typeIndex = filename.lastIndexOf(".");
		fileInfo.setFileType(typeIndex > -1 ? filename.substring(typeIndex + 1) : "");

		fileInfo.setFileDir(Constant.uploadPath);
		fileInfo.setFileContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		fileInfo.setFileSize(file.length());

		try {
			FileCopyUtils.copy(file, new File(fileInfo.getFullPath()));
			file.deleteOnExit();
		} catch (IOException e) {
			fileInfo.setFileName(originalFilename);
		}
		return fileInfo;
	}

	/**
	 * 将上传的文件组合成系统的文件信息
	 * 
	 * @param multipartFile
	 * @return
	 */
	private FileInfo getInfoFrom(MultipartFile multipartFile) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileId(UUID.randomUUID().toString());
		fileInfo.setCreator(SubjectUtil.getUsername());
		fileInfo.setCreateTime(new Date());

		// 文件名称
		String originalFilename = multipartFile.getOriginalFilename();
		String filename;
		int lastUnixIndex = originalFilename.lastIndexOf("/");
		int lastWinIndex = originalFilename.lastIndexOf("\\");
		if (lastUnixIndex > lastWinIndex) {
			filename = originalFilename.substring(lastUnixIndex + 1);
		} else {
			filename = originalFilename.substring(lastWinIndex + 1);
		}

		fileInfo.setFileName(filename);
		// 文件类型
		int typeIndex = filename.lastIndexOf(".");
		fileInfo.setFileType(typeIndex > -1 ? filename.substring(typeIndex + 1) : "");

		fileInfo.setFileDir(Constant.uploadPath);
		fileInfo.setFileContentType(multipartFile.getContentType());
		fileInfo.setFileSize(multipartFile.getSize());
		return fileInfo;
	}

	@Override
	public FileInfo findFileInfo(String filename) {
		String fileType, fileId;
		int typeIndex = filename.lastIndexOf(".");
		if (typeIndex > 0) {
			fileType = filename.substring(typeIndex + 1);
			fileId = filename.substring(0, filename.lastIndexOf("."));
		} else {
			fileType = "";
			fileId = filename;
		}
		FileInfo fileInfo = fileInfoMapper.selectById(fileId);
		if (fileInfo != null && fileType.equals(fileInfo.getFileType())) {
			return fileInfo;
		} else {
			return null;
		}
	}

	@Override
	public int delete(String id) {
		FileInfo fileInfo = fileInfoMapper.selectById(id);
		File file = new File(fileInfo.getFullPath());
		if (file.exists()) {
			file.deleteOnExit();
		}
		return fileInfoMapper.deleteById(id);
	}
}
