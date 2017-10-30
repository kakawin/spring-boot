package com.kakawin.gis.springboot.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import com.kakawin.gis.springboot.config.Constant;

public class FileUtil {

	static Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static byte[] download(String url) {
		log.info("Downloading, url: " + url);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
		if (response.hasBody()) {
			log.info("Downloaded, size: " + response.getBody().length);
			return response.getBody();
		} else {
			log.info("Downloaded, size: empty");
			return new byte[] {};
		}
	}

	public static void downloadToFile(String url, File dest) throws IOException {
		byte[] bytes = download(url);
		FileCopyUtils.copy(bytes, dest);
	}

	public static File createTempFile(String filename) {
		String type, name;
		int typeIndex = filename.lastIndexOf(".");
		if (typeIndex > 0) {
			type = "." + filename.substring(typeIndex + 1);
			name = filename.substring(0, filename.lastIndexOf("."));
		} else {
			type = "";
			name = filename;
		}
		return new File(
				Constant.tempPath + File.separator + name + "_" + DateUtil.formatMillis(new Date()) + type);
	}

}
