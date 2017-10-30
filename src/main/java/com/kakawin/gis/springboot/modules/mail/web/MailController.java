package com.kakawin.gis.springboot.modules.mail.web;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakawin.gis.springboot.modules.mail.entity.SysMailMessage;
import com.kakawin.gis.springboot.modules.mail.service.MailService;
import com.kakawin.gis.springboot.modules.system.service.FileService;
import com.kakawin.gis.springboot.utils.FileUtil;
import com.kakawin.gis.springboot.utils.SysResponse;


@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	MailService mailService;

	@Autowired
	FileService fileService;

	@PostMapping("/send")
	public SysResponse send() throws MessagingException, IOException {
		SysMailMessage sysMailMessage = new SysMailMessage("kakawin@126.com", "测试主题", "测试内容");
		sysMailMessage.addCarbonCopy("404132661@qq.com");
		File file = FileUtil.createTempFile("123.jpg");
		FileUtil.downloadToFile("http://localhost:8000/file/download/26484846-4419-45ea-8b65-98e3e2e20de7.jpg", file);
		sysMailMessage.addFileInfo(fileService.save(file));
		mailService.send(sysMailMessage);
		return SysResponse.ok();
	}

}
