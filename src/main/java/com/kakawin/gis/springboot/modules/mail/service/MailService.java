package com.kakawin.gis.springboot.modules.mail.service;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kakawin.gis.springboot.modules.mail.entity.SysMailMessage;
import com.kakawin.gis.springboot.modules.system.entity.FileInfo;

@Service
public class MailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	String mailUsername;

	public void send(SysMailMessage sysMailMessage) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
		message.setFrom(mailUsername);
		message.setTo(sysMailMessage.getTarget());
		message.setSubject(sysMailMessage.getSubject());
		message.setText(sysMailMessage.getText());
		message.setCc(sysMailMessage.getCarbonCopys().toArray(new String[0]));
		List<FileInfo> fileInfos = sysMailMessage.getFileInfos();
		for (FileInfo fileInfo : fileInfos) {
			FileSystemResource file = new FileSystemResource(new File(fileInfo.getFullPath()));
			if (file.exists()) {
				message.addAttachment(fileInfo.getFileName(), file);
			}
		}
		javaMailSender.send(mimeMessage);
	}

}
