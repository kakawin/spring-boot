package com.kakawin.gis.springboot.modules.system.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kakawin.gis.springboot.modules.system.entity.FileInfo;
import com.kakawin.gis.springboot.modules.system.service.FileService;
import com.kakawin.gis.springboot.utils.SysResponse;


@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * 上传文件并返回文件信息
	 * 
	 * @param multipartFiles
	 * @return
	 */
	@RequiresAuthentication
	@PostMapping("/upload")
	@ResponseBody
	public SysResponse upload(@RequestParam("file") MultipartFile[] multipartFiles) {
		List<FileInfo> fileInfos = fileService.save(multipartFiles);
		return SysResponse.ok(fileInfos);
	}

	@RequiresPermissions("system:file:delete")
	@DeleteMapping("/delete")
	@ResponseBody
	public SysResponse delete(@RequestParam String id) {
		fileService.delete(id);
		return SysResponse.ok();
	}

	/**
	 * 在线查看文件
	 * 
	 * @param filename
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/resource/{filename}")
	public ResponseEntity<InputStreamResource> resource(@PathVariable String filename, HttpServletRequest request)
			throws IOException {
		FileInfo fileInfo = fileService.findFileInfo(filename);
		if (fileInfo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		FileSystemResource file = new FileSystemResource(fileInfo.getFullPath());
		if (!file.exists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		filename = URLEncoder.encode(fileInfo.getFileName(), "UTF-8");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
		headers.add("Content-Disposition", String.format("inline;filename=\"%s\"", filename));// attachment
		headers.add("Pragma", "no-cache"); 
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType(fileInfo.getFileContentType()))
				.body(new InputStreamResource(file.getInputStream()));
	}

	/**
	 * 下载文件
	 * 
	 * @param filename
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/download/{filename}")
	public ResponseEntity<InputStreamResource> download(@PathVariable String filename, HttpServletRequest request)
			throws IOException {
		FileInfo fileInfo = fileService.findFileInfo(filename);
		if (fileInfo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		FileSystemResource file = new FileSystemResource(fileInfo.getFullPath());
		if (!file.exists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		filename = URLEncoder.encode(fileInfo.getFileName(), "UTF-8");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
		headers.add("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType(fileInfo.getFileContentType()))
				.body(new InputStreamResource(file.getInputStream()));
	}

}
