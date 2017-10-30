package com.kakawin.gis.springboot.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "system.constant")
public class Constant {

	public static Integer defaultPageSize;

	public static Integer maxPageSize;

	public static String defaultPassword;

	public static String tokenName;

	public static String uploadPath;

	public static String tempPath;

	public static void setDefaultPageSize(Integer defaultPageSize) {
		Constant.defaultPageSize = defaultPageSize;
	}

	public static void setMaxPageSize(Integer maxPageSize) {
		Constant.maxPageSize = maxPageSize;
	}

	public static void setDefaultPassword(String defaultPassword) {
		Constant.defaultPassword = defaultPassword;
	}

	public static void setTokenName(String tokenName) {
		Constant.tokenName = tokenName;
	}

	public static void setUploadPath(String uploadPath) {
		Constant.uploadPath = uploadPath;
		new File(uploadPath).mkdirs();
	}

	public static void setTempPath(String tempPath) {
		Constant.tempPath = tempPath;
		new File(tempPath).mkdirs();
	}

}
